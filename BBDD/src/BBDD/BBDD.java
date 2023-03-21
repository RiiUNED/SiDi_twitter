package BBDD;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Scanner;

import Comun.*;

public class BBDD {

	public static void main(String[] args) throws Exception {
		
		// registro en el puerto por defecto de rmi
		int puerto = Registry.REGISTRY_PORT;
		
		DatosInt servidor = configurar(puerto);
		
		menu(servidor);

	}
	
	public static DatosInt configurar(int puerto) throws RemoteException, MalformedURLException {
		Registry registry = LocateRegistry.getRegistry(puerto);
		try {
			registry.list();

		} catch (Exception e) {
			registry = LocateRegistry.createRegistry(puerto);
		}

		// Exportar objeto
		DatosInt servidorDatos = new DatosImpl();
		UnicastRemoteObject.unexportObject(servidorDatos, false);
		servidorDatos = (DatosInt) UnicastRemoteObject.exportObject(servidorDatos, 0);
		Naming.rebind("rmi://localhost:" + puerto + "/" + DatosInt.class.getCanonicalName(), servidorDatos);
		
		return servidorDatos;
	}
	
	/*
	 * funcion para ofertar el menu de la BBDD que describe el enunciado de la
	 * practica
	 * autor: rsanchez628@alumno.uned.es
	 */

	public static void menu(DatosInt servidor) throws RemoteException { 
	        Scanner sc = new Scanner(System.in);
	        int opcion;

	        do {
	            System.out.println("1. Información de la BBDD");
	            System.out.println("2. Listar trinos (nick del propietario y timestamp)");
	            System.out.println("3. Salir");

	            System.out.print("Ingrese su opción: ");
	            opcion = sc.nextInt();

	            switch (opcion) {
	                case 1:
	                    System.out.println("Ha elegido información de la BBDD.");
	                    //Código necesario para la info de la BBDD
	                    
	                    break;
	                case 2:
	                    System.out.println("Ha elegido listar trinos.");
	                    servidor.imprimirTrinos();
	                    break;
	                case 3:
	                    System.out.println("Saliendo del menú...");
	                    break;
	                default:
	                    System.out.println("Opción inválida, por favor intente nuevamente.");
	                    break;
	            }
	        } while (opcion != 3);
	        
	        System.out.println("Fuera del menu.");
	        sc.close();
	    }

}
