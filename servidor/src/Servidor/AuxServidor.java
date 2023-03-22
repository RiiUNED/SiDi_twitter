package Servidor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.function.Consumer;

import Comun.*;

class AuxServidor {

	public static GestorInt configurar(int puerto) throws RemoteException {

		Registry registry = LocateRegistry.getRegistry(puerto);
		try {
			registry.list();

		} catch (Exception e) {
			registry = LocateRegistry.createRegistry(puerto);
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
			e.printStackTrace();
			return servidor2;
		}
		return servidor2;
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
	
	public static void banear(DatosInt d, Usuario u) throws RemoteException{
		if(d.banear(u)) {
			u.show();
			System.out.println("ha sido baneado");
		} else {
			u.show();
			System.out.println("ya estaba baneado");
		}
		
	}
	
	public static void unban(
			GestorInt servidor, 
			DatosInt d, 
			Usuario u) throws RemoteException{
		List<Trino> trinosB = d.unban(u);
		
		for(Trino t : trinosB) {
			servidor.trinar(u, t, false);
		}
	}

	public static void menu(GestorInt servidor, DatosInt d) throws RemoteException {
		Scanner sc = new Scanner(System.in);
		int opcion;

		do {
			System.out.println("1. Información del servidor");
			System.out.println("2. Listar usuarios registrados");
			System.out.println("3. Listar usuarios logueados");
			System.out.println("4. Bloquear (banear) usuario");
			System.out.println("5. Desbloquear usuario");
			System.out.println("6. Salir");

			System.out.print("Ingrese su opción: ");
			opcion = sc.nextInt();

			switch (opcion) {
			case 1:
				System.out.println("Ha elegido ver información del servidor.");
				// ¿Qué información?
				break;
			case 2:
				System.out.println("Ha elegido listar usuarios registrados.");
				showRegistrados(d);
				break;
			case 3:
				System.out.println("Ha elegido listar usuarios logueados.");
				showLog(d);
				break;
			case 4:
				System.out.println("Ha elegido bloquear (banear) usuario.");
				List<Usuario> lu = DebugS.crearUsuario();
				Usuario u = DebugS.elegirUsuario(lu, sc);
				banear(d, u);
				break;
			case 5:
				System.out.println("Ha elegido desbloquear usuario.");
				List<Usuario> lud = DebugS.crearUsuario();
				Usuario ud = DebugS.elegirUsuario(lud, sc);
				unban(servidor, d, ud);
				break;
			case 6:
				System.out.println("Saliendo del menú...");
				break;
			default:
				System.out.println("Opción inválida, por favor intente nuevamente.");
				break;
			}
		} while (opcion != 6);

		System.out.println("Fuera del menu.");
		sc.close();
	}

	// muestra los usuarios registrados en la aplicación
	public static void showRegistrados(DatosInt d) throws RemoteException {
		List<Usuario> registrados = d.getRegistrados();

		for (Usuario usuario : registrados) {
			usuario.show();
		}
	}

	// muestra los usuarios logueados en la aplicación
	public static void showLog(DatosInt d) throws RemoteException {
		List<Sesion> log = d.getLogueados();
		Usuario usuario;

		for (Sesion sesion : log) {
			usuario = sesion.getUser();
			usuario.show();
		}
	}

	public static void publicar(List<CallbackInt> l, Trino trino) {
		if (!l.isEmpty()) {
			l.stream().forEach(new Consumer<>() {

				@Override
				public void accept(CallbackInt t) {
					// TODO Auto-generated method stub
					try {
						t.publicar(trino);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

}
