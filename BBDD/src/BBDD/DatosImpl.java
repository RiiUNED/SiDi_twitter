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
	private HashMap<Trino, List<Usuario>> pendientes;

	protected DatosImpl() throws RemoteException {
		super();
		this.registrados = new LinkedList<Usuario>();
		this.logueados = new LinkedList<Sesion>();
		this.baneados = new LinkedList<Usuario>();
		this.trinos = new HashMap<Usuario, List<Trino>>();
		this.bloqueos = new HashMap<Usuario, List<Usuario>>();
		this.seguidores = new HashMap<Usuario, List<Usuario>>();
		this.pendientes = new HashMap<Trino, List<Usuario>>();
	}

	// Devuelve servidores de los usuarios activos
	@Override
	public List<CallbackInt> getActivos(Usuario u) throws java.rmi.RemoteException {

		List<CallbackInt> activos = new LinkedList<CallbackInt>();
		HashMap<Usuario, List<Usuario>> seguidores = this.seguidores;
		List<Usuario> misSeguidores = Auxiliar.getMisSeguidores(seguidores, u);

		for (Usuario seguidor : misSeguidores) {
			for (Sesion s : this.logueados) {
				if (seguidor.identico(s.getUser())) {
					activos.add(s.getServidor());
				}
			}
		}

		return activos;
	}

	// actualiza los trinos sin enviar a los usuarios desconectados
	@Override
	public void updatePendientes(Usuario u, Trino t) throws java.rmi.RemoteException {
		List<Usuario> inactivos = new LinkedList<Usuario>();
		List<Usuario> misSeguidores = Auxiliar.getMisSeguidores(seguidores, u);
		
		inactivos = AuxDatos.getInactivos(misSeguidores, logueados);
		
		this.pendientes.put(t, inactivos);
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
	public List<Sesion> getLogueados() throws java.rmi.RemoteException {
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

	// desloguea al usuario cuando sale de la aplicación
	@Override
	public boolean desloguear(Sesion s) throws java.rmi.RemoteException {

		for (Iterator<Sesion> iter = this.logueados.iterator(); iter.hasNext();) {
			Sesion s1 = iter.next();
			if (s1.identica(s)) {
				iter.remove();
			}
		}
		return !AuxDatos.containsS(this.logueados, s);
	};

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
		if (Auxiliar.checkLlavesU(this.seguidores, lider)) {
			List<Usuario> misSeguidores = Auxiliar.getMisSeguidores(this.seguidores, lider);
			misSeguidores.add(seguidor);
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
