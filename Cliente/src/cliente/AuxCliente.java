package cliente;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import Comun.*;

/*
 * Clase con funciones auxliares de cliente
 * para mejore legibilidad.
 * 
 * autor: rsanchez628@alumno.uned.es Ricardo Sanchez
 */
class AuxCliente {

	/*
	 * funcion para ofertar al usuario el primer menu que describe el enunciado de
	 * la practica autor: rsanchez628@alumno.uned.es
	 */

	public static void menu1(Configuracion setup) throws RemoteException {
		Scanner sc = new Scanner(System.in);
		int opcion;
		AutentificarInt servicioAutentificar = setup.getAutentificar();
		List<Usuario> usuarios = Debug.crearUsuario();

		do {
			System.out.println("1. Registrar a un nuevo usuario");
			System.out.println("2. Hacer login");
			System.out.println("3. Salir");

			System.out.print("Ingrese su opción: ");
			opcion = sc.nextInt();

			switch (opcion) {
			case 1:
				System.out.println("Ha elegido registrar a un nuevo usuario.");
				Debug.registrarUsuario(usuarios, servicioAutentificar, sc);
				break;
			case 2:
				System.out.println("Ha elegido hacer login.");
				Sesion sesion = Debug.crearSesion(usuarios, sc, setup);
				if (Debug.loguearUsuario(usuarios, servicioAutentificar, sesion)) {
					menu2(usuarios, setup, sc, sesion);
				}
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

	/*
	 * funcion para ofertar al usuario el menu una vez logueado según el enunciado
	 * de la practica autor: rsanchez628@alumno.uned.es
	 */

	public static void menu2(List<Usuario> usuarios, Configuracion setup, Scanner sc, Sesion sesion)
			throws RemoteException {
		int opcion;
		GestorInt serGestor = setup.getGestor();
		AutentificarInt serAuten = setup.getAutentificar();
		Usuario lider;
		
		serGestor.updateTrinos(sesion);

		do {
			System.out.println("1. Información del usuario.");
			System.out.println("2. Enviar trino");
			System.out.println("3. Listar usuarios del sistema");
			System.out.println("4. Seguir a");
			System.out.println("5. Dejar de seguir a");
			System.out.println("6. Borrar trino");
			System.out.println("7. Salir (Logout)");

			System.out.print("Ingrese su opción: ");
			opcion = sc.nextInt();

			switch (opcion) {
			case 1:
				System.out.println("Ha elegido ver la información del usuario.");
				//HashMap<Usuario, List<Usuario>> s = serGestor.getSeguidores();
				//showSeguidos(s);
				//sesion.getUser().show();
				System.out.println("Trinos");
				HashMap<Usuario, List<Trino>> lt = serGestor.getTrinos();
				showTrinos(lt);
				break;
			case 2:
				System.out.println("Ha elegido enviar trino.");
				Debug.trinar(sesion, serGestor, sc);
				break;
			case 3:
				System.out.println("Ha elegido listar usuarios del sistema.");
				listUser(serGestor);
				break;
			case 4:
				System.out.println("Ha elegido seguir a.");
				lider = Debug.elegirUsuario(usuarios, sc);
				serGestor.seguir(sesion, lider);
				break;
			case 5:
				System.out.println("Ha elegido dejar de seguir a.");
				lider = Debug.elegirUsuario(usuarios, sc);
				serGestor.abandonar(sesion, lider);
				break;
			case 6:
				System.out.println("Borrar trino.");
				// Código
				break;
			case 7:
				System.out.println("Saliendo del menú...");
				if(serAuten.desloguear(sesion)) {
					System.out.println("Su sesión se ha cerrado correctamente");
				} else {
					System.out.println("Se ha producido un error cerrando su sesión.");
				}
				break;
			default:
				System.out.println("Opción inválida, por favor intente nuevamente.");
				break;
			}
		} while (opcion != 7);

		System.out.println("Fuera del menu.");
	}

	/*
	 * Servico Autentificar. Registra a un usuario en la aplicacion
	 * 
	 * @autor: rsanchez628@alumno.uned.es Ricardo Sanchez
	 */
	public static void autentificar(AutentificarInt servicio, Usuario u) {
		u.show();
		try {
			if (servicio.registrar(u)) {
				System.out.println("el usuario se ha registrado correctamente");
			} else {
				System.out.println("ya existe un usuario registrado con nick: " + u.getNick());
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Servico Autentificar. Loguea a un usuario en la aplicacion
	 * 
	 * @autor: rsanchez628@alumno.uned.es Ricardo Sanchez
	 */
	public static boolean loguear(AutentificarInt servicio, Sesion s) throws java.rmi.RemoteException {
		// u.show();
		Usuario u = s.getUser();
		if (!servicio.checkRegistro(u)) {
			System.out.println("El usuario no se encuenta registrado");
			return false;
		} else {
			try {
				if (servicio.loguear(s)) {
					System.out.println("el usuario se ha logueado correctamente");
					return true;
				} else {
					System.out.println("ya existe un usuario logueado con nick: " + u.getNick());
					return false;
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}

	/*
	 * Servico Autentificar. Registra a un usuario en la aplicacion
	 * 
	 * @autor: rsanchez628@alumno.uned.es Ricardo Sanchez
	 */
	public static void registrar(AutentificarInt servicio, Usuario u) {
		u.show();
		try {
			if (servicio.registrar(u)) {
				System.out.println("el usuario se ha registrado correctamente");

			} else {
				System.out.println("ya existe un usuario registrado con nick: " + u.getNick());
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Configuracion configurar(int puerto) {
		try {

			// IMPORTAR LAS INTERFACES
			String registroAutentificar = "rmi://localhost:" + puerto + "/" + AutentificarInt.class.getCanonicalName();
			String registroGestor = "rmi://localhost:" + puerto + "/" + GestorInt.class.getCanonicalName();
			AutentificarInt servicioAutentificar = (AutentificarInt) Naming.lookup(registroAutentificar);
			GestorInt servicioGestor = (GestorInt) Naming.lookup(registroGestor);

			// registro en el puerto por defecto de rmi
			Registry registry = LocateRegistry.getRegistry(puerto);
			try {
				registry.list();

			} catch (Exception e) {
				registry = LocateRegistry.createRegistry(puerto);
			}

			CallbackInt servidorC = new CallbackImpl();

			servidorC = (CallbackInt) UnicastRemoteObject.exportObject(servidorC, 0);
			Naming.rebind("rmi://localhost:" + puerto + "/" + CallbackInt.class.getCanonicalName(), servidorC);

			return new Configuracion(servicioAutentificar, servicioGestor, servidorC);

		} catch (Exception e) {

			System.out.println("Excepcion: " + e);
			e.printStackTrace();
			return new Configuracion(null, null, null);

		}
	}

	// Para chequear el correcto funcionamiento del sistema
	// -----------------------------------------------------
	// BORRAR DESPUÉS

	// --- funciones que muestran por consola la BBDD del servidor
	public static void showTrinos(HashMap<Usuario, List<Trino>> tp) {
		for (Map.Entry<Usuario, List<Trino>> entry : tp.entrySet()) {
			Usuario u = entry.getKey();
			String nick = u.getNick();
			System.out.println("El usuario " + nick + " ha publicado:");
			List<Trino> l = entry.getValue();
			for (Trino e : l) {
				System.out.println(e.getMessage());
				System.out.println();
			}
		}
	}

	public static void showBloqueados(HashMap<Usuario, List<Usuario>> ub) {
		for (Map.Entry<Usuario, List<Usuario>> entry : ub.entrySet()) {
			Usuario u = entry.getKey();
			String nick = u.getNick();
			System.out.println("El usuario " + nick + " bloqueado a:");
			List<Usuario> l = entry.getValue();
			for (Usuario e : l) {
				e.show();
				System.out.println();
			}
		}
	}

	public static void showSeguidos(HashMap<Usuario, List<Usuario>> ls) {
		for (Map.Entry<Usuario, List<Usuario>> entry : ls.entrySet()) {
			Usuario u = entry.getKey();
			String nick = u.getNick();
			System.out.println("Los usuarios del seguidor: " + nick + " son:");
			List<Usuario> l = entry.getValue();
			for (Usuario e : l) {
				e.show();
				System.out.println();
			}
		}
	}
	
	public static void showRegistrados(List<Usuario> l) {
		System.out.println("Usuarios registrados en la aplicacion.");
		for (Usuario e : l) {
			e.show();
			System.out.println();
		}
	}
	
	public static void showLogueados(List<Sesion> l) {
		Usuario u;
		System.out.println("Usuarios actualmente logueados en la aplicacion.");
		for(Sesion s : l) {
			u = s.getUser();
			u.show();
			System.out.println();
		}
	}
	
	public static void listUser(GestorInt servidor) throws RemoteException {
		List<Usuario> lu = servidor.getRegistrados();
		List<Sesion> ls = servidor.getLogueados();
		showRegistrados(lu);
		showLogueados(ls);
	}
}
