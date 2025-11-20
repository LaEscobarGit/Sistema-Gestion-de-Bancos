import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        GestorCuentas gestor = new GestorCuentas();

        int opcion;
        do {
            System.out.println("\n=== MENU DE CUENTAS BANCARIAS ===");
            System.out.println("1. Crear nueva cuenta");
            System.out.println("2. Mostrar todas las cuentas");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Nombre del titular: ");
                    String titular = sc.nextLine();

                    System.out.print("Tipo de cuenta (Ahorro/Corriente): ");
                    String tipo = sc.nextLine();

                    System.out.print("Saldo inicial: ");
                    double saldo = sc.nextDouble();
                    sc.nextLine(); // limpiar buffer

                    gestor.crearCuenta(titular, tipo, saldo);
                    break;

                case 2:
                    gestor.mostrarCuentas();
                    break;

                case 0:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opcion invalida");
            }

        } while (opcion != 0);

        sc.close();
    }
}
