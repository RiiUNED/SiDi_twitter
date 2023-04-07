/**
 * Autor:	Ricardo Sanchez
 * Email:	rsanchez628@alumno.uned.es
 */
package BBDD;

import java.util.*;

import Interfaces.*;
import Servicios.Auxiliar;
import Datos.*;

//Clase con funciones auxiliares de la clase BBDD
public class AuxDatos {

	/*
	 * Elimina un trino de la estructura de datos que relaciona usuarios
	 * y trinos
	 * param:
	 * 	u:		usuario del que se quiere eliminar el trino
	 * 	t:		trino que se quiere eliminar
	 * 	ts: 	estructura de datos que elimina usuarios y trinos
	 * return: estructura de datos actualizada
	 */
	protected static void borrarRegistro(
			Usuario u, 
			Trino t,
			HashMap<Usuario, List<Trino>> ts) {
		List<Trino> misTrinos = Auxiliar.getMisTrinos(ts, u);
		misTrinos = AuxDatos.removeT(misTrinos, t);
	}

	/*
	 * eliminar trinos que no han sido recibidos por usuarios deslogueados
	 * param:
	 * 	t:		trino que se borrara
	 * 	p:		estructura de datos que relaciona trinos y usuarios deslogueados
	 * return: estructura de datos actualizada 
	 */
	protected static void borrarPendientes(
			Trino t,
			HashMap<Trino, List<Usuario>> p) {
		List<Trino> llaves = new LinkedList<Trino>(p.keySet());
		for (Trino llave : llaves) {
			if (llave.equals(t)) {
				p.remove(llave);
			}
		}
	}

	/*
	 * chequea si un usuario esta logueado en el sistema param: u: usuario del que
	 * se quiere comprobar si esta logueado en el sistema return: true -> el usuario
	 * esta logueado en el sistema false -> el usuario no esta logueado en el
	 * sistema
	 */
	protected static boolean checkLog(Sesion s, List<Sesion> logueados) throws java.rmi.RemoteException {
		return AuxDatos.containsS(logueados, s);
	}

	/*
	 *informa de la URL del servicio RMI de la BBDD 
	 *param: 
	 * servicio: 		informacion con la URL del serivicio RMI 
	 * 					de la BBDD 
	 *return: salida por consola con la informacion sobre la 
	 *URL del servicio RMI de la BBDD
	 */
	protected static void info(String servicio) {
		System.out.println("Servicio RMI de la BBDD");
		System.out.println(servicio);
	}

	/*
	 *Comprueba si Estructura de datos contiene un usuario
	 *param:
	 *	l:		Estructura de datos
	 *	u1:		Usuario que se quiere comprobar
	 *return:
	 *	true	-> contiene al usuario
	 *	false	-> no contiene al usuario
	 */
	protected static boolean containsU(List<Usuario> l, Usuario u1) {
		boolean resultado = false;
		for (Usuario u : l) {
			resultado = resultado || u.identico(u1);
		}
		return resultado;
	}

	/*
	 *Comprueba si Estructura de datos contiene una sesion
	 *param:
	 *	l:		Estructura de datos
	 *	s1:		sesion que se quiere comprobar
	 *return:
	 *	true	-> contiene la sesion
	 *	false	-> no contiene la sesion
	 */
	protected static boolean containsS(List<Sesion> l, Sesion s1) {
		boolean resultado = false;

		for (Sesion s : l) {
			resultado = resultado || s.identica(s1);
		}
		return resultado;
	}

	/*
	 * Devuelve usuarios de los seguidores no logueados de un usuario
	 * param:
	 * 	misSeguidores:		seguidores de un usuario
	 * 	loguados:			usuarios logueados
	 * return: Estructura de datos con los seguidores no logueados
	 */
	protected static List<Usuario> getInactivos(List<Usuario> misSeguidores, List<Sesion> logueados) {

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

	/*
	 * Devuelve los trinos que no haya recibido un usuario deslogueado
	 * Elminia al usuario de la estructura de datos que relaciona usuarios 
	 * deslogueados y trinos en espera
	 * param:
	 * pendientes:		estructura de datos que relaciona trinos pendientes y 
	 * 					usuarios deslogueados
	 * usuario:			usuario del que se quieren conseguir los trinos en 
	 * 					espera
	 * return: trinos en espera del usuario deslogueado
	 * 	 
	 */
	protected static List<Trino> getTargetedKeyTU(HashMap<Trino, List<Usuario>> pendientes, Usuario user) {
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

	/*
	 * Devuelve los trinos que no haya recibido un usuario deslogueado
	 * Elminia al usuario de la estructura de datos que relaciona usuarios 
	 * deslogueados y trinos en espera
	 * param:
	 * pendientes:		estructura de datos que relaciona trinos pendientes y 
	 * 					usuarios deslogueados
	 * usuario:			usuario del que se quieren conseguir los trinos en 
	 * 					espera
	 * return: trinos en espera del usuario deslogueado
	 * 	 
	 */
	protected static List<Trino> getTargetedKeyUT(HashMap<Usuario, List<Trino>> trinosB, Usuario user) {
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

	/*
	 * Elimina un usuario de una lista de usuarios
	 * param:
	 * 	usuarios:		lista de usuarios
	 * 	user:			usuario a eliminar
	 * return: lista actualizada
	 */
	protected static List<Usuario> removeU(List<Usuario> usuarios, Usuario user) {
		for (Iterator<Usuario> iter = usuarios.iterator(); iter.hasNext();) {
			Usuario u1 = iter.next();
			if (u1.identico(user)) {
				iter.remove();
			}
		}
		return usuarios;
	}

	/*
	 * Elimina un trino de una lista de usuarios
	 * param:
	 * 	trinos:		lista de trinos
	 * 	t:			trino a eliminar
	 * return: lista actualizada
	 */
	protected static List<Trino> removeT(List<Trino> trinos, Trino t) {
		for (Iterator<Trino> iter = trinos.iterator(); iter.hasNext();) {
			Trino t1 = iter.next();
			if (t1.equals(t)) {
				iter.remove();
			}
		}
		return trinos;
	}

}
