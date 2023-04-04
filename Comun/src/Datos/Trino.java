package Datos;

import java.io.Serializable;

/*
 * Clase para instanciar los trinos de los usuarios
 * @autor: rsanchez628@alumno.uned.es
 * 			Ricardo Sanchez
 */

public class Trino implements Serializable{
	private Usuario sender;
	private String message;
	
	public Trino(Usuario u, String m) {
		this.sender = u;
		this.message = m; 
	}
	
	//geters y seters
	public Usuario getSender() {
		return sender;
	}
	public void setSender(Usuario sender) {
		this.sender = sender;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void showTrino() {
		System.out.println(this.sender.getNick()+", tuiteó: ");
		System.out.println(this.message);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((sender == null) ? 0 : sender.hashCode());
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
		Trino other = (Trino) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (sender == null) {
			if (other.sender != null)
				return false;
		} else if (!sender.equals(other.sender))
			return false;
		return true;
	}
}
