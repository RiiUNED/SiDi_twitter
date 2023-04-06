package Datos;

import java.io.Serializable;

import Interfaces.*;

public class Sesion implements Serializable{
	
	private Usuario user;
	private CallbackUsuarioInterface servidor;
	
	public Sesion(Usuario u, CallbackUsuarioInterface s) {
		this.user = u;
		this.servidor = s;
	}
	
	public Usuario getUser() {
		return this.user;
	}

	public CallbackUsuarioInterface getServidor() {
		return this.servidor;
	}
	
	public boolean identica(Sesion s) {
		Usuario u = s.getUser();
		return this.user.identico(u);
	}
	
	public void show() {
		System.out.println("Sesión del usuario:");
		this.getUser().show();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sesion other = (Sesion) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	
}
