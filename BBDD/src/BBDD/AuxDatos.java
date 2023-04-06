package BBDD;

import java.util.*;

import Interfaces.*;
import Datos.*;

class AuxDatos {

	public static void showMapTrino(HashMap<Usuario, List<Trino>> mt) {
		List<Usuario> lu = new LinkedList<Usuario>(mt.keySet());
		for (Usuario u : lu) {
			System.out.println("Usuario: ");
			u.show();
			List<Trino> lt = mt.get(u);
			System.out.println("Trinos: ");
			for (Trino t : lt) {
				//t.showTrino();
				System.out.println(t.toString());
			}
		}
	}
	
	//informa de la URL del servicio RMI de la BBDD
	static void info(String servicio){
		//showBaneados();
		//showTrinoB();
		//Auxiliar.showSeguidores(this.seguidores);
		
		System.out.println("Servicio RMI de la BBDD");
		System.out.println(servicio);
	}

	// muestra listas de usuarios
	public static void showLU(List<Usuario> lu) {
		for (Usuario u : lu) {
			u.show();
		}
	}

	// Comprueba si una lista contiene un usuario
	public static boolean containsU(List<Usuario> l, Usuario u1) {
		boolean resultado = false;
		for (Usuario u : l) {
			resultado = resultado || u.identico(u1);
		}
		return resultado;
	}

	// Comprueba si una lista contiene una sesion
	public static boolean containsS(List<Sesion> l, Sesion s1) {
		boolean resultado = false;

		for (Sesion s : l) {
			resultado = resultado || s.identica(s1);
		}
		return resultado;
	}

	// Devuelve usuarios de los seguidores no logueados
	public static List<Usuario> getInactivos(List<Usuario> misSeguidores, List<Sesion> logueados) {

		List<Usuario> inactivos = new LinkedList<Usuario>();
		boolean conectado = false;

		for (Usuario seguidor : misSeguidores) {
			conectado = false;
			for (Sesion logueado : logueados) {
				if (seguidor.identico(logueado.getUser())) {
					conectado = true;
				}
			}
			if (!conectado) {
				inactivos.add(seguidor);
			}
		}

		return inactivos;
	}

	// Devuelve los trinos que tenga un usuario que se la para por parametro en
	// espera
	// elimina el usuario de los trinos que lo tuvieran en espera
	public static List<Trino> getTargetedKeyTU(HashMap<Trino, List<Usuario>> pendientes, Usuario user) {
		List<Trino> sinPublicar = new LinkedList<Trino>();
		List<Trino> llaves = new LinkedList<Trino>(pendientes.keySet());

		for (Trino k : llaves) {
			List<Usuario> enEspera = pendientes.get(k);
			if (containsU(enEspera, user)) {
				sinPublicar.add(k);
				pendientes.remove(k);
				enEspera = removeU(enEspera, user);
				pendientes.put(k, enEspera);
			}
		}

		return sinPublicar;
	}

	// Devuelve los trinos que tenga un usuario que se la para por parametro en
	// espera
	// elimina el usuario de los trinos que lo tuvieran en espera
	public static List<Trino> getTargetedKeyUT(HashMap<Usuario, List<Trino>> trinosB, Usuario user) {
		List<Trino> enEspera = new LinkedList<Trino>();
		Iterator<Map.Entry<Usuario, List<Trino>>> iterador = trinosB.entrySet().iterator();
		while (iterador.hasNext()) {
		    Map.Entry<Usuario, List<Trino>> entrada = iterador.next();
		    if (entrada.getKey().identico(user)) {
		    	enEspera = entrada.getValue();
		        iterador.remove();
		    }
		}
		return enEspera;
	}

	// elimina un usuario de una lista
	public static List<Usuario> removeU(List<Usuario> usuarios, Usuario user) {
		for (Iterator<Usuario> iter = usuarios.iterator(); iter.hasNext();) {
			Usuario u1 = iter.next();
			if (u1.identico(user)) {
				iter.remove();
			}
		}
		return usuarios;
	}

	// elimina un trino de una lista
	public static List<Trino> removeT(List<Trino> trinos, Trino t) {
		for (Iterator<Trino> iter = trinos.iterator(); iter.hasNext();) {
			Trino t1 = iter.next();
			if (t1.equals(t)) {
				iter.remove();
			}
		}
		return trinos;
	}

}
