package Servidor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import Comun.AutentificarInt;
import Comun.DatosInt;
import Comun.Usuario;

/*
 * Implementacion de los servicios autentificar
 * Registro
 * Login
 * consumidos por los usuarios del servicio
 * @autor: rsanchez628@alumno.uned.es
 * 			Ricardo Sanchez
 */
public class AutentificarImpl extends UnicastRemoteObject implements AutentificarInt {

	private DatosInt servicioDatos;

	public AutentificarImpl() throws RemoteException {
		super();
		int puerto = Registry.REGISTRY_PORT;
		String registroDatos = "rmi://localhost:" + puerto + "/" + DatosInt.class.getCanonicalName();
		try {
			this.servicioDatos = (DatosInt) Naming.lookup(registroDatos);
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	// registra un usuario en el sistema
	@Override
	public boolean registrar(Usuario u) throws java.rmi.RemoteException {
		return this.servicioDatos.registrar(u);
	}

	// Loguea a un usuario permitiendole hacer uso del sistema
	@Override
	public boolean loguear(Usuario u) throws java.rmi.RemoteException {
		return this.servicioDatos.loguear(u);
	}

	// banea a un usuario impidiendole hacer uso del sistema
	public boolean banear(Usuario u) throws java.rmi.RemoteException{
		return this.servicioDatos.banear(u);
	}

	// auxiliar para pruebas. borrar después

}
