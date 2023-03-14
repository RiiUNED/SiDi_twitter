package Servidor;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;

import java.util.*;

import Comun.*;

/*Implementacion de los servicios Gestor
 * enviar trino
 * bloquear usuario - desbloquear
 * seguir usario - dejar de seguir
 * consumidos por los usuarios del servicio
 * @autor: rsanchez628@alumno.uned.es
 * 			Ricardo Sanchez
 */

public class GestorImpl extends UnicastRemoteObject implements GestorInt {

	private DatosInt servicioDatos;
	private HashMap<Trino, List<Usuario>> pendientes;

	public GestorImpl() throws RemoteException {
		super();
		int puerto = Registry.REGISTRY_PORT;
		this.servicioDatos = AuxServidor.getServicioDatos(puerto);
		this.pendientes = new HashMap<Trino, List<Usuario>>();
	}

	// el usuario envia un trino a sus seguidores
	public void trinar(Usuario u, Trino trino) throws java.rmi.RemoteException {
		this.servicioDatos.trinar(u, trino);
		
		HashMap<Usuario, List<Usuario>> seguidores = this.servicioDatos.getSeguidores();
		List<Usuario> misSeguidores = Auxiliar.getMisSeguidores(seguidores, u);
		List<Sesion> logueados = this.servicioDatos.getLogueados();
		List<CallbackInt> activos = new LinkedList<CallbackInt>();
		List<Usuario> inactivos = new LinkedList<Usuario>();
		
		activos = AuxServidor.getActivos(misSeguidores, logueados);
		inactivos = AuxServidor.getInactivos(misSeguidores, logueados);
		
		System.out.println();
		System.out.println("Usuarios inactivos");
		this.pendientes.put(trino, inactivos);
		
		Auxiliar.showPendientes(this.pendientes);
		
		AuxServidor.publicar(activos, trino);

	}

	// un usuario bloquea a otro
	public void bloquear(Usuario lider, Usuario bloqueado) throws java.rmi.RemoteException {
		this.servicioDatos.bloquear(lider, bloqueado);
	}

	// un usuario desbloquea a otro
	public void desbloquear(Usuario lider, Usuario desbloqueado) throws java.rmi.RemoteException {
		this.servicioDatos.desbloquear(lider, desbloqueado);
	}

	// un usuario sigue a otro
	public void seguir(Sesion sesion, Usuario lider) throws java.rmi.RemoteException {
		Usuario seguidor = sesion.getUser();
		this.servicioDatos.seguir(lider, seguidor);
	}

	// un seguidor abandona a su lider
	public void abandonar(Usuario lider, Usuario ex) throws java.rmi.RemoteException {
		this.servicioDatos.abandonar(lider, ex);
	}

	// ------------------------------- PRUEBAS -----------------------------

	// test de la función enviar. BORRAR DESPUÉS
	public HashMap<Usuario, List<Trino>> getTrinos() throws java.rmi.RemoteException {
		return this.servicioDatos.getTrinos();
	}

	// test de la funcion bloquear. BORRAR DESPUÉS
	public HashMap<Usuario, List<Usuario>> getBloqueados() throws java.rmi.RemoteException {
		return this.servicioDatos.getBloqueados();
	}

	// test de la funcion seguir abandonar. BORRAR DESPUÉS
	public HashMap<Usuario, List<Usuario>> getSeguidores() throws java.rmi.RemoteException {
		return this.servicioDatos.getSeguidores();
	}
}
