package Servidor;

import java.rmi.*;
import java.rmi.server.*;
//import java.util.HashMap;

import Comun.ServerInt;
import Comun.Usuario;


/*
 * Implementa la interfaz remota del servidor ServerInt
 * @autor: rsanchez628@alumno.uned.es
 * 			Ricardo Sanchez
 */
public class ServerImp extends UnicastRemoteObject implements ServerInt{
	
	public ServerImp() throws RemoteException{
		super();
	}
	
	/*
	 * Registra usuarios en la aplicacion
	 * @autor: rsanchez628@alumno.uned.es
	 * 			Ricardo Sanchez
	 */
	//autenticar
	
	/*
	 * Test para ver si el problema est� enviando el objeto Usuario
	 */
	public String autenticarse1 (Usuario u) throws java.rmi.RemoteException{
		return u.getName() + u.getNick();
	}
	public String autenticarse2 (String name, String nick, String pass) throws java.rmi.RemoteException {
		
		Usuario u = new Usuario(name, nick, pass);
		return u.getName() + " ** " + u.getNick();
		
	}
	
	public String decirHola () throws java.rmi.RemoteException{
		return "HOLA";
	}
}