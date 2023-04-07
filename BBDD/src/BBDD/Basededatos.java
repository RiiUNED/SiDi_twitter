/**
 * Autor: 	Ricardo Sanchez Fernadez
 * Email:	rsanchez628@alumno.uned.es
 */
package BBDD;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Scanner;

import Interfaces.*;

/*
 * Punto de entrada a la BBDD
 */
public class Basededatos {

	public static void main(String[] args) throws Exception {

		// registro en el puerto por defecto de rmi
		int puerto = Registry.REGISTRY_PORT;
		String servicio = "rmi://localhost:" + puerto + "/" + ServicioDatosInterface.class.getCanonicalName();

		ServicioDatosInterface servidor = configurar(puerto, servicio);

		menu(servidor, servicio);

		System.exit(0);

	}

	/*
	 * Publica la interfaz de los servicios de datos que consume el serividor: 
	 * param: 
	 * 	puerto: puerto en le que se publica la interfaz de datos
	 * 	servicio: informacion con la URL  
	 * return:
	 * interfaces con servicio datos
	 */
	public static ServicioDatosInterface configurar(
			int puerto, 
			String servicio)
			throws RemoteException, MalformedURLException {
		Registry registry = LocateRegistry.getRegistry(puerto);
		try {
			registry.list();

		} catch (Exception e) {
			registry = LocateRegistry.createRegistry(puerto);
		}

		ServicioDatosInterface servidorDatos = new ServicioDatosImpl();
		UnicastRemoteObject.unexportObject(servidorDatos, false);
		servidorDatos = (ServicioDatosInterface) UnicastRemoteObject.exportObject(servidorDatos, 0);
		Naming.rebind(servicio, servidorDatos);

		return servidorDatos;
	}

	/*
	 * Publica el menu que ofrece la BBDD
	 * param:
	 * 	servidor:		interfaz con los servicios que publica la BBDD
	 * 	servicio:		informacion con la URL 
	 * return: publicacion del primer menu
	 */
	public static void menu(ServicioDatosInterface servidor, String servicio) throws RemoteException {
		Scanner sc = new Scanner(System.in);
		int opcion;

		do {
			System.out.println("1. Información de la BBDD");
			System.out.println("2. Listar trinos (nick del propietario y timestamp)");
			System.out.println("3. Salir");

			System.out.print("Ingrese su opción: ");
			opcion = sc.nextInt();
			sc.nextLine();

			switch (opcion) {
			case 1:
				System.out.println("Ha elegido información de la BBDD.");
				AuxDatos.info(servicio);
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
