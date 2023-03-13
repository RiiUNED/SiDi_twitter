package cliente;

import java.rmi.RemoteException;
import java.util.*;

import Comun.*;

//Esta clase va fuera del proyecto
class Debug {

	public static void trinar(
			Sesion sesion, 
			GestorInt serGestor,
			Scanner sc) {
		Usuario user = sesion.getUser();
		List<String> mensajes = crearMensajes();
		String mensaje = elegirMensaje(mensajes, sc);

		Trino trino = new Trino(user, mensaje);

		try {
			serGestor.trinar(user, trino);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void registrarUsuario(
			List<Usuario> usuarios, 
			AutentificarInt servicioAutentificar, 
			Scanner sc)
			throws RemoteException {
		Usuario u = elegirUsuario(usuarios, sc);
		AuxCliente.registrar(servicioAutentificar, u);
	}

	public static boolean loguearUsuario(List<Usuario> usuarios, AutentificarInt servicioAutentificar, Sesion s)
			throws RemoteException {

		return AuxCliente.loguear(servicioAutentificar, s);

	}

	// crear usuarios
	public static List<Usuario> crearUsuario() {

		List<Usuario> usuarios = new LinkedList<>();

		// Crear usuarios
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
		usuarios.add(blq1);
		name = "Bloqueado2";
		nick = "BLQ2";
		pass = "SiDi_2024";
		Usuario blq2 = new Usuario(name, nick, pass);
		usuarios.add(blq2);
		name = "Bloqueado3";
		nick = "BLQ3";
		pass = "SiDi_2024";
		Usuario blq3 = new Usuario(name, nick, pass);
		usuarios.add(blq3);

		// Seguidores
		name = "Seguidor1";
		nick = "SGD1";
		pass = "SiDi_2024";
		Usuario sgd1 = new Usuario(name, nick, pass);
		usuarios.add(sgd1);
		name = "Seguidor2";
		nick = "SGD2";
		pass = "SiDi_2024";
		Usuario sgd2 = new Usuario(name, nick, pass);
		usuarios.add(sgd2);
		name = "Seguidor3";
		nick = "SGD3";
		pass = "SiDi_2024";
		Usuario sgd3 = new Usuario(name, nick, pass);
		usuarios.add(sgd3);

		return usuarios;
	}

	// Crear trinos
	public static List<String> crearMensajes() {

		List<String> mensajes = new LinkedList<>();

		System.out.println("creando los trinos...");
		System.out.println();
		String m1 = "Trino 1";
		mensajes.add(m1);
		String m2 = "Trino 2";
		mensajes.add(m2);
		String m3 = "Trino 3";
		mensajes.add(m3);
		String m4 = "Trino 4";
		mensajes.add(m4);
		String m5 = "Trino 5";
		mensajes.add(m5);
		String m6 = "Trino 6";
		mensajes.add(m6);
		mensajes.add("Trino 7");
		mensajes.add("Trino 8");
		mensajes.add("Trino 9");
		mensajes.add("Trino 10");
		mensajes.add("Trino 11");
		mensajes.add("Trino 12");

		return mensajes;
	}

	public static Usuario elegirUsuario(List<Usuario> usuarios, Scanner sc) {
		System.out.println("Elija usuario: 1 Ri, 2 Ro, 3 B1.");
		int usr = sc.nextInt();
		switch (usr) {
		case 1: {
			return usuarios.get(0);
		}
		case 2: {
			return usuarios.get(1);
		}
		case 3: {
			return usuarios.get(2);
		}

		default:
			return null;
		}

	}
	
	public static String elegirMensaje(List<String> mensajes, Scanner sc) {
		System.out.println("Elija mensaje (1-12)");
		int op = sc.nextInt();
		switch (op) {
		case 1: {
			return mensajes.get(0);
		}
		case 2: {
			return mensajes.get(1);
		}
		case 3: {
			return mensajes.get(2);
		}
		case 4: {
			return mensajes.get(3);
		}
		case 5: {
			return mensajes.get(4);
		}
		case 6: {
			return mensajes.get(5);
		}
		case 7: {
			return mensajes.get(6);
		}
		case 8: {
			return mensajes.get(7);
		}
		case 9: {
			return mensajes.get(8);
		}
		case 10: {
			return mensajes.get(9);
		}
		case 11: {
			return mensajes.get(10);
		}
		case 12: {
			return mensajes.get(11);
		}

		default:
			return null;
		}

	}

	public static Sesion crearSesion(
			List<Usuario> usuarios, 
			Scanner sc, 
			Configuracion setup) {
		Usuario u = elegirUsuario(usuarios, sc);
		CallbackInt s = setup.getServidor();
		Sesion sesion = new Sesion(u, s);
		return sesion;
	}

}
