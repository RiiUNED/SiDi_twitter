/**
 * Autor:	Ricardo Sanchez Fernandez
 * email:	rsanchez628@alumno.uned.es
 */

package Interfaces;

import java.rmi.Remote;
import java.util.*;

import Datos.*;

/*
 * Interfaz con los servicios Gestor que seran
 * consumidos por los clientes del servicio
 */

public interface ServicioGestorInterface extends Remote {

	// usuario publica un trino
	public void trinar(Usuario u, Trino t, boolean registrar) throws java.rmi.RemoteException;

	// un usuario se hace seguidor de otro
	public void seguir(Sesion sesion, Usuario lider) throws java.rmi.RemoteException;

	// un seguidor abandona a su lider
	public void abandonar(Sesion ex, Usuario lider) throws java.rmi.RemoteException;

	// publica trinos que quedaran pedientes mientras el usuario estuvo deslogueado
	public void updateTrinos(Sesion s) throws java.rmi.RemoteException;
	
	// borra el trino de un usuario en la BBDD y evita que le llegue a los usuarios
	// que esten desloguados 
	public void borrarTrino(Sesion s, Trino t) throws java.rmi.RemoteException;
}
