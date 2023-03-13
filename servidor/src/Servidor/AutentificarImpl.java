package Servidor;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Comun.*;

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
		this.servicioDatos = AuxServidor.getServicioDatos(puerto);
	}

	// registra un usuario en el sistema
	@Override
	public boolean registrar(Usuario u) throws java.rmi.RemoteException {
		return this.servicioDatos.registrar(u);
	}

	// Loguea a un usuario permitiendole hacer uso del sistema
	@Override
	public boolean loguear(Sesion s) throws java.rmi.RemoteException {
		return this.servicioDatos.loguear(s);
	}

	// banea a un usuario impidiendole hacer uso del sistema
	public boolean banear(Usuario u) throws java.rmi.RemoteException{
		return this.servicioDatos.banear(u);
	}
	
	public boolean checkRegistro(Usuario u) throws java.rmi.RemoteException{
		return this.servicioDatos.checkRegistro(u);
	}

	// auxiliar para pruebas. borrar después

}
