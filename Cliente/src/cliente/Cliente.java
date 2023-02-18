package cliente;

import java.io.*;
import java.rmi.*;
import java.rmi.registry.Registry;
import java.util.Scanner;

import Comun.ServerInt;
import Comun.Usuario;

/*
 * La clase representa un cliente de objeto distribuido
 * @autor: rsanchez628@alumno.uned.es
 * 			Ricardo Sanchez
 */

public class Cliente {
	
	public static void main(String[] args) {
		
		int puerto = Registry.REGISTRY_PORT;
		
		try {
			
			//Se comunica con la interfaz
			String registro = "rmi://localhost:" + puerto + "/" + ServerInt.class.getCanonicalName();
			ServerInt h = (ServerInt)Naming.lookup(registro);
			
			String name = "Ricardo";
			String nick = "rsanchez628";
			String pass = "SiDi_2023";
			
			Usuario user = new Usuario(name, nick, pass);
			
			String respuesta = h.decirHola();
			System.out.println(respuesta);
			String respuesta2 = h.autenticarse2(name, nick, pass);
			System.out.println(respuesta2);
			Usuario u = new Usuario(name, nick, pass);
			String respuesta1 = h.autenticarse1(u);
			System.out.println(respuesta1);
			
		} catch (Exception e) {
			
			System.out.println("Excepcion: " + e);
			e.printStackTrace();
			
		}		
	}
	
}
