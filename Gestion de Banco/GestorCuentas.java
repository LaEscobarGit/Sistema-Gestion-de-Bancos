import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

public class GestorCuentas {

    private ArrayList<Cuenta> cuentas;
    private static final String ARCHIVO = "cuentas.dat";
    private static final int LIMITE_CUENTAS = 10;

    public GestorCuentas() {
        cuentas = cargarCuentas();
    }

    // Crear nueva cuenta
    public boolean crearCuenta(String titular, String tipoCuenta, double saldoInicial) {

        if (cuentas.size() >= LIMITE_CUENTAS) {
            System.out.println("No es posible crear mas cuentas. Limite maximo alcanzado.");
            return false;
        }

        String id = UUID.randomUUID().toString();
        Cuenta nueva = new Cuenta(id, titular, tipoCuenta, saldoInicial);
        cuentas.add(nueva);

        if (guardarCuentas()) {
            System.out.println(" Cuenta creada con ID: " + id);
            return true;
        } else {
            System.out.println("Error al guardar la cuenta en el archivo.");
            return false;
        }
    }

    // Mostrar todas las cuentas
    public void mostrarCuentas() {
        if (cuentas.isEmpty()) {
            System.out.println("No hay cuentas registradas.");
            return;
        }
        for (Cuenta c : cuentas) {
            System.out.println(c);
        }
    }

    // Guardar cuentas en archivo
    private boolean guardarCuentas() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            oos.writeObject(cuentas);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cargar cuentas desde archivo
    @SuppressWarnings("unchecked")
    private ArrayList<Cuenta> cargarCuentas() {
        File file = new File(ARCHIVO);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO))) {
            return (ArrayList<Cuenta>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
