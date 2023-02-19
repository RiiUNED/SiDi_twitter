package cliente;

import java.io.*;
import java.rmi.*;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Comun.*;

/*
 * La clase representa un cliente de objeto distribuido
 * @autor: rsanchez628@alumno.uned.es
 * 			Ricardo Sanchez
 */

public class Cliente {

	public static void main(String[] args) {

		int puerto = Registry.REGISTRY_PORT;

		try {

			// Se comunica con la interfaz
			// String registro = "rmi://localhost:" + puerto + "/" +
			// ServerInt.class.getCanonicalName();
			String registro = "rmi://localhost:" + puerto + "/" + AutentificarInt.class.getCanonicalName();
			// ServerInt h = (ServerInt)Naming.lookup(registro);
			AutentificarInt h = (AutentificarInt) Naming.lookup(registro);

			String name = "Ricardo";
			String nick = "rsanchez628";
			String pass = "SiDi_2023";

			Usuario user1 = new Usuario(name, nick, pass);

			if (h.registro(user1)) {
				System.out.println("el usuario se ha registrado correctamente");
			} else {
				System.out.println("ya existe un usuario registrado con nick: " + user1.getNick());
			}
			
			name = "Roberto";
			nick = "rosanchez628";
			pass = "SiDi_2024";
			Usuario user2 = new Usuario(name, nick, pass);

			if (h.registro(user2)) {
				System.out.println("el usuario se ha registrado correctamente");
			} else {
				System.out.println("ya existe un usuario registrado con nick: " + user2.getNick());
			}
			
			// h.autenticar(user);

			HashMap<String, Usuario> bbdd = h.getBBDD();
			for (Map.Entry<String, Usuario> entry : bbdd.entrySet()) {
				entry.getValue().show();
			}

		} catch (Exception e) {

			System.out.println("Excepcion: " + e);
			e.printStackTrace();

		}
	}

}
