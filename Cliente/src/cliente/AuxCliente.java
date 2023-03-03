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
	 * funcion para ofertar al usuario el primer menu que describe el enunciado de la
	 * practica
	 * autor: rsanchez628@alumno.uned.es
	 */

	public static void menu1(Configuracion setup) throws RemoteException { 
	        Scanner sc = new Scanner(System.in);
	        int opcion;

	        do {
	            System.out.println("1. Registrar a un nuevo usuario");
	            System.out.println("2. Hacer login");
	            System.out.println("3. Salir");

	            System.out.print("Ingrese su opci�n: ");
	            opcion = sc.nextInt();

	            switch (opcion) {
	                case 1:
	                    System.out.println("Ha elegido registrar a un nuevo usuario.");
	                    AutentificarInt servicioAutentificar = setup.getAutentificar();
	                    // Crear usuario
	                    List<List<Usuario>> usuarios = crearUsuario();
	                    Usuario userRi = usuarios.get(0).get(0);
	                    servicioAutentificar.registrar(userRi);
	                    
	                    break;
	                case 2:
	                    System.out.println("Ha elegido hacer login.");
	                    // Aqu� puedes agregar el c�digo necesario para hacer login.
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

	// Crear trinos
	public static List<Trino> crearTrinos(List<Usuario> usuarios) {

		List<Trino> trinos = new LinkedList<>();

		Usuario userRi = usuarios.get(0);
		Usuario userRo = usuarios.get(1);
		System.out.println("creando los trinos...");
		System.out.println();
		String messageRi1 = "Hola, me llamo RICARDO.";
		Trino trinoRi1 = new Trino(userRi, messageRi1);
		trinos.add(trinoRi1);
		String messageRi2 = "RICARDO publica su segundo trino.";
		Trino trinoRi2 = new Trino(userRi, messageRi2);
		trinos.add(trinoRi2);
		String messageRo1 = "Hola, me llamo ROBERTO.";
		Trino trinoRo1 = new Trino(userRo, messageRo1);
		trinos.add(trinoRo1);
		String messageRo2 = "ROBERTO publica su segundo trino.";
		Trino trinoRo2 = new Trino(userRo, messageRo2);
		trinos.add(trinoRo2);
		String messageRo3 = "ROBERTO publica su tercer trino.";
		Trino trinoRo3 = new Trino(userRo, messageRo3);
		trinos.add(trinoRo3);

		return trinos;
	}

	// crear usuarios
	public static List<List<Usuario>> crearUsuario() {

		List<List<Usuario>> u = new LinkedList<>();
		List<Usuario> usuarios = new LinkedList<>();
		List<Usuario> bloqueados = new LinkedList<>();
		List<Usuario> seguidores = new LinkedList<>();

		// Crear usuarios
		System.out.println("creando los usuarios...");
		System.out.println();
		String name = "Ricardo";
		String nick = "I000";
		String pass = "SiDi_2023";
		Usuario userRi = new Usuario(name, nick, pass);
		usuarios.add(userRi);

		name = "Roberto";
		nick = "O999";
		pass = "SiDi_2024";
		Usuario userRo = new Usuario(name, nick, pass);
		usuarios.add(userRo);

		// Usuarios bloqueados
		name = "Bloqueado1";
		nick = "BLQ1";
		pass = "SiDi_2024";
		Usuario blq1 = new Usuario(name, nick, pass);
		name = "Bloqueado2";
		nick = "BLQ2";
		pass = "SiDi_2024";
		Usuario blq2 = new Usuario(name, nick, pass);
		name = "Bloqueado3";
		nick = "BLQ3";
		pass = "SiDi_2024";
		Usuario blq3 = new Usuario(name, nick, pass);
		bloqueados.add(blq1);
		bloqueados.add(blq2);
		bloqueados.add(blq3);

		// Seguidores
		name = "Seguidor1";
		nick = "SGD1";
		pass = "SiDi_2024";
		Usuario sgd1 = new Usuario(name, nick, pass);
		name = "Seguidor2";
		nick = "SGD2";
		pass = "SiDi_2024";
		Usuario sgd2 = new Usuario(name, nick, pass);
		name = "Seguidor3";
		nick = "SGD3";
		pass = "SiDi_2024";
		Usuario sgd3 = new Usuario(name, nick, pass);
		seguidores.add(sgd1);
		seguidores.add(sgd2);
		seguidores.add(sgd3);

		u.add(usuarios);
		u.add(bloqueados);
		u.add(seguidores);

		return u;
	}

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



