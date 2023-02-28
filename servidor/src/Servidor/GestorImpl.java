package Servidor;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;

import java.util.*;
import java.util.function.Consumer;
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
	private List<CallbackInt> receptores;

	public GestorImpl() throws RemoteException {
		super();
		int puerto = Registry.REGISTRY_PORT;
		String registroDatos = "rmi://localhost:" + puerto + "/" + DatosInt.class.getCanonicalName();
		try {
			this.servicioDatos = (DatosInt) Naming.lookup(registroDatos);
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
		this.receptores = new LinkedList<>();
	}

	// el usuario envia un trino a sus seguidores
	public void enviar(Usuario u, Trino trino) throws java.rmi.RemoteException {
		this.servicioDatos.trinar(u, trino);

		if (!this.receptores.isEmpty()) {
			this.receptores.stream().forEach(new Consumer<>() {

				@Override
				public void accept(CallbackInt t) {
					// TODO Auto-generated method stub
					try {
						t.publicar(trino);
					} catch (RemoteException e) {
						e.printStackTrace();
					}

				}
			});
		}

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
	public void seguir(Usuario lider, Usuario seguidor, CallbackInt s) throws java.rmi.RemoteException {
		this.servicioDatos.seguir(lider, seguidor);
		this.receptores.add(s);
	}

	// un seguidor abandona a su lider
	public void abandonar(Usuario lider, Usuario ex) throws java.rmi.RemoteException {
		this.servicioDatos.abandonar(lider, ex);
	}

	// ------------------------------- PRUEBAS -----------------------------
	// test callback
	public void recibir(CallbackInt u) throws java.rmi.RemoteException {
		this.receptores.add(u);
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
