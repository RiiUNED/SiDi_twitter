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

	private List<Usuario> registrados;
	private List<Sesion> logueados;
	private List<Usuario> baneados;
	private HashMap<Usuario, List<Trino>> trinos;
	private HashMap<Usuario, List<Usuario>> bloqueos;
	private HashMap<Usuario, List<Usuario>> seguidores;

	protected DatosImpl() throws RemoteException {
		super();
		this.registrados = new LinkedList<>();
		this.logueados = new LinkedList<>();
		this.baneados = new LinkedList<>();
		this.trinos = new HashMap<>();
		this.bloqueos = new HashMap<>();
		this.seguidores = new HashMap<>();
	}

	// registra usuarios en el sistema
	@Override
	public boolean registrar(Usuario u) throws java.rmi.RemoteException {
		if (!AuxDatos.containsU(this.registrados, u)) {
			this.registrados.add(u);
			return true;
		} else {
			return false;
		}
	}

	// devuelve la lista de los usuarios registrados en el sistema
	@Override
	public List<Usuario> getRegistrados() throws java.rmi.RemoteException {
		return this.registrados;
	}

	// devuelve la lista de los usuarios logueados en el sistema
	@Override
	public List<Sesion> getLogueados() throws java.rmi.RemoteException{
		return this.logueados;
	}

	// chequea si un usuario esta registrado
	public boolean checkRegistro(Usuario u) throws java.rmi.RemoteException {
		return AuxDatos.containsU(this.registrados, u);
	}

	// chequea si un usuario esta logueado
	public boolean checkLog(Sesion s) throws java.rmi.RemoteException {
		return AuxDatos.containsS(this.logueados, s);
	}

	// loguea usuarios en el sistema
	@Override
	public boolean loguear(Sesion s) throws java.rmi.RemoteException {
		// if (!AuxDatos.contains(this.logueados, u)) {
		if (!this.checkLog(s)) {
			this.logueados.add(s);
			return true;
		} else {
			return false;
		}
	}

	// banea a un usuario impidiendole hacer uso del sistema
	@Override
	public boolean banear(Usuario u) throws java.rmi.RemoteException {
		if (!this.baneados.contains(u)) {
			this.baneados.add(u);
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
		if (Auxiliar.checkLlaves(this.seguidores, lider)) {
			System.out.println("reutiliza lista");
			List<Usuario> misSeguidores = Auxiliar.getMisSeguidores(this.seguidores, lider);
			misSeguidores.add(seguidor);
			//this.seguidores.get(lider).add(seguidor); //Aquí está el problema
		} else {
			List<Usuario> l = new LinkedList<>();
			l.add(seguidor);
			this.seguidores.put(lider, l);
		}
		System.out.println("Listado de seguidores");
		Auxiliar.showSeguidores(this.seguidores);
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
