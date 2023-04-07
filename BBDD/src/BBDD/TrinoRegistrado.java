/**
 * Autor: 	Ricardo Sanchez Fernadez
 * Email:	rsanchez628@alumno.uned.es
 */
package BBDD;

import java.util.*;

/*
 * Clase que encapsula ciertos datos de los trinos
 * para su registro en la BBDD
 */
class TrinoRegistrado {
	private String nick;
	private long timeStamp;
	
	//Constructor
	public TrinoRegistrado(String nick) {
		this.nick = nick;
		Date date = new Date();
		this.timeStamp = date.getTime();
	}
	
	/*
	 * Getters y metodos de servicios
	 * --------------------------------------------------
	 */
	public String getnick() {
		return this.nick;
	}
	
	public long getTimestamp() {
		return this.timeStamp;
	}
	
	public void show() {
		System.out.println(this.nick+" "+this.timeStamp);
	}
}
