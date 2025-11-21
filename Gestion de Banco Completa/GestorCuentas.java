import java.io.*;
import java.util.*;

public class GestorCuentas {

    private static final String ARCHIVO_CUENTAS = "cuentas.txt";
    private static final String ARCHIVO_HISTORIAL = "historial.txt";

    // Guarda una cuenta (append)
    public static void guardarCuenta(Cuenta c) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_CUENTAS, true))) {
            pw.println(c.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Carga todas las cuentas desde archivo
    public static List<Cuenta> cargarCuentas() {
        List<Cuenta> lista = new ArrayList<>();
        File f = new File(ARCHIVO_CUENTAS);
        if (!f.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_CUENTAS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                lista.add(Cuenta.fromString(linea));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Sobrescribe el archivo con la lista actual
    public static void sobrescribir(List<Cuenta> lista) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_CUENTAS))) {
            for (Cuenta c : lista) {
                pw.println(c.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Registra una línea en historial
    public static void registrarMovimiento(String texto) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_HISTORIAL, true))) {
            pw.println(texto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Buscar por número
    public static Cuenta buscar(String numero) {
        for (Cuenta c : cargarCuentas()) {
            if (c.getNumero().equals(numero)) return c;
        }
        return null;
    }

    // Genera ID automático tipo C0001, C0002, etc basándose en los existentes
    public static String generarIDAutomatico() {
        List<Cuenta> lista = cargarCuentas();
        int max = 0;
        for (Cuenta c : lista) {
            String n = c.getNumero();
            if (n != null && n.length() > 1 && (n.charAt(0) == 'C' || n.charAt(0) == 'c')) {
                String digits = n.substring(1).replaceAll("[^0-9]", "");
                try {
                    int val = Integer.parseInt(digits);
                    if (val > max) max = val;
                } catch (Exception ex) {  }
            } else {
                // si el número no empieza con C, intenta parsear entero completo
                String digits = n.replaceAll("[^0-9]", "");
                try {
                    int val = Integer.parseInt(digits);
                    if (val > max) max = val;
                } catch (Exception ex) { }
            }
        }
        int next = max + 1;
        return String.format("C%04d", next); 
    }

    // Crear nueva cuenta (usa ID automático)
    public static boolean crearCuentaAuto(String nombre, double saldo, String[] salidaId) {
        List<Cuenta> lista = cargarCuentas();
        if (lista.size() >= 10) return false;

        String id = generarIDAutomatico();
        // evita que haya duplicados
        while (buscar(id) != null) {
            // incrementar numérico
            String digits = id.substring(1).replaceAll("[^0-9]", "");
            int val = 0;
            try { val = Integer.parseInt(digits); } catch (Exception ex) {}
            val++;
            id = String.format("C%04d", val);
        }

        Cuenta c = new Cuenta(id, nombre, saldo);
        guardarCuenta(c);
        registrarMovimiento("CREACION | Cuenta: " + id + " | Nombre: " + nombre + " | Saldo: " + saldo);
        if (salidaId != null && salidaId.length > 0) salidaId[0] = id;
        return true;
    }

    // Cerrar cuenta: solo si saldo == 0
    public static boolean cerrarCuenta(String numero) {
        List<Cuenta> lista = cargarCuentas();
        for (Cuenta c : lista) {
            if (c.getNumero().equals(numero)) {
                if (c.getSaldo() != 0) return false;
                c.cerrar();
                sobrescribir(lista);
                registrarMovimiento("CIERRE | Cuenta: " + numero);
                return true;
            }
        }
        return false;
    }

    // Modificar nombre
    public static boolean modificarNombre(String numero, String nuevoNombre) {
        List<Cuenta> lista = cargarCuentas();
        for (Cuenta c : lista) {
            if (c.getNumero().equals(numero)) {
                if (c.isCerrada()) return false;
                c.setNombre(nuevoNombre);
                sobrescribir(lista);
                registrarMovimiento("MODIFICAR NOMBRE | Cuenta: " + numero + " | Nuevo: " + nuevoNombre);
                return true;
            }
        }
        return false;
    }

    // Depositar
    public static boolean depositar(String numero, double monto) {
        if (monto <= 0) return false;
        List<Cuenta> lista = cargarCuentas();
        for (Cuenta c : lista) {
            if (c.getNumero().equals(numero)) {
                if (c.isCerrada()) return false;
                c.setSaldo(c.getSaldo() + monto);
                sobrescribir(lista);
                registrarMovimiento("DEPOSITO | Cuenta: " + numero + " | +" + monto);
                return true;
            }
        }
        return false;
    }

    // Retirar
    public static boolean retirar(String numero, double monto) {
        if (monto <= 0) return false;
        List<Cuenta> lista = cargarCuentas();
        for (Cuenta c : lista) {
            if (c.getNumero().equals(numero)) {
                if (c.isCerrada()) return false;
                if (c.getSaldo() < monto) return false;
                c.setSaldo(c.getSaldo() - monto);
                sobrescribir(lista);
                registrarMovimiento("RETIRO | Cuenta: " + numero + " | -" + monto);
                return true;
            }
        }
        return false;
    }

    // Transferir (doble registro en historial)
    public static boolean transferir(String origen, String destino, double monto) {
        if (monto <= 0) return false;
        List<Cuenta> lista = cargarCuentas();
        Cuenta cOrigen = null, cDestino = null;
        for (Cuenta c : lista) {
            if (c.getNumero().equals(origen)) cOrigen = c;
            if (c.getNumero().equals(destino)) cDestino = c;
        }
        if (cOrigen == null || cDestino == null) return false;
        if (cOrigen.isCerrada() || cDestino.isCerrada()) return false;
        if (cOrigen.getSaldo() < monto) return false;

        cOrigen.setSaldo(cOrigen.getSaldo() - monto);
        cDestino.setSaldo(cDestino.getSaldo() + monto);
        sobrescribir(lista);

        registrarMovimiento("TRANSFERENCIA SALIENTE | Origen: " + origen + " | -"+ monto + " | Destino: " + destino);
        registrarMovimiento("TRANSFERENCIA ENTRANTE  | Destino: " + destino + " | +"+ monto + " | Origen: " + origen);

        return true;
    }
}
