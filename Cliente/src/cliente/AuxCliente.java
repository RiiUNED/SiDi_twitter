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

		do {
			System.out.println("1. Registrar a un nuevo usuario");
			System.out.println("2. Hacer login");
			System.out.println("3. Salir");

			System.out.print("Ingrese su opci�n: ");
			opcion = sc.nextInt();

			switch (opcion) {
			case 1:
				System.out.println("Ha elegido registrar a un nuevo usuario.");
				System.out.println("Elija usuario: 1 Ri, 2 Ro, 3 B1.");
				int usr = sc.nextInt();
				Debug.registrarUsuario(servicioAutentificar, usr);
				break;
			case 2:
				System.out.println("Ha elegido hacer login.");
				System.out.println("Elija usuario: 1 Ri, 2 Ro, 3 B1.");
				usr = sc.nextInt();
				if(Debug.loguearUsuario(servicioAutentificar, usr)) {
					menu2(setup, sc);	
				}				
				break;
			case 3:
				System.out.println("Saliendo del men�...");
				break;
			default:
				System.out.println("Opci�n inv�lida, por favor intente nuevamente.");
				break;
			}
		} while (opcion != 3);

		System.out.println("Fuera del menu.");
		sc.close();
	}

	/*
	 * funcion para ofertar al usuario el menu una vez logueado seg�n el enunciado
	 * de la practica autor: rsanchez628@alumno.uned.es
	 */

	public static void menu2(Configuracion setup, Scanner sc) throws RemoteException {
		//Scanner sc = new Scanner(System.in);
		int opcion;

		do {
			System.out.println("1. Informaci�n del usuario.");
			System.out.println("2. Enviar trino");
			System.out.println("3. Listar usuarios del sistema");
			System.out.println("4. Seguir a");
			System.out.println("5. Dejar de seguir a");
			System.out.println("6. Borrar trino");
			System.out.println("7. Salir (Logout)");

			System.out.print("Ingrese su opci�n: ");
			opcion = sc.nextInt();

			switch (opcion) {
			case 1:
				System.out.println("Ha elegido ver la informaci�n del usuario.");
				// C�digo
				break;
			case 2:
				System.out.println("Ha elegido enviar trino.");
				// C�digo
				break;
			case 3:
				System.out.println("Ha elegido listar usuarios del sistema.");
				// C�digo
				break;
			case 4:
				System.out.println("Ha elegido seguir a.");
				// C�digo
				break;
			case 5:
				System.out.println("Ha elegido dejar de seguir a.");
				// C�digo
				break;
			case 6:
				System.out.println("Borrar trino.");
				// C�digo
				break;
			case 7:
				System.out.println("Saliendo del men�...");
				break;
			default:
				System.out.println("Opci�n inv�lida, por favor intente nuevamente.");
				break;
			}
		} while (opcion != 7);

		System.out.println("Fuera del menu.");
		//sc.close();
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
	public static boolean loguear(AutentificarInt servicio, Usuario u) throws java.rmi.RemoteException{
		//u.show();
		if(!servicio.checkRegistro(u)) {
			System.out.println("El usuario no se encuenta registrado");
			return false;
		} else {
			try {
				if (servicio.loguear(u)) {
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
			System.out.println("Importando las interfaces...");
			System.out.println();
			String registroAutentificar = "rmi://localhost:" + puerto + "/" + AutentificarInt.class.getCanonicalName();
			String registroGestor = "rmi://localhost:" + puerto + "/" + GestorInt.class.getCanonicalName();
			AutentificarInt servicioAutentificar = (AutentificarInt) Naming.lookup(registroAutentificar);
			GestorInt servicioGestor = (GestorInt) Naming.lookup(registroGestor);

			// publicando las callbacks
			System.out.println("Publicando las callbacks...");
			System.out.println();

			// -----
			// registro en el puerto por defecto de rmi
			Registry registry = LocateRegistry.getRegistry(puerto);
			try {
				registry.list();
				System.out.println("se ha registrado el servicio callback en el puerto: " + puerto);

			} catch (Exception e) {
				registry = LocateRegistry.createRegistry(puerto);
				System.out.println("se ha creado el servicio callback en el puerto: " + puerto);
			}

			CallbackInt servidorC = new CallbackImpl();

			servidorC = (CallbackInt) UnicastRemoteObject.exportObject(servidorC, 0);
			Naming.rebind("rmi://localhost:" + puerto + "/" + CallbackInt.class.getCanonicalName(), servidorC);

			for (String name : registry.list()) {
				System.out.println(name);
			}

			return new Configuracion(servicioAutentificar, servicioGestor, servidorC);

		} catch (Exception e) {

			System.out.println("Excepcion: " + e);
			e.printStackTrace();
			return new Configuracion(null, null, null);

		}
	}

	// Para chequear el correcto funcionamiento del sistema
	// -----------------------------------------------------
	// BORRAR DESPU�S

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
}
