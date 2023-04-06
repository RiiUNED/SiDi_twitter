/**
 * Autor:	Ricardo Sanchez
 * Email:	rsanchez628@alumno.uned.es
 */
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

import Interfaces.*;
import Servicios.Auxiliar;
import Datos.*;

class AuxServidor {

	static ServicioGestorInterface configurar(int puerto, String autenticar, String gestor) throws RemoteException {

		Registry registry = LocateRegistry.getRegistry(puerto);
		try {
			registry.list();

		} catch (Exception e) {
			registry = LocateRegistry.createRegistry(puerto);
		}

		// Exportar objeto
		ServicioAutentificacionInterface servidor1 = new ServicioAutentificacionImpl();
		ServicioGestorInterface servidor2 = new ServicioGestorImpl();
		UnicastRemoteObject.unexportObject(servidor1, false);
		UnicastRemoteObject.unexportObject(servidor2, false);
		servidor1 = (ServicioAutentificacionInterface) UnicastRemoteObject.exportObject(servidor1, 0);
		servidor2 = (ServicioGestorInterface) UnicastRemoteObject.exportObject(servidor2, 0);
		try {
			// Naming.rebind("rmi://localhost:" + puerto + "/" +
			// AutentificarInt.class.getCanonicalName(), servidor1);
			// Naming.rebind("rmi://localhost:" + puerto + "/" +
			// GestorInt.class.getCanonicalName(), servidor2);
			Naming.rebind(autenticar, servidor1);
			Naming.rebind(gestor, servidor2);
		} catch (RemoteException | MalformedURLException e) {
			e.printStackTrace();
			return servidor2;
		}
		return servidor2;
	}

	static ServicioDatosInterface getServicioDatos(int puerto) {
		String registroDatos = "rmi://localhost:" + puerto + "/" + ServicioDatosInterface.class.getCanonicalName();
		ServicioDatosInterface servicioDatos = null;
		try {
			servicioDatos = (ServicioDatosInterface) Naming.lookup(registroDatos);
			return servicioDatos;
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			//e.printStackTrace();
			System.out.println("El correcto orden para levantar servicios es:");
			System.out.println("1. BBDD");
			System.out.println("2. Servidor");
			System.out.println("3. Clientes");
			System.out.println("El correcto orden para salir es:");
			System.out.println("1. Clientes");
			System.out.println("2. Servidor");
			System.out.println("3. BBDD");
			System.exit(0);
			return servicioDatos;
		}
	}

	private static void banear(ServicioDatosInterface d, Usuario u) throws RemoteException {
		if (d.banear(u)) {
			u.show();
			System.out.println("ha sido baneado");
		} else {
			u.show();
			System.out.println("ya estaba baneado");
		}

	}

	private static void unban(ServicioGestorInterface servidor, ServicioDatosInterface d, Usuario u) throws RemoteException {
		List<Trino> trinosB = d.unban(u);

		for (Trino t : trinosB) {
			servidor.trinar(u, t, false);
		}
	}

	static void menu(ServicioGestorInterface servidor, ServicioDatosInterface d, String autenticar, String gestor) throws RemoteException {
		Scanner sc = new Scanner(System.in);
		boolean serv = true;
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
			sc.nextLine();

			switch (opcion) {
			case 1:
				System.out.println("Ha elegido ver información del servidor.");
				info(autenticar, gestor);
				break;
			case 2:
				System.out.println("Ha elegido listar usuarios registrados.");
				showRegistrados(d);
				break;
			case 3:
				System.out.println("Ha elegido listar usuarios logueados.");
				showLog(d);
				break;
			case 4: // Mirar si creo usuario en Cliente, pasarlo a comun y pista
				System.out.println("Ha elegido bloquear (banear) usuario.");
				/*
				 * List<Usuario> lu = DebugS.crearUsuario(); Usuario u =
				 * DebugS.elegirUsuario(lu, sc);
				 */
				Usuario u = Auxiliar.getUsuario(sc, serv);
				banear(d, u);
				break;
			case 5:
				System.out.println("Ha elegido desbloquear usuario.");
				/*
				 * List<Usuario> lud = DebugS.crearUsuario(); Usuario ud =
				 * DebugS.elegirUsuario(lud, sc);
				 */
				Usuario ud = Auxiliar.getUsuario(sc, serv);
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
	private static void showRegistrados(ServicioDatosInterface d) throws RemoteException {
		List<Usuario> registrados = d.getRegistrados();

		for (Usuario usuario : registrados) {
			usuario.show();
		}
	}

	// muestra los usuarios logueados en la aplicación
	private static void showLog(ServicioDatosInterface d) throws RemoteException {
		List<Sesion> log = d.getLogueados();
		Usuario usuario;

		for (Sesion sesion : log) {
			usuario = sesion.getUser();
			usuario.show();
		}
	}

	static void publicar(List<CallbackUsuarioInterface> l, Trino trino) {
		if (!l.isEmpty()) {
			l.stream().forEach(new Consumer<>() {

				@Override
				public void accept(CallbackUsuarioInterface t) {
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

	// informa de la URL del servicio RMI del Servidor
	static void info(String autenticar, String gestor) {
		// showBaneados();
		// showTrinoB();
		// Auxiliar.showSeguidores(this.seguidores);

		System.out.println("Servicio RMI del servidor");
		System.out.println("Servicio autenticar: "+autenticar);
		System.out.println("Servicio gestor: "+gestor);
	}

}
