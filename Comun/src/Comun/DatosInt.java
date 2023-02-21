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

	// a�ade un seguidor
	// public void anSeguidor(Usuario lider, Usuario seguidor) throws
	// java.rmi.RemoteException;

	// borra un seguidor
	// public void delSeguidor(Usuario lider, Usuario seguidor) throws
	// java.rmi.RemoteException;

	// a�ade un mensaje
	public void trinar(Usuario u, Trino t) throws java.rmi.RemoteException;

	// test de la funci�n enviar. BORRAR DESPU�S
	public HashMap<Usuario, List<Trino>> getTrinos() throws java.rmi.RemoteException;

}