package buscaminas.servidor;

import buscaminas.comunes.BaseDeDatos;
import buscaminas.comunes.Mensaje;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor{
    static List<ObjectOutputStream> clientes = new ArrayList<>();
    
    public static void main(String args[]) throws IOException {
        ServerSocket servidor = new ServerSocket(35558);
        BaseDeDatos bd = new BaseDeDatos();
        
        
        while(true){
            Socket cliente = servidor.accept();
            if(clientes.size()<2){
                ObjectOutputStream salidaDatos = new ObjectOutputStream(cliente.getOutputStream());
                ObjectInputStream entradaDatos = new ObjectInputStream(cliente.getInputStream());
                clientes.add(salidaDatos);
                
                salidaDatos.writeObject(new Mensaje("ACEPTADO",null));
                int noJugador = clientes.size() - 1;
                salidaDatos.writeObject(new Mensaje("JUGADOR",noJugador));
                
                ManejoCliente manejador = new ManejoCliente(clientes, cliente, entradaDatos, salidaDatos, noJugador);
                manejador.start();
            }else{
                ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
                salida.writeObject(new Mensaje("RECHAZADO",null));
                salida.close();
                entrada.close();
                cliente.close();
            }
        }
    }
    
    public void desconectar(){
        
    }
}