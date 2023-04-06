/**
 * Autor: 	Ricardo Sanchez
 * Email:	rsanchez628@alumno.uned.es
 */
package cliente;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import Interfaces.*;
import Servicios.*;
import Datos.*;

// Clase con funciones auxiliares de la clase cliente
class AuxCliente {

	/*
	 * funcion para ofertar al usuario el primer menu que describe el enunciado de
	 * la practica autor: rsanchez628@alumno.uned.es
	 */

	public static void menu1(Configuracion setup, String servicio) throws RemoteException {
		Scanner sc = new Scanner(System.in);
		boolean servidor = false;
		int opcion;
		ServicioAutentificacionInterface servicioAutentificar = setup.getAutentificar();
		//List<Usuario> usuarios = DebugC.crearUsuario();

		do {
			System.out.println("1. Registrar a un nuevo usuario");
			System.out.println("2. Hacer login");
			System.out.println("3. Salir");

			System.out.print("Ingrese su opción: ");
			opcion = sc.nextInt();
			sc.nextLine();

			switch (opcion) {
			case 1:
				System.out.println("Ha elegido registrar a un nuevo usuario.");
				//DebugC.registrarUsuario(usuarios, servicioAutentificar, sc);
				registrarUsuario(servicioAutentificar, sc, servidor);
				break;
			case 2:
				System.out.println("Ha elegido hacer login.");
				Sesion sesion = crearSesion(sc, setup);
				if(checkPass(servicioAutentificar, sesion)) {
					if (loguear(servicioAutentificar, sesion)) {
						menu2(setup, sc, sesion, servicio);
					} 
				}else {
					System.out.println("Datos incorrectos");
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
	 * Crea la sesión del usuario que se loguea
	 * param:
	 * 	Scanner:		Scanner del sistema
	 * 	Setup:		Configuración/Servidores de la aplicacion
	 * return: sesión del usuario que se loguea	
	 */
	private static Sesion crearSesion( 
			Scanner sc, 
			Configuracion setup) {
		Usuario ghost = getGhostUser(sc);
		CallbackUsuarioInterface s = setup.getServidor();
		Sesion sesion = new Sesion(ghost, s);
		return sesion;
	}
	
	private static boolean checkPass(ServicioAutentificacionInterface servicio, Sesion sesion) throws RemoteException {
		return servicio.checkPass(sesion);
	}
	
	/*
	 * Pide datos por consola para crear un usuario fantasma con el que hacer el Login
	 * param:
	 * Scanner:		Scanner del sistema
	 * return:		Usuario fantasma
	 */
	private static Usuario getGhostUser(Scanner sc) {
		String Qnick = "Introduzca el nick: ";
		String nick = Auxiliar.getString(sc, Qnick);
		String Qpass = "Introduzca el pass: ";
		String pass = Auxiliar.getString(sc, Qpass);
		Usuario ghost = new Usuario(null, nick, pass);
		return ghost;
	}
	
	/*
	 * Registra usuarios en el sistema
	 * @param:
	 * servicioAutentificar:	Interfaz con los servicios de Autentificación del servidor
	 * sc:						Scanner del sistema
	 * @return: Usuario registrado en la BBDD de la aplicación
	 */
	private static void registrarUsuario(
			ServicioAutentificacionInterface servicioAutentificar, 
			Scanner sc,
			boolean servidor) throws RemoteException {
		//Usuario u = elegirUsuario(usuarios, sc);
		Usuario u = Auxiliar.getUsuario(sc, servidor);
		AuxCliente.registrar(servicioAutentificar, u);
	}
	
	/*
	 * funcion para ofertar al usuario el menu una vez logueado según el enunciado
	 * de la practica autor: rsanchez628@alumno.uned.es
	 */

	private static void menu2(
			Configuracion setup, 
			Scanner sc, 
			Sesion sesion,
			String servicio)
			throws RemoteException {
		int opcion;
		ServicioGestorInterface serGestor = setup.getGestor();
		ServicioAutentificacionInterface serAuten = setup.getAutentificar();
		Usuario lider;
		boolean follow = false;
		
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
			sc.nextLine();

			switch (opcion) {
			case 1:
				System.out.println("Ha elegido ver la información del usuario logueado.");
				info(servicio);
				break;
			case 2:
				System.out.println("Ha elegido enviar trino.");
				//DebugC.trinar(sesion, serGestor, sc);
				trinar(sesion, serGestor, sc);
				break;
			case 3:
				System.out.println("Ha elegido listar usuarios del sistema.");
				//listUser(serGestor);
				listUser(serAuten);
				break;
			case 4:
				System.out.println("Ha elegido seguir a.");
				//lider = DebugC.elegirUsuario(usuarios, sc);
				follow = true;
				String lNick = getLiderNick(sc, follow);
				lider = serAuten.getUser(lNick);
				serGestor.seguir(sesion, lider);
				break;
			case 5:
				System.out.println("Ha elegido dejar de seguir a.");
				follow = false;
				//lider = DebugC.elegirUsuario(usuarios, sc);
				lider = serAuten.getUser(getLiderNick(sc, follow));
				serGestor.abandonar(sesion, lider);
				break;
			case 6:
				System.out.println("Borrar trino.");
				Usuario user = sesion.getUser();
				//Trino t = DebugC.elegirTrino(user, sc);
				Trino t = getTrino(sc, user);
				serGestor.borrarTrino(sesion, t);
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
	 * Crea un trino del usuario que esta registrado en la sesison
	 * param:
	 * 	sc: Scanner del sistema
	 * 	sesion: sesion del usuario que registra el trino
	 * return:	Trino del usuario
	 */
	private static Trino getTrino(Scanner sc, Usuario u) {
		String Qmessage = "Introduzca el mensaje del trino: ";
		String m = Auxiliar.getString(sc, Qmessage);
		String nick = u.getNick();
		Trino t = new Trino(nick, m);
		return t;
	}
	
	/*
	 * Crea a un usuario que se seguira o abandonara segun la opcion follow/unfollow
	 * param:
	 * 	sc:		scanner del sistema
	 * 	follow:	bandera para indicar si el usuario se seguira o dejara de seguir
	 * return: usuario que se seguira o dejara de seguir
	 */
	private static String getLiderNick(Scanner sc, boolean follow) {
		String lNick = null;
		String QnickF = "Introduzca el nick del usuario a seguir: ";
		String QnickU = "Introduzca el nick del usuario a dejar de seguir: ";
		if(follow) {
			lNick = Auxiliar.getString(sc, QnickF);	
		} else {
			lNick = Auxiliar.getString(sc, QnickU);
		}
		return lNick;
	}
	
	/*
	 * El proceso cliente trina
	 * @param
	 * Sesion:		sesion del cliente que trina -> interfaz para hacer callback
	 * serGestor:	interfaz con la llamada al metodo del servidor
	 * sc:			scanner para comunicación de los procesos con el usuario
	 * @return:
	 * publicación del trino del proceso cliente
	 * 
	 */
	private static void trinar(
			Sesion sesion, 
			ServicioGestorInterface serGestor,
			Scanner sc) {
		Usuario user = sesion.getUser();
		String nick = user.getNick();
		boolean registrar = true;
		String askTrino = "Introduzca el trino";
		String trinoTxt = Auxiliar.getString(sc, askTrino);
		//Trino trino = elegirTrino(user, sc);
		Trino trino = new Trino(nick, trinoTxt);

		try {
			
			serGestor.trinar(user, trino, registrar);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Servico Autentificar. Registra a un usuario en la aplicacion
	 * 
	 * @autor: rsanchez628@alumno.uned.es Ricardo Sanchez
	 */
	/*
	private static void autentificar(AutentificarInt servicio, Usuario u) {
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
	}*/

	/*
	 * Servico Autentificar. Loguea a un usuario en la aplicacion
	 * 
	 * @autor: rsanchez628@alumno.uned.es Ricardo Sanchez
	 */
	private static boolean loguear(ServicioAutentificacionInterface servicio, Sesion s) throws java.rmi.RemoteException {
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
	private static void registrar(ServicioAutentificacionInterface servicio, Usuario u) {
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

	static Configuracion configurar(int puerto, String servicio) {
		try {

			// IMPORTAR LAS INTERFACES
			String registroAutentificar = "rmi://localhost:" + puerto + "/" + ServicioAutentificacionInterface.class.getCanonicalName();
			String registroGestor = "rmi://localhost:" + puerto + "/" + ServicioGestorInterface.class.getCanonicalName();
			ServicioAutentificacionInterface servicioAutentificar = (ServicioAutentificacionInterface) Naming.lookup(registroAutentificar);
			ServicioGestorInterface servicioGestor = (ServicioGestorInterface) Naming.lookup(registroGestor);

			// registro en el puerto por defecto de rmi
			Registry registry = LocateRegistry.getRegistry(puerto);
			try {
				registry.list();

			} catch (Exception e) {
				registry = LocateRegistry.createRegistry(puerto);
			}

			CallbackUsuarioInterface servidorC = new CallbackUsuarioImpl();

			servidorC = (CallbackUsuarioInterface) UnicastRemoteObject.exportObject(servidorC, 0);
//			Naming.rebind("rmi://localhost:" + puerto + "/" + CallbackInt.class.getCanonicalName(), servidorC);
			Naming.rebind(servicio, servidorC);

			return new Configuracion(servicioAutentificar, servicioGestor, servidorC);

		} catch (Exception e) {
/*
			System.out.println("Excepcion: " + e);
			e.printStackTrace(); */
			System.out.println("El correcto orden para levantar servicios es:");
			System.out.println("1. BBDD");
			System.out.println("2. Servidor");
			System.out.println("3. Clientes");
			System.out.println("El correcto orden para salir es:");
			System.out.println("1. Clientes");
			System.out.println("2. Servidor");
			System.out.println("3. BBDD");
			System.exit(0);
			return new Configuracion(null, null, null);

		}
	}

	// Para chequear el correcto funcionamiento del sistema
	// -----------------------------------------------------
	// BORRAR DESPUÉS

	// --- funciones que muestran por consola la BBDD del servidor
	/*
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
	}*/
/*
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
	}*/
	
	private static void showRegistrados(List<Usuario> l) {
		System.out.println("Usuarios registrados en la aplicacion.");
		for (Usuario e : l) {
			e.show();
			System.out.println();
		}
	}
	
	private static void showLogueados(List<Sesion> l) {
		Usuario u;
		System.out.println("Usuarios actualmente logueados en la aplicacion.");
		for(Sesion s : l) {
			u = s.getUser();
			u.show();
			System.out.println();
		}
	}
	
	private static void listUser(ServicioAutentificacionInterface servidor) throws RemoteException {
		List<Usuario> lu = servidor.getRegistrados();
		List<Sesion> ls = servidor.getLogueados();
		showRegistrados(lu);
		showLogueados(ls);
	}
	
	static void info(String servicio) {
		// showBaneados();
		// showTrinoB();
		// Auxiliar.showSeguidores(this.seguidores);

		System.out.println("Servicio RMI del cliente");
		System.out.println("Servicio: "+servicio);
		//System.out.println("Servicio gestor: "+gestor);
	}
}
