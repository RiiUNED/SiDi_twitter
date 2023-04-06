/**
 * Autor:	Ricardo Sanchez
 * email:	rsanchez628@alumno.uned.es
 */

package Interfaces;

import java.rmi.Remote;

import Datos.*;

/*
 * Interfaz de los callback en los objetos cliente que permite 
 * publicar trinos de los usuarios seguidos en el timeline de 
 * sus seguidores
 */
public interface CallbackUsuarioInterface extends Remote{
	
	/*
	 * Metodo que publica el trino del usuario que es seguido
	 * param:
	 * 	trino:		Trino del usuario que se sigue 
	 * return: publicacion del trino en el timeline del seguidor
	 */
	public void publicar (Trino trino) throws java.rmi.RemoteException;
}
