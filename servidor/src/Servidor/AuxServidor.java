package Servidor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import Comun.*;

class AuxServidor {

	public static void configurar(int puerto) throws RemoteException {

		Registry registry = LocateRegistry.getRegistry(puerto);
		try {
			registry.list();
			System.out.println("se ha registrado el servidor en el puerto: " + puerto);

		} catch (Exception e) {
			registry = LocateRegistry.createRegistry(puerto);
			System.out.println("se ha creado el servidor en el puerto: " + puerto);
		}

		// Exportar objeto
		AutentificarInt servidor1 = new AutentificarImpl();
		GestorInt servidor2 = new GestorImpl();
		UnicastRemoteObject.unexportObject(servidor1, false);
		UnicastRemoteObject.unexportObject(servidor2, false);
		servidor1 = (AutentificarInt) UnicastRemoteObject.exportObject(servidor1, 0);
		servidor2 = (GestorInt) UnicastRemoteObject.exportObject(servidor2, 0);
		try {
			Naming.rebind("rmi://localhost:" + puerto + "/" + AutentificarInt.class.getCanonicalName(), servidor1);
			Naming.rebind("rmi://localhost:" + puerto + "/" + GestorInt.class.getCanonicalName(), servidor2);
		} catch (RemoteException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// salida por consola de las interfaces que pueden consumir los clientes
		System.out.println("Interfaces publicadas");
		for (String name : registry.list()) {
			System.out.println(name);

		}

	}

	public static DatosInt getServicioDatos(int puerto) {
		String registroDatos = "rmi://localhost:" + puerto + "/" + DatosInt.class.getCanonicalName();
		DatosInt servicioDatos = null;
		try {
			servicioDatos = (DatosInt) Naming.lookup(registroDatos);
			return servicioDatos;
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
			return servicioDatos;
		}
	}

	public static void menu(DatosInt d) throws RemoteException {
		Scanner sc = new Scanner(System.in);
		int opcion;

		do {
			System.out.println("1. Informaci�n del servidor");
			System.out.println("2. Listar usuarios registrados");
			System.out.println("3. Listar usuarios logueados");
			System.out.println("4. Bloquear (banear) usuario");
			System.out.println("5. Desbloquear usuario");
			System.out.println("6. Salir");

			System.out.print("Ingrese su opci�n: ");
			opcion = sc.nextInt();

			switch (opcion) {
			case 1:
				System.out.println("Ha elegido ver informaci�n del servidor.");
				// �Qu� informaci�n?
				break;
			case 2:
				System.out.println("Ha elegido listar usuarios registrados.");
				showRegistrados(d);
				break;
			case 3:
				System.out.println("Ha elegido listar usuarios logueados.");
				// listar usuarios logueados.
				break;
			case 4:
				System.out.println("Ha elegido bloquear (banear) usuario.");
				// listar usuarios registrados.
				break;
			case 5:
				System.out.println("Ha elegido desbloquear usuario.");
				// desbloqueo de usuario
				break;
			case 6:
				System.out.println("Saliendo del men�...");
				break;
			default:
				System.out.println("Opci�n inv�lida, por favor intente nuevamente.");
				break;
			}
		} while (opcion != 6);

		System.out.println("Fuera del menu.");
		sc.close();
	}
	
	public static void showRegistrados(DatosInt d) throws RemoteException {
		List<Usuario> registrados = d.getRegistrados();
		
		for(Usuario usuario : registrados) {
			usuario.show();		}
		}
	}

