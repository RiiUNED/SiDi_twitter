package Servidor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
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

	private HashMap<Usuario, List<Trino>> trinos = new HashMap<>();
	private HashMap<Usuario, List<Usuario>> bloqueos = new HashMap<>();
	private HashMap<Usuario, List<Usuario>> seguidos = new HashMap<>();

	public GestorImpl() throws RemoteException {
		super();
	}

	// el usuario envia un trino a sus seguidores
	public void enviar(Usuario u, Trino t) throws java.rmi.RemoteException {
		/*
		 * if (this.trinos.containsKey(u)) { this.trinos.get(u).add(t); } else {
		 * List<Trino> l = new LinkedList<>(); l.add(t); this.trinos.put(u, l); }
		 */
		int puerto = Registry.REGISTRY_PORT;
		String registroDatos = "rmi://localhost:" + puerto + "/" + DatosInt.class.getCanonicalName();
		try {
			DatosInt servicioDatos = (DatosInt) Naming.lookup(registroDatos);
			servicioDatos.trinar(u, t);
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// test de la funci�n enviar. BORRAR DESPU�S
	public HashMap<Usuario, List<Trino>> getTrinos() throws java.rmi.RemoteException {
		// return this.trinos;
		int puerto = Registry.REGISTRY_PORT;
		String registroDatos = "rmi://localhost:" + puerto + "/" + DatosInt.class.getCanonicalName();
		try {
			DatosInt servicioDatos = (DatosInt) Naming.lookup(registroDatos);
			return servicioDatos.getTrinos();
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new HashMap<>();
		}
	}

	// un usuario bloquea a otro
	public void bloquear(Usuario bloqueante, Usuario bloqueado) throws java.rmi.RemoteException {
		if (this.bloqueos.containsKey(bloqueante)) {
			this.bloqueos.get(bloqueante).add(bloqueado);
		} else {
			List<Usuario> l = new LinkedList<>();
			l.add(bloqueado);
			this.bloqueos.put(bloqueante, l);
		}
	}

	// un usuario sigue a otro
	public void seguir(Usuario seguidor, Usuario seguido) throws java.rmi.RemoteException {
		if (this.seguidos.containsKey(seguido)) {
			this.seguidos.get(seguido).add(seguidor);
		} else {
			List<Usuario> l = new LinkedList<>();
			l.add(seguidor);
			this.seguidos.put(seguido, l);
		}
	}

	// test de la funcion bloquear. BORRAR DESPU�S
	public HashMap<Usuario, List<Usuario>> getBloqueados() throws java.rmi.RemoteException {
		return this.bloqueos;
	}

	// test de la funcion seguir. BORRAR DESPU�S
	public HashMap<Usuario, List<Usuario>> getSeguidos() throws java.rmi.RemoteException {
		return this.seguidos;
	}
}
