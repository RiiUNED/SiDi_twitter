/**
 * Autor:	Ricardo Sanchez Fernandez
 * email:	rsanchez628@alumno.uned.es
 */

package Servicios;

import java.util.*;

import Datos.*;

//Clase con funciones que dan servicio a los distintos procesos
public class Auxiliar {
	
	/*
	 * Recibe datos por consola y genera un usuario
	 * param:
	 * 	sc			scanner del sistema
	 * 	servidor:	bandera para usarse desde el servidor
	 * return: usuario que generan los datos
	 */
	public static Usuario getUsuario(Scanner sc, boolean servidor) {
		String askNombre = "Introduzca el nombre de usuario: ";
		String askNick = "Introduzca el nick del usuario: ";
		String askPass = "Introduzca el pass del usuario: ";
		String name = null;
		String pass = null;
		if(!servidor) {name = Auxiliar.getString(sc, askNombre);}
			String nick = Auxiliar.getString(sc, askNick);
		if(!servidor) {pass = Auxiliar.getString(sc, askPass);}
		Usuario u = new Usuario(name, nick, pass);
		return u;
	}
	
	/*
	 * Recibe un texto con una pregunta y devuelve la respuesta que da
	 * el usuario por consola
	 * @param:
	 * texto:	texto con la pregunta que vera el usuario en consola
	 * sc:		scanner del sistema
	 * @return:	string con respuesta del usuario
	 */
	public static String getString(Scanner sc, String texto) {
		String data = null;
		System.out.println(texto);
		data = sc.nextLine();
		return data;
	}
	
	/*
	 * Muestra por consola los seguidores de cada usuario
	 * param:
	 * 	seguidores: 	estructura de datos que relaciona cada usuario con sus 
	 * 					seguidores
	 * return: publicacion con formato de la estructura de datos por consola
	 */
	public static void showSeguidores(HashMap<Usuario, List<Usuario>> seguidores) {
		List<Usuario> llaves = new LinkedList<Usuario>(seguidores.keySet());
		System.out.println("Mostrando seguidores");
		for(Usuario k : llaves) {
			System.out.println("el usuario");
			k.show();
			System.out.println("tiene como seguidores");
			List<Usuario> s = seguidores.get(k);
			for(Usuario u : s) {
				u.show();
			}
		}
	}
	
	/*
	 * Devuelve los seguidores del usuario que se pasa como argumento
	 * param:
	 * 	seguidores:		estructura de datos que relaciona a los usuarios 
	 * 					con los seguidores que tiene
	 * 	user:			usuario del que se quieren saber los seguidores
	 * return: los seguidores del usuario 
	 */
	public static List<Usuario> getMisSeguidores(
			HashMap<Usuario, List<Usuario>> seguidores, 
			Usuario user){
		List<Usuario> misSeguidores = new LinkedList<Usuario>();
		
		List<Usuario> llaves = new LinkedList<Usuario>(seguidores.keySet());
		for(Usuario llave : llaves) {
			if (llave.identico(user)){
				misSeguidores = seguidores.get(llave);
			}
		}
		
		return misSeguidores;
	}
	
	/*
	 * Devuelve los trinos del usuario que se pasa como argumento
	 * param:
	 * 	trinos: 		estructura de datos que relaciona a los usuarios 
	 * 					con sus trinos
	 * 	user:			usurio del que se quieren saber los trinos
	 * return: los trinos del usuario
	 */
	public static List<Trino> getMisTrinos(
			HashMap<Usuario, List<Trino>> trinos, 
			Usuario user){
		List<Trino> misTrinos = new LinkedList<Trino>();
		
		List<Usuario> llaves = new LinkedList<Usuario>(trinos.keySet());
		for(Usuario llave : llaves) {
			if (llave.identico(user)){
				misTrinos = trinos.get(llave);
			}
		}
		
		return misTrinos;
	}
	
	/*
	 * Comprueba si un usuario tiene seguidores
	 * param:
	 * 	seguidores:		estructura de datos que relaciona a los usuarios con
	 * 					sus seguidores
	 * 	lider:			usuario del que se quiere saber si tiene seguidores
	 * return:
	 * 	true 		-> el usuario tiene seguidores
	 * 	false		-> el usuario no tiene seguidores
	 */
	public static boolean checkLlavesUU(HashMap<Usuario, List<Usuario>> seguidores, Usuario lider) {
		boolean existe = false;
		
			List<Usuario> llaves = new LinkedList<Usuario>(seguidores.keySet());
			
			for(Usuario llave : llaves) {
				existe = existe || llave.identico(lider);
			}
		return existe;
	}
	
	/*
	 * Comprueba si un usuario ha trinado
	 * param:
	 * trinosB:			estructura de datos que relaciona a usuarios y trinos
	 * u:				usurio del que se quiere comprobar si tiene trinos
	 * return:
	 * 	true			-> el usuario tiene trinos
	 * 	false			-> el usuario no tiene trinos
	 */
	public static boolean checkLlavesUT(HashMap<Usuario, List<Trino>> trinosB, Usuario u) {
		boolean existe = false;
		
			List<Usuario> llaves = new LinkedList<Usuario>(trinosB.keySet());
			
			for(Usuario llave : llaves) {
				existe = existe || llave.identico(u);
			}
		return existe;
	}

}
