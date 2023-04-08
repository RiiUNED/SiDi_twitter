/**
 * Autor:	Ricardo Sanchez Fernandez
 * email:	rsanchez628@alumno.uned.es
 */

package Interfaces;

import java.rmi.*;
import java.util.*;

import Datos.*;

/*
 * Interfaz con los servicios Auntentificar que seran consumidos 
 * por los clientes del servicio
 */

public interface ServicioAutentificacionInterface extends Remote {

	// registra un usuario en el sistema
	public boolean registrar(Usuario u) throws java.rmi.RemoteException;

	// loguea al usuario para perimitirle hacer uso del sistema
	public boolean loguear(Sesion s) throws java.rmi.RemoteException;

	// desloguea al usuario cuando sale de la aplicaci�n
	public boolean desloguear(Sesion s) throws java.rmi.RemoteException;

	// comprueba si un usuario est� registrado
	public boolean checkRegistro(Usuario u) throws java.rmi.RemoteException;

	// devuelve la lista de los usuarios registrados en la aplicacion
	public List<Usuario> getRegistrados() throws java.rmi.RemoteException;

	// devuelve la lista de los usuarios logueados en ese momento en la aplicacion
	public List<Sesion> getLogueados() throws java.rmi.RemoteException;
	
	//devuelve el usuario registrado del que se le pase nick
	public Usuario getUser(String nick) throws java.rmi.RemoteException;
	
	//chequea si la contrase�a se corresponde con la registrada al nick del usuario
	//que se proporciono para generar la sesion
	public boolean checkPass(Sesion sesion) throws java.rmi.RemoteException; 
}
