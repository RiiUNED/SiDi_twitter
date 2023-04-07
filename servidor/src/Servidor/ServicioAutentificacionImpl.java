/**
 * Autor:	Ricardo Sanchez Fernandez
 * Email:	rsanchez@alumno.uned.es
 */
package Servidor;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import Interfaces.*;
import Datos.*;

/*
 * Implementacion de los servicios autentificar del servidor
 */
public class ServicioAutentificacionImpl extends UnicastRemoteObject implements ServicioAutentificacionInterface {

	private ServicioDatosInterface servicioDatos;

	//Constructor
	public ServicioAutentificacionImpl() throws RemoteException {
		super();
		int puerto = Registry.REGISTRY_PORT;
		this.servicioDatos = AuxServidor.getServicioDatos(puerto);
	}

	// devuelve la lista de los usuarios registrados en la aplicacion
	@Override
	public List<Usuario> getRegistrados() throws java.rmi.RemoteException {
		return this.servicioDatos.getRegistrados();
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

	// desloguea al usuario cuando sale de la aplicación
	@Override
	public boolean desloguear(Sesion s) throws java.rmi.RemoteException {
		return this.servicioDatos.desloguear(s);
	};

	// Comprueba si un usuario esta registrado
	@Override
	public boolean checkRegistro(Usuario u) throws java.rmi.RemoteException {
		return this.servicioDatos.checkRegistro(u);
	}

	// devuelve la lista de los usuarios logueados en ese momento en la aplicacion
	@Override
	public List<Sesion> getLogueados() throws java.rmi.RemoteException {
		return this.servicioDatos.getLogueados();
	}

	// devuelve el usuario registrado del que se le pase nick
	public Usuario getUser(String nick) throws java.rmi.RemoteException {
		return this.servicioDatos.getUser(nick);
	}

	// chequea si la contraseña se corresponde con la registrada al nick del usuario
	// que se proporciono para generar la sesion
	public boolean checkPass(Sesion sesion) throws java.rmi.RemoteException{
		return this.servicioDatos.checkPass(sesion);
	}
}
