package Comun;

import java.rmi.Remote;
import java.util.HashMap;
import java.util.List;

/**
 * Interfaz con los servicios Datos de la BBDD que serán consumidos por el
 * servidor
 * 
 * @author rsanchez628@alumno.uned.es Ricardo Sanchez
 *
 */
public interface DatosInt extends Remote {

	// --------------------------- CONSUMIDAS POR EL SERVICIO AUTENTIFICACION
	// registra un usuario en el sistema
	public boolean registrar(Usuario u) throws java.rmi.RemoteException;

	// devuelve la lista de usuarios registrados en la BBDD
	public List<Usuario> getRegistrados() throws java.rmi.RemoteException;

	// loguea al usuario para perimitirle hacer uso del sistema
	public boolean loguear(Sesion s) throws java.rmi.RemoteException;

	// devuelve la lista de usuarios loguados en la BBDD
	public List<Sesion> getLogueados() throws java.rmi.RemoteException;

	// banea a un usuario impidiendole hacer uso del sistema
	public boolean banear(Usuario u) throws java.rmi.RemoteException;

	// chequea que un usuario esté registrado
	public boolean checkRegistro(Usuario u) throws java.rmi.RemoteException;

	// --------------------------- CONSUMIDAS POR EL SERVICIO GESTOR
	// --------------------------- trinar
	// añade un mensaje
	public void trinar(Usuario u, Trino t) throws java.rmi.RemoteException;

	// --------------------------- bloquear
	// bloquear a un usuario
	public void bloquear(Usuario lider, Usuario bloqueado) throws java.rmi.RemoteException;

	// desbloquear a un usuario
	public void desbloquear(Usuario lider, Usuario desbloqueado) throws java.rmi.RemoteException;

	// --------------------------- seguir
	// seguir a otro usuario
	public void seguir(Usuario lider, Usuario seguidor) throws java.rmi.RemoteException;

	// abandonar a un lider
	public void abandonar(Usuario lider, Usuario ex) throws java.rmi.RemoteException;

	// --------------------------- test BORRAR DESPUÉS
	// test de la función enviar. BORRAR DESPUÉS
	public HashMap<Usuario, List<Trino>> getTrinos() throws java.rmi.RemoteException;

	// test de las funciones bloquear desbloquear. BORRAR DESPUÉS
	public HashMap<Usuario, List<Usuario>> getBloqueados() throws java.rmi.RemoteException;

	// test de las funciones seguir abandonar. BORRAR DESPUÉS
	public HashMap<Usuario, List<Usuario>> getSeguidores() throws java.rmi.RemoteException;

}