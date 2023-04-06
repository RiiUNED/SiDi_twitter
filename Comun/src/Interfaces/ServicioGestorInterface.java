/**
 * Autor:	Ricardo Sanchez
 * email:	rsanchez628@alumno.uned.es
 */

package Interfaces;

import java.rmi.Remote;
import java.util.*;

import Datos.*;

/*
 * Interfaz con los servicios Gestor que serán
 * consumidos por los clientes del servicio
 */

public interface ServicioGestorInterface extends Remote {

	// usuario publica un trino
	public void trinar(Usuario u, Trino t, boolean registrar) throws java.rmi.RemoteException;

	// un usuario bloquea a otro
	//public void bloquear(Usuario lider, Usuario bloqueado) throws java.rmi.RemoteException;

	// un usuario desbloquea a otro
	//public void desbloquear(Usuario lider, Usuario desbloqueado) throws java.rmi.RemoteException;

	// un usuario se hace seguidor de otro
	public void seguir(Sesion sesion, Usuario lider) throws java.rmi.RemoteException;

	// un seguidor abandona a su lider
	public void abandonar(Sesion ex, Usuario lider) throws java.rmi.RemoteException;

	// publica trinos que quedaran pedientes mientras el usuario estuvo deslogueado
	public void updateTrinos(Sesion s) throws java.rmi.RemoteException;

	// devuelve la lista de los usuarios registrados en la aplicacion
	///public List<Usuario> getRegistrados() throws java.rmi.RemoteException;

	// devuelve la lista de los usuarios logueados en ese momento en la aplicacion
	//public List<Sesion> getLogueados() throws java.rmi.RemoteException;
	
	// borra el trino de un usuario en la BBDD y evita que le llegue a los usuarios
	// que esten desloguados 
	public void borrarTrino(Sesion s, Trino t) throws java.rmi.RemoteException;

	//Devuelve la estructura de datos que relaciona usuarios y trinos
	public HashMap<Usuario, List<Trino>> getTrinos() throws java.rmi.RemoteException;

	//public HashMap<Usuario, List<Usuario>> getBloqueados() throws java.rmi.RemoteException;

	//Devuelve la estructura de datos que relaciona usuarios con sus seguidores
	public HashMap<Usuario, List<Usuario>> getSeguidores() throws java.rmi.RemoteException;
}
