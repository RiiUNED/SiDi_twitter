package Comun;

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
}
