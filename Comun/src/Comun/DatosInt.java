package Comun;

import java.rmi.Remote;
import java.util.HashMap;
import java.util.List;

/**
 * Interfaz con los servicios Datos de la BBDD que ser�n consumidos por el
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

	// desloguea al usuario cuando sale de la aplicaci�n
	public boolean desloguear(Sesion s) throws java.rmi.RemoteException;

	// devuelve la lista de usuarios loguados en la BBDD
	public List<Sesion> getLogueados() throws java.rmi.RemoteException;

	// banea a un usuario impidiendole hacer uso del sistema
	public boolean banear(Usuario u) throws java.rmi.RemoteException;

	// chequea que un usuario est� registrado
	public boolean checkRegistro(Usuario u) throws java.rmi.RemoteException;

	// --------------------------- CONSUMIDAS POR EL SERVICIO GESTOR
	// devuelve servidores de los usuarios activos
	public List<CallbackInt> getActivos(Usuario u) throws java.rmi.RemoteException;
	
	// actualiza los trinos sin enviar a los usuarios desconectados
	public void updatePendientes(Usuario u, Trino t) throws java.rmi.RemoteException;
	
	// devuelve una lista de trinos que el usuario no public� estando deslogueado
	public List<Trino> getTrinos(Sesion s) throws java.rmi.RemoteException;
	
	// --------------------------- trinar
	// a�ade un mensaje
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
	public void abandonar(Sesion ex, Usuario lider) throws java.rmi.RemoteException;

	// --------------------------- test BORRAR DESPU�S
	// test de la funci�n enviar. BORRAR DESPU�S
	public HashMap<Usuario, List<Trino>> getTrinos() throws java.rmi.RemoteException;

	// test de las funciones bloquear desbloquear. BORRAR DESPU�S
	public HashMap<Usuario, List<Usuario>> getBloqueados() throws java.rmi.RemoteException;

	// test de las funciones seguir abandonar. BORRAR DESPU�S
	public HashMap<Usuario, List<Usuario>> getSeguidores() throws java.rmi.RemoteException;
	
	/* borar el trino de un usuario en la BBDD y evita que le llegue a los usuarios
	 * que lo est�n desloguados 
	 */
	public void borrarTrino(Sesion s, Trino t) throws java.rmi.RemoteException;
	
	public void imprimirTrinos() throws java.rmi.RemoteException;

}