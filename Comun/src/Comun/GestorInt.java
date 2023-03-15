package Comun;

import java.rmi.Remote;
import java.util.*;

/*
 * Interfaz con los servicios Gestor que ser�n
 * consumidos por los usuarios del servicio
 * @autor: rsanchez628@alumno.uned.es
 * 			Ricardo Sanchez
 */

public interface GestorInt extends Remote {

	// usuario envia un trino
	public void trinar(Usuario u, Trino t) throws java.rmi.RemoteException;

	// un usuario bloquea a otro
	public void bloquear(Usuario lider, Usuario bloqueado) throws java.rmi.RemoteException;

	// un usuario desbloquea a otro
	public void desbloquear(Usuario lider, Usuario desbloqueado) throws java.rmi.RemoteException;

	// un usuario se hace seguidor de otro
	public void seguir(Sesion sesion, Usuario lider) throws java.rmi.RemoteException;

	// un seguidor abandona a su lider
	public void abandonar(Usuario lider, Usuario ex) throws java.rmi.RemoteException;
	
	// publica trinos que quedaran pedientes mientras el usuario estuvo deslogueado
	public void updateTrinos(Sesion s) throws java.rmi.RemoteException;

	// funciones de prueba. Borrar despu�s
	public HashMap<Usuario, List<Trino>> getTrinos() throws java.rmi.RemoteException;

	public HashMap<Usuario, List<Usuario>> getBloqueados() throws java.rmi.RemoteException;

	public HashMap<Usuario, List<Usuario>> getSeguidores() throws java.rmi.RemoteException;
}
