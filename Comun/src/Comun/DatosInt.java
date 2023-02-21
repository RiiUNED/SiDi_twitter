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

	// añade un seguidor
	// public void anSeguidor(Usuario lider, Usuario seguidor) throws
	// java.rmi.RemoteException;

	// borra un seguidor
	// public void delSeguidor(Usuario lider, Usuario seguidor) throws
	// java.rmi.RemoteException;

	// añade un mensaje
	public void trinar(Usuario u, Trino t) throws java.rmi.RemoteException;

	// test de la función enviar. BORRAR DESPUÉS
	public HashMap<Usuario, List<Trino>> getTrinos() throws java.rmi.RemoteException;

}