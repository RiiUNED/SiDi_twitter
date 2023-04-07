/**
 * Autor:	Ricardo Sanchez Fernandez
 * email:	rsanchez628@alumno.uned.es
 */

package cliente;

import Interfaces.*;
import Datos.*;

public class CallbackUsuarioImpl implements CallbackUsuarioInterface{
	
	/*
	 * Publica el trino que se le pasa como argumento
	 * param:
	 * 	trino: 		trino a publicar
	 * return: publicacion del trino
	 */
	@Override
	public void publicar (Trino trino) throws java.rmi.RemoteException{
		String firma = trino.GetNickPropietario();
		String m = trino.GetTrino();
		
		System.out.println();
		System.out.print(firma+"# ");
		System.out.println(m);
		System.out.println();
	}

}
