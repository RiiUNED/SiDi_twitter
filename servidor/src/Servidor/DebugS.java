package Servidor;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

//import Interfaces.*;

import Datos.*;
// Clase para ayudar con las pruebas

class DebugS {

	public static Usuario elegirUsuario(List<Usuario> usuarios, Scanner sc) {
		System.out.println("Elija usuario: 1 Ri, 2 Ro, 3 B1.");
		int usr = sc.nextInt();
		switch (usr) {
		case 1: {
			return usuarios.get(0);
		}
		case 2: {
			return usuarios.get(1);
		}
		case 3: {
			return usuarios.get(2);
		}

		default:
			return null;
		}

	}

	// crear usuarios
	public static List<Usuario> crearUsuario() {

		List<Usuario> usuarios = new LinkedList<>();

		// Crear usuarios
		String name = "Ricardo";
		String nick = "I000";
		String pass = "SiDi_2023";
		Usuario userRi = new Usuario(name, nick, pass);
		usuarios.add(userRi);

		name = "Roberto";
		nick = "O999";
		pass = "SiDi_2024";
		Usuario userRo = new Usuario(name, nick, pass);
		usuarios.add(userRo);

		// Usuarios bloqueados
		name = "Bloqueado1";
		nick = "BLQ1";
		pass = "SiDi_2024";
		Usuario blq1 = new Usuario(name, nick, pass);
		usuarios.add(blq1);
		name = "Bloqueado2";
		nick = "BLQ2";
		pass = "SiDi_2024";
		Usuario blq2 = new Usuario(name, nick, pass);
		usuarios.add(blq2);
		name = "Bloqueado3";
		nick = "BLQ3";
		pass = "SiDi_2024";
		Usuario blq3 = new Usuario(name, nick, pass);
		usuarios.add(blq3);

		// Seguidores
		name = "Seguidor1";
		nick = "SGD1";
		pass = "SiDi_2024";
		Usuario sgd1 = new Usuario(name, nick, pass);
		usuarios.add(sgd1);
		name = "Seguidor2";
		nick = "SGD2";
		pass = "SiDi_2024";
		Usuario sgd2 = new Usuario(name, nick, pass);
		usuarios.add(sgd2);
		name = "Seguidor3";
		nick = "SGD3";
		pass = "SiDi_2024";
		Usuario sgd3 = new Usuario(name, nick, pass);
		usuarios.add(sgd3);

		return usuarios;
	}

}
