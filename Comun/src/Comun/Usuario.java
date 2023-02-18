package Comun;

import java.rmi.server.RemoteObject;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.*;
import java.io.*;

/*
 * Clase para instanciar usuarios
 * @autor: rsanchez628@alumno.uned.es
 * 			Ricardo Sanchez
 */

public class Usuario implements Serializable{
	private String name;
	private String nick;
	private String password;
	
	//Constructor
	public Usuario(String nombre, String nick, String pass) {
		this.name = nombre;
		this.nick = nick;
		this.password = pass;
	}
	
	//Metodos get and set
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
