package Comun;

import java.rmi.*;
import java.util.*;

/*
 * Interfaz con los servicios Auntentificar que ser�n consumidos 
 * por los usuarios del servicio
 * @autor: rsanchez628@alumno.uned.es
 * 			Ricardo Sanchez
 */

public interface AutentificarInt extends Remote {

	// registra a los usuarios en el servicio de mensajeria
	public Boolean registro(Usuario u) throws java.rmi.RemoteException;

	// loguea a los usuarios en el servicio de mensajeria
	public Boolean login(Usuario u) throws java.rmi.RemoteException;
	
	// clase auxiliar para chequeo durante la implementaci�n de la interfaz
	// borrar despu�s
	public HashMap<String, Usuario> getBBDD() throws java.rmi.RemoteException;
}
