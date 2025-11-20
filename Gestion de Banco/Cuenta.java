import java.io.Serializable;

public class Cuenta implements Serializable {

    private String id;
    private String titular;
    private String tipoCuenta;
    private double saldo;

    public Cuenta(String id, String titular, String tipoCuenta, double saldo) {
        this.id = id;
        this.titular = titular;
        this.tipoCuenta = tipoCuenta;
        this.saldo = saldo;
    }

    public String getId() { return id; }
    public String getTitular() { return titular; }
    public String getTipoCuenta() { return tipoCuenta; }
    public double getSaldo() { return saldo; }

    @Override
    public String toString() {
        return "ID: " + id + ", Titular: " + titular + ", Tipo: " + tipoCuenta + ", Saldo: " + saldo;
    }
}
