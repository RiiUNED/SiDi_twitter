/**
 * Autor: 	Ricardo Sanchez Fernandez
 * Email:	rsanchez628@alumno.uned.es
 */

package Interfaces;

import java.rmi.Remote;
import java.util.HashMap;
import java.util.List;

import Datos.*;

/*
 * Interfaz con los servicios Datos de la BBDD que serán consumidos por el
 * servidor
 */
public interface ServicioDatosInterface extends Remote {

	// registra un usuario en el sistema
	public boolean registrar(Usuario u) throws java.rmi.RemoteException;

	// devuelve la lista de usuarios registrados en la BBDD
	public List<Usuario> getRegistrados() throws java.rmi.RemoteException;

	// loguea al usuario para perimitirle hacer uso del sistema
	public boolean loguear(Sesion s) throws java.rmi.RemoteException;

	// desloguea al usuario cuando sale de la aplicación
	public boolean desloguear(Sesion s) throws java.rmi.RemoteException;

	// devuelve la lista de usuarios loguados en la BBDD
	public List<Sesion> getLogueados() throws java.rmi.RemoteException;

	// banea a un usuario impidiendole hacer uso del sistema
	public boolean banear(Usuario u) throws java.rmi.RemoteException;

	// levanta el baneo a un usuario impidiendole hacer uso del sistema
	public List<Trino> unban(Usuario u) throws java.rmi.RemoteException;

	// chequea que un usuario esté registrado
	public boolean checkRegistro(Usuario u) throws java.rmi.RemoteException;

	// devuelve el usuario registrado del que se le pase nick
	public Usuario getUser(String nick) throws java.rmi.RemoteException;

	// devuelve servidores de los usuarios activos
	public List<CallbackUsuarioInterface> getActivos(Usuario u) throws java.rmi.RemoteException;

	// actualiza los trinos sin enviar a los usuarios desconectados
	public void updatePendientes(Usuario u, Trino t) throws java.rmi.RemoteException;

	// devuelve una lista de trinos que el usuario no publicó estando deslogueado
	public List<Trino> getTrinos(Sesion s) throws java.rmi.RemoteException;

	// añade el trino a la estructura de datos que relaciona usuario y trinos
	public void trinar(Usuario u, Trino t) throws java.rmi.RemoteException;

	// seguir a otro usuario
	public void seguir(Usuario lider, Usuario seguidor) throws java.rmi.RemoteException;

	// abandonar a un lider
	public void abandonar(Sesion ex, Usuario lider) throws java.rmi.RemoteException;

	// borra el trino de un usuario en la BBDD y evita que le llegue a los usuarios
	// que esten desloguados 
	public void borrarTrino(Sesion s, Trino t) throws java.rmi.RemoteException;

	//publica los trinos en los timeline correspondientes
	public void imprimirTrinos() throws java.rmi.RemoteException;

	// chequea si un usuario fue baneado
	public boolean checkBan(Usuario u) throws java.rmi.RemoteException;

	// registra el trino como de un usuario baneado
	public void putTrinoB(Usuario u, Trino t) throws java.rmi.RemoteException;

	// registra el trino con timestamp en la BBDD
	public void registrarTrino(Usuario u) throws java.rmi.RemoteException;

	// chequea si la contraseña se corresponde con la registrada al nick del usuario
	// que se proporciono para generar la sesion
	public boolean checkPass(Sesion sesion) throws java.rmi.RemoteException;
}