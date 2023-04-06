package cliente;

import Interfaces.*;

public class Configuracion {
	
	private final ServicioAutentificacionInterface autentificar;
	private final ServicioGestorInterface gestor;
	private final CallbackUsuarioInterface servidor;
	
	public Configuracion(ServicioAutentificacionInterface a, ServicioGestorInterface g, CallbackUsuarioInterface s) {
		this.autentificar = a;
		this.gestor = g;
		this.servidor = s;
	}
	
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
