package Comun;

import java.rmi.Remote;
import java.util.*;

/*
 * Interfaz con los servicios Gestor que serán
 * consumidos por los usuarios del servicio
 * @autor: rsanchez628@alumno.uned.es
 * 			Ricardo Sanchez
 */

public interface GestorInt extends Remote{
	
	// usuario envia un trino
	public void enviar(Usuario u, Trino t) throws java.rmi.RemoteException;
	
	// un usuario bloquea a otro
	public void bloquear(Usuario bloqueante, Usuario bloqueado) throws java.rmi.RemoteException;
	
	// un usuario se hace seguidor de otro
	public void seguir(Usuario seguidor, Usuario seguido) throws java.rmi.RemoteException;

	//funciones de prueba. Borrar después
	public HashMap<Usuario, List<Trino>> getTrinos() throws java.rmi.RemoteException;
	public HashMap<Usuario, List<Usuario>> getBloqueados() throws java.rmi.RemoteException;
	public HashMap<Usuario, List<Usuario>> getSeguidos() throws java.rmi.RemoteException;
}
