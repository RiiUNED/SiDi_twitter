/**
 * Autor: 	Ricardo Sanchez
 * Email:	rsanchez628@alumno.uned.es
 */

package Servidor;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;

import java.util.*;

import Interfaces.*;
import Datos.*;

/*
 * Implementacion de los servicios Gestor de la aplicacion
 */

public class ServicioGestorImpl extends UnicastRemoteObject implements ServicioGestorInterface {

	private ServicioDatosInterface servicioDatos;

	//Constructor de la clase
	public ServicioGestorImpl() throws RemoteException {
		super();
		int puerto = Registry.REGISTRY_PORT;
		this.servicioDatos = AuxServidor.getServicioDatos(puerto);
		// this.pendientes = new HashMap<Trino, List<Usuario>>();
	}

	// el usuario publica un trino
	public void trinar(Usuario u, Trino trino, boolean registrar) throws java.rmi.RemoteException {
		if (servicioDatos.checkBan(u)) {
			servicioDatos.putTrinoB(u, trino);
		} else {
			this.servicioDatos.trinar(u, trino);
			List<CallbackUsuarioInterface> activos = new LinkedList<CallbackUsuarioInterface>();
			activos = this.servicioDatos.getActivos(u);
			AuxServidor.publicar(activos, trino);
			this.servicioDatos.updatePendientes(u, trino);
		}
		if (registrar) {
			servicioDatos.registrarTrino(u);
		}
	}

	// el usuario actualiza el timeline
	public void updateTrinos(Sesion s) throws java.rmi.RemoteException {
		List<Trino> sinPublicar = servicioDatos.getTrinos(s);

		CallbackUsuarioInterface servidor = s.getServidor();
		List<CallbackUsuarioInterface> l = new LinkedList<CallbackUsuarioInterface>();
		l.add(servidor);

		for (Trino t : sinPublicar) {
			AuxServidor.publicar(l, t);
		}
	};

	/*
	 * // un usuario bloquea a otro public void bloquear(Usuario lider, Usuario
	 * bloqueado) throws java.rmi.RemoteException {
	 * this.servicioDatos.bloquear(lider, bloqueado); }
	 * 
	 * // un usuario desbloquea a otro public void desbloquear(Usuario lider,
	 * Usuario desbloqueado) throws java.rmi.RemoteException {
	 * this.servicioDatos.desbloquear(lider, desbloqueado); }
	 */

	// un usuario sigue a otro
	public void seguir(Sesion sesion, Usuario lider) throws java.rmi.RemoteException {
		Usuario seguidor = sesion.getUser();
		this.servicioDatos.seguir(lider, seguidor);
	}

	// un seguidor abandona a su lider
	public void abandonar(Sesion ex, Usuario lider) throws java.rmi.RemoteException {
		this.servicioDatos.abandonar(ex, lider);
	}

	// devuelve la lista de los usuarios registrados en la aplicacion
	// public List<Usuario> getRegistrados() throws java.rmi.RemoteException{
	// return this.servicioDatos.getRegistrados();
	// }

	// devuelve la lista de los usuarios logueados en ese momento en la aplicacion
	// public List<Sesion> getLogueados() throws java.rmi.RemoteException{
	// return this.servicioDatos.getLogueados();
	// }

	// borra el trino de un usuario en la BBDD y evita que le llegue a los usuarios
	// que esten desloguados
	public void borrarTrino(Sesion s, Trino t) throws java.rmi.RemoteException {
		this.servicioDatos.borrarTrino(s, t);
	}

	// Devuelve la estructura de datos que relaciona usuarios y trinos
	public HashMap<Usuario, List<Trino>> getTrinos() throws java.rmi.RemoteException {
		return this.servicioDatos.getTrinos();
	}

	/*
	 * // test de la funcion bloquear. BORRAR DESPUÉS public HashMap<Usuario,
	 * List<Usuario>> getBloqueados() throws java.rmi.RemoteException { return
	 * this.servicioDatos.getBloqueados(); }
	 */

	// Devuelve la estructura de datos que relaciona usuarios con sus seguidores
	public HashMap<Usuario, List<Usuario>> getSeguidores() throws java.rmi.RemoteException {
		return this.servicioDatos.getSeguidores();
	}
}
