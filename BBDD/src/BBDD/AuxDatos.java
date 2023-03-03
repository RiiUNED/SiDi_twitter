package BBDD;

import Comun.*;
import java.util.*;

class AuxDatos {
	
	public static boolean contains(List<Usuario> l, Usuario u1) {
		boolean resultado = false;
		
		for(Usuario u : l) {
			resultado = resultado || u.equal(u1);
		}
		return resultado;
	}

}
