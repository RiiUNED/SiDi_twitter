package cliente;

import Comun.*;

public class Configurar {
	
	private final AutentificarInt autentificar;
	private final GestorInt gestor;
	private final CallbackInt servidor;
	
	public Configurar(AutentificarInt a, GestorInt g, CallbackInt s) {
		this.autentificar = a;
		this.gestor = g;
		this.servidor = s;
	}
	
	public AutentificarInt getAutentificar() {
		return this.autentificar;
	}
	
	public GestorInt getGestor() {
		return this.gestor;
	}
	
	public CallbackInt getServidor() {
		return this.servidor;
	}

}
