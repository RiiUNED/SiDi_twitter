package Comun;

import java.rmi.*;
import java.util.HashMap;

/*
 * Interfaz remota del servidor
 * @autor: rsanchez628@alumno.uned.es
 * 			Ricardo Sanchez	
 */

public interface ServerInt extends Remote {
	
	/*
	 * Registra usuarios en la aplicacion
	 * @autor: rsanchez628@alumno.uned.es
	 * 			Ricardo Sanchez
	 */
	//autenticar
	
	//interfaces de prueba para el paso de objetos desde el cliente
	public String autenticarse1 (Usuario u) throws java.rmi.RemoteException;
	
	public String autenticarse2 (String name, String nick, String pass) throws java.rmi.RemoteException;
	
	public String decirHola () throws java.rmi.RemoteException;

}
	