package Servidor;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;

import java.util.*;
import java.net.*;

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

	public GestorImpl() throws RemoteException {
		super();
		int puerto = Registry.REGISTRY_PORT;
		String registroDatos = "rmi://localhost:" + puerto + "/" + DatosInt.class.getCanonicalName();
		try {
			this.servicioDatos = (DatosInt) Naming.lookup(registroDatos);
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	// el usuario envia un trino a sus seguidores
	public void enviar(Usuario u, Trino t) throws java.rmi.RemoteException {
		this.servicioDatos.trinar(u, t);
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
	public void seguir(Usuario lider, Usuario seguidor) throws java.rmi.RemoteException {
		this.servicioDatos.seguir(lider, seguidor);
	}
	
	// un seguidor abandona a su lider
	public void abandonar(Usuario lider, Usuario ex) throws java.rmi.RemoteException{
		this.servicioDatos.abandonar(lider, ex);
	}

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
