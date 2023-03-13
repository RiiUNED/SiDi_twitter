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
	private List<CallbackInt> activos;
	private HashMap<Trino, List<Usuario>> pendientes;

	public GestorImpl() throws RemoteException {
		super();
		int puerto = Registry.REGISTRY_PORT;
		this.servicioDatos = AuxServidor.getServicioDatos(puerto);
		this.activos = new LinkedList<CallbackInt>();
		this.pendientes = new HashMap<Trino, List<Usuario>>();
	}

	// el usuario envia un trino a sus seguidores
	public void trinar(Usuario u, Trino trino) throws java.rmi.RemoteException {
		this.servicioDatos.trinar(u, trino);
		
		//List<Usuario> seguidores = this.servicioDatos.getSeguidores().get(u);
		HashMap<Usuario, List<Usuario>> seguidores = this.servicioDatos.getSeguidores();
		Auxiliar.showSeguidores(seguidores);
		List<Usuario> misSeguidores = Auxiliar.getMisSeguidores(seguidores, u);
		System.out.println("Size misSeguidores: " + misSeguidores.size());
		List<Sesion> logueados = this.servicioDatos.getLogueados();
		
		for(Usuario seguidor : misSeguidores) {
			for(Sesion s : logueados) {
				if(seguidor.identico(s.getUser())) {
					this.activos.add(s.getServidor());
				} // aquí va la lógica de los pendientes
			}
		}

		if (!this.activos.isEmpty()) {
			System.out.println("Size activos: "+ this.activos.size());
			this.activos.stream().forEach(new Consumer<>() {

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
		this.activos.clear();
		

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
