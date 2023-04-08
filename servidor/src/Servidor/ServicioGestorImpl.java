/**
 * Autor: 	Ricardo Sanchez Fernadez
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
 * Implementacion de los servicios Gestor del servidor
 */

public class ServicioGestorImpl extends UnicastRemoteObject implements ServicioGestorInterface {

	private ServicioDatosInterface servicioDatos;

	//Constructor de la clase
	public ServicioGestorImpl() throws RemoteException {
		super();
		int puerto = Registry.REGISTRY_PORT;
		this.servicioDatos = AuxServidor.getServicioDatos(puerto);
	}

	// el usuario publica un trino
	public void trinar(Usuario u, Trino trino, boolean registrar) throws java.rmi.RemoteException {
		if (servicioDatos.checkBan(u)) {
			servicioDatos.putTrinoB(u, trino);
		} else {
			this.servicioDatos.trinar(u, trino);
			List<CallbackUsuarioInterface> activos = new LinkedList<CallbackUsuarioInterface>();
			activos = this.servicioDatos.getActivos(u);
			if(activos!=null) {
				AuxServidor.publicar(activos, trino);
				if (!servicioDatos.checkBan(u)) {
					this.servicioDatos.updatePendientes(u, trino);
				} else {
					registrar = false;
				}
			}
		}
		if (registrar) {
			servicioDatos.registrarTrino(u);
		}
	}

	/*
	 * Actualiza el timeline del usuario tras loguearse
	 * param:
	 * 	s:		datos del usuario encapsulados junto a la interfaz
	 * 			con el callback que permite la publicacion de los trinos
	 * return:	actulizacion del timeline del usuario
	 */
	public void updateTrinos(Sesion s) throws java.rmi.RemoteException {
		List<Trino> sinPublicar = servicioDatos.getTrinos(s);

		CallbackUsuarioInterface servidor = s.getServidor();
		List<CallbackUsuarioInterface> l = new LinkedList<CallbackUsuarioInterface>();
		l.add(servidor);

		for (Trino t : sinPublicar) {
			AuxServidor.publicar(l, t);
		}
	};

	// un usuario sigue a otro
	public void seguir(Sesion sesion, Usuario lider) throws java.rmi.RemoteException {
		Usuario seguidor = sesion.getUser();
		this.servicioDatos.seguir(lider, seguidor);
	}

	// un seguidor abandona a su lider
	public void abandonar(Sesion ex, Usuario lider) throws java.rmi.RemoteException {
		this.servicioDatos.abandonar(ex, lider);
	}

	// borra el trino de un usuario en la BBDD y evita que le llegue a los usuarios
	// que esten desloguados
	public void borrarTrino(Sesion s, Trino t) throws java.rmi.RemoteException {
		this.servicioDatos.borrarTrino(s, t);
	}

}
