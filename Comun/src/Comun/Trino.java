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
	
	public boolean equals(Trino t2) {
		boolean eq = false;
		Usuario ut = this.sender;
		String mt = this.message;
		Usuario u2 = t2.getSender();
		String m2 = t2.getMessage();
		
		boolean eq1 = ut.identico(u2);
		boolean eq2 = mt.equals(m2);
		
		if(eq1 && eq2) {eq = true;}
		
		return eq;
	}
}
