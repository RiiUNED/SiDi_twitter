package Comun;

import java.io.Serializable;

public class Sesion implements Serializable{
	
	private Usuario user;
	private CallbackInt servidor;
	
	public Sesion(Usuario u, CallbackInt s) {
		this.user = u;
		this.servidor = s;
	}
	
	public Usuario getUser() {
		return this.user;
	}

	public CallbackInt getServidor() {
		return this.servidor;
	}
	
	public boolean identica(Sesion s) {
		Usuario u = s.getUser();
		return this.user.identico(u);
	}
}
