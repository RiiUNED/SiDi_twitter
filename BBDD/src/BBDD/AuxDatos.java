package BBDD;

import Comun.*;
import java.util.*;

class AuxDatos {

	public static boolean containsU(List<Usuario> l, Usuario u1) {
		boolean resultado = false;
		for (Usuario u : l) {
			resultado = resultado || u.identico(u1);
		}
		return resultado;
	}

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

}
