/**
 * Autor: 	Ricardo Sanchez Fernandez
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
public class AuxCliente {

	/*
	 * Publica el primer menu que ofrece la aplicacion al usuario
	 * param:
	 * 	setup:			interfaces de con los servicios
	 * 	servicio:		informacion con la URL del callback de los clientes
	 * return: publicacion del primer menu
	 */
	public static void menu1(Configuracion setup, String servicio) throws RemoteException {
		Scanner sc = new Scanner(System.in); //Scanner del sistema
		boolean servidor = false; // bandera para seleccionar opciones en distintas funciones
		int opcion; // varible para recoger las opciones que vaya introduciendo el usuario
		//Interfaz con los servicios autentificacion proporcionados por el servidor
		ServicioAutentificacionInterface servicioAutentificar = setup.getAutentificar(); 

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
	
	/*
	 * Comprueba si el password facilitada por el usuario esta registrada
	 * en la BBDD para el nick
	 * param:
	 * sesion:		datos del cliente proporcionados por el usuario
	 * return:
	 * 	true		-> el password es correcto
	 * 	false		-> el password es incorrecto 
	 */
	private static boolean checkPass(
			ServicioAutentificacionInterface servicio, 
			Sesion sesion) throws RemoteException {
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
	 * Publica el segundo menu que ofrece la aplicacion al usuario
	 * param:
	 * 	setup:			interfaces con los servicios
	 * 	sc:				scanner del sistema
	 * 	sesion:			datos del cliente encapsulados junto a la interfaz que 
	 * 					proporciona el callback
	 * 	servicio:		informacion con la URL del callback de los clientes
	 * return: publicacion del segundo menu
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
				trinar(sesion, serGestor, sc);
				break;
			case 3:
				System.out.println("Ha elegido listar usuarios del sistema.");
				listUser(serAuten);
				break;
			case 4:
				System.out.println("Ha elegido seguir a.");
				follow = true;
				String lNick = getLiderNick(sc, follow);
				lider = serAuten.getUser(lNick);
				serGestor.seguir(sesion, lider);
				break;
			case 5:
				System.out.println("Ha elegido dejar de seguir a.");
				follow = false;
				lider = serAuten.getUser(getLiderNick(sc, follow));
				serGestor.abandonar(sesion, lider);
				break;
			case 6:
				System.out.println("Borrar trino.");
				Usuario user = sesion.getUser();
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
	 * param
	 * sesion:		datos encapsulador del cliente que trina junto a la 
	 * 				interfaz para hacer callback
	 * serGestor:	interfaz con la llamada al metodo del servidor
	 * sc:			scanner para comunicación de los procesos con el usuario
	 * return:
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
		Trino trino = new Trino(nick, trinoTxt);

		try {
			
			serGestor.trinar(user, trino, registrar);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}


	/*
	 * Loguea al usuario en la aplicacion
	 * param:
	 * 	servicio:		interfaz con los servicios autentificar del servidor
	 * 	sesion:			datos del cliente encapsulados junto a la interfaz que 
	 * 					proporciona el callback
	 * return:
	 * 	true			-> el usuario se loguea correctamente
	 * 	false			-> el usuario no sea logueado
	 */
	private static boolean loguear(
			ServicioAutentificacionInterface servicio, 
			Sesion s) throws java.rmi.RemoteException {
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
				e.printStackTrace();
				return false;
			}
		}
	}

	/*
	 * Registra al usuario en la aplicacion
	 * param:
	 * 	servicio:		interfaz con los servicios autentificar del servidor
	 * 	usuario:		datos del cliente encapsulados 
	 * return: usuario registrado
	 */
	private static void registrar(
			ServicioAutentificacionInterface servicio, 
			Usuario u) {
		u.show();
		try {
			if (servicio.registrar(u)) {
				System.out.println("el usuario se ha registrado correctamente");

			} else {
				System.out.println("ya existe un usuario registrado con nick: " + u.getNick());
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Importa las interfaces que publica el servidor para que los clientes
	 * puedan consumir sus servicios:
	 * 	-> servicio autentificar
	 * 	-> servicio gestor
	 * Publica la interfaz del cliente en la que se puede hacer callback para
	 * mostrar los trinos
	 * param:
	 * 	puerto: 		puerto del que se importan las interfaces del servidor
	 * 	servicio:		informacion con la URL del callback de los clientes
	 * return:
	 * 	interfaces con los servicios del servidor importadas
	 * 	interfaz con el callbak del cliente publicada
	 */
	protected static Configuracion configurar(int puerto, String servicio) {
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

			// Publica la interfaz del cliente
			CallbackUsuarioInterface servidorC = new CallbackUsuarioImpl();

			servidorC = (CallbackUsuarioInterface) UnicastRemoteObject.exportObject(servidorC, 0);
			Naming.rebind(servicio, servidorC);

			return new Configuracion(servicioAutentificar, servicioGestor, servidorC);

		} catch (Exception e) {
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

	/*
	 * informa de la URL del servicio RMI del callback del proceso cliente
	 * param:
	 * 	servicio:		informacion con la URL del callback de los clientes
	 * return: 	salida por consola con la informacion sobre la URL del servicio
	 * 			RMI del callback del proceso cliente
	 */
	private static void info(String servicio) {
		System.out.println("Servicio RMI del cliente");
		System.out.println("Servicio: "+servicio);
	}
	
	/*
	 * Informa al usuario de usuarios registrados y logueados en la aplicacion
	 * param:
	 * 	servidor: 		interfaz con los servicios de autentificacion publicados
	 * 					por el servidor.
	 * return:
	 * 	publicacion de los usuarios registrados
	 * 	publicacion de los usuarios logueados
	 */
	private static void listUser(ServicioAutentificacionInterface servidor) throws RemoteException {
		List<Usuario> lu = servidor.getRegistrados();
		List<Sesion> ls = servidor.getLogueados();
		showRegistrados(lu);
		showLogueados(ls);
	}
	
	/*
	 * Muestra por consola los usuarios registrados en la aplicacion
	 * param: 
	 * 	l:		Estructura de datos con los usuarios registrados
	 * return: publicacion de los usuarios registrados
	 */
	private static void showRegistrados(List<Usuario> l) {
		System.out.println("Usuarios registrados en la aplicacion.");
		for (Usuario e : l) {
			e.show();
			System.out.println();
		}
	}
	
	/*
	 * Muestra por consola los usuarios logueados en la aplicacion
	 * param: 
	 * 	l:		Estructura de datos con los usuarios logueados
	 * return: publicacion de los usuarios logueados
	 */
	private static void showLogueados(List<Sesion> l) {
		Usuario u;
		System.out.println("Usuarios actualmente logueados en la aplicacion.");
		for(Sesion s : l) {
			u = s.getUser();
			u.show();
			System.out.println();
		}
	}
}
