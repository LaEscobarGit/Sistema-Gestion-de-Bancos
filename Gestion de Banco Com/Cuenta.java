import java.io.Serializable;

public class Cuenta implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String numero;
    private String nombre;
    private double saldo;
    private boolean cerrada;

    public Cuenta(String numero, String nombre, double saldo) {
        this.numero = numero;
        this.nombre = nombre;
        this.saldo = saldo;
        this.cerrada = false;
    }

    public String getNumero() {
         return numero;
         }
    public String getNombre() { 
        return nombre;
     }
    public double getSaldo() { 
        return saldo;
     }
    public boolean isCerrada() { 
        return cerrada; 
    }

    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }
    public void setSaldo(double saldo) { 
        this.saldo = saldo; 
    }
    public void cerrar() { 
        this.cerrada = true; 
    }

    @Override
    public String toString() {
        return numero + ";" + nombre + ";" + saldo + ";" + cerrada;
    }

    public static Cuenta fromString(String linea) {
        String[] p = linea.split(";", -1);
        String num = p.length > 0 ? p[0] : "";
        String nom = p.length > 1 ? p[1] : "";
        double sal = 0;
        try { 
            if (p.length > 2) sal = Double.parseDouble(p[2]);
         } catch (NumberFormatException e) {}
        Cuenta c = new Cuenta(num, nom, sal);
        if (p.length > 3 && p[3].equalsIgnoreCase("true")) c.cerrar();
        return c;
    }
}
