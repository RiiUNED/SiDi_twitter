/**
 * Autor:	Ricardo Sanchez Fernandez
 * email:	rsanchez628@alumno.uned.es
 */

package cliente;

import Interfaces.*;

/*
 * Clase para encapsular las distintas interfaces con los servicios
 * que prestan los actores de la aplicacion
 */
public class Configuracion {
	
	private final ServicioAutentificacionInterface autentificar;
	private final ServicioGestorInterface gestor;
	private final CallbackUsuarioInterface servidor;
	
	//Constructos
	public Configuracion(
			ServicioAutentificacionInterface a, 
			ServicioGestorInterface g, 
			CallbackUsuarioInterface s) {
		this.autentificar = a;
		this.gestor = g;
		this.servidor = s;
	}
	
	/*
	 * Getters
	 * --------------------------------------------------------
	 */
	public ServicioAutentificacionInterface getAutentificar() {
		return this.autentificar;
	}
	
	public ServicioGestorInterface getGestor() {
		return this.gestor;
	}
	
	public CallbackUsuarioInterface getServidor() {
		return this.servidor;
	}

}
