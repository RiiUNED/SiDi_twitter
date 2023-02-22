package BBDD;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import Comun.*;;

/*Implementacion de los servicios Datos
 * de la BBDD
 * enviar trino
 * bloquear usuario - desbloquear
 * seguir usario - dejar de seguir
 * consumidos por los usuarios del servicio / proceso cliente
 * @autor: rsanchez628@alumno.uned.es
 * 			Ricardo Sanchez
 */

public class DatosImpl extends UnicastRemoteObject implements DatosInt {

	private List<Usuario> registrados = new LinkedList<>();
	private List<Usuario> logueados = new LinkedList<>();
	private List<Usuario> baneados = new LinkedList<>();
	private HashMap<Usuario, List<Trino>> trinos = new HashMap<>();
	private HashMap<Usuario, List<Usuario>> bloqueos = new HashMap<>();
	private HashMap<Usuario, List<Usuario>> seguidores = new HashMap<>();

	protected DatosImpl() throws RemoteException {
		super();
	}

	// registra usuarios en el sistema
	@Override
	public boolean registrar(Usuario u) throws java.rmi.RemoteException {
		if (!this.registrados.contains(u)) {
			this.registrados.add(u);
			return true;
		} else {
			return false;
		}
	}

	// loguea usuarios en el sistema
	@Override
	public boolean loguear(Usuario u) throws java.rmi.RemoteException {
		if (!this.logueados.contains(u)) {
			this.logueados.add(u);
			return true;
		} else {
			return false;
		}
	}

	// banea a un usuario impidiendole hacer uso del sistema
	@Override
	public boolean banear(Usuario u) throws java.rmi.RemoteException{
		if (!this.baneados.contains(u)) {
			this.logueados.add(u);
			return true;
		} else {
			return false;
		}
	}

	public void trinar(Usuario u, Trino t) throws java.rmi.RemoteException {
		if (this.trinos.containsKey(u)) {
			this.trinos.get(u).add(t);
		} else {
			List<Trino> l = new LinkedList<>();
			l.add(t);
			this.trinos.put(u, l);
		}
	}

	public void bloquear(Usuario lider, Usuario bloqueado) throws java.rmi.RemoteException {
		if (this.bloqueos.containsKey(lider)) {
			this.bloqueos.get(lider).add(bloqueado);
		} else {
			List<Usuario> l = new LinkedList<>();
			l.add(bloqueado);
			this.bloqueos.put(lider, l);
		}
	}

	public void desbloquear(Usuario lider, Usuario desbloqueado) throws java.rmi.RemoteException {
		this.bloqueos.get(lider).remove(desbloqueado);
	}

	public void seguir(Usuario lider, Usuario seguidor) throws java.rmi.RemoteException {
		if (this.seguidores.containsKey(lider)) {
			this.seguidores.get(lider).add(seguidor);
		} else {
			List<Usuario> l = new LinkedList<>();
			l.add(seguidor);
			this.seguidores.put(lider, l);
		}
	}

	public void abandonar(Usuario lider, Usuario ex) throws java.rmi.RemoteException {
		this.seguidores.get(lider).remove(ex);
	}

	// test de la función trinar. BORRAR DESPUÉS
	public HashMap<Usuario, List<Trino>> getTrinos() throws java.rmi.RemoteException {
		return this.trinos;
	}

	// test de las funciones bloquear desbloquear. BORRAR DESPUÉS
	public HashMap<Usuario, List<Usuario>> getBloqueados() throws java.rmi.RemoteException {
		return this.bloqueos;
	}

	// test de las funciones seguir abandonar. BORRAR DESPUÉS
	public HashMap<Usuario, List<Usuario>> getSeguidores() throws java.rmi.RemoteException {
		return this.seguidores;
	}
}
