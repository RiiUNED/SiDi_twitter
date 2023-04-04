package Datos;

import java.rmi.server.RemoteObject;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.*;
import java.io.*;

/*
 * Clase para instanciar usuarios
 * @autor: rsanchez628@alumno.uned.es
 * 			Ricardo Sanchez
 */

public class Usuario implements Serializable {
	private String name;
	private String nick;
	private String password;

	// Constructor
	public Usuario(String nombre, String nick, String pass) {
		this.name = nombre;
		this.nick = nick;
		this.password = pass;
	}

	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	// Metodos get and set
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

	// Mostrar usuario
	public void show() {
		System.out.println(this.toString());
	}

	@Override
	public String toString() {
		if (this.name == null) {
			return "Usuario{" + "nick='" + this.nick + '\'' +'}';
		} else {
			return "Usuario{" + "nombre='" + this.name + '\'' + ", nick='" + this.nick + '\'' + '}';
		}
	}

	// mismo usuario
	public Boolean identico(Usuario u) {
		// Boolean name = this.name.equals(u.getName());
		return this.nick.equals(u.getNick());
		// Boolean pass = this.password.equals(u.getPassword());

		// return name && nick && pass;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nick == null) ? 0 : nick.hashCode());
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
		Usuario other = (Usuario) obj;
		if (nick == null) {
			if (other.nick != null)
				return false;
		} else if (!nick.equals(other.nick))
			return false;
		return true;
	}
}
