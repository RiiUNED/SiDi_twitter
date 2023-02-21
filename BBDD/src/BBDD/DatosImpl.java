package BBDD;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import Comun.*;;

public class DatosImpl extends UnicastRemoteObject implements DatosInt {

	private HashMap<Usuario, List<Trino>> trinos = new HashMap<>();
	// private HashMap<Usuario, List<Usuario>> bloqueos = new HashMap<>();
	// private HashMap<Usuario, List<Usuario>> seguidos = new HashMap<>();

	protected DatosImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public void trinar(Usuario u, Trino t) throws java.rmi.RemoteException {
		if (this.trinos.containsKey(u)) {
			this.trinos.get(u).add(t);
		} else {
			List<Trino> l = new LinkedList<>();
			l.add(t);
			this.trinos.put(u, l);
		}
		System.out.println("El usuario: "+u.getNick()+" manipula la BBDD");
	}

	// test de la función enviar. BORRAR DESPUÉS
	public HashMap<Usuario, List<Trino>> getTrinos() throws java.rmi.RemoteException {
		return this.trinos;
	}
}
