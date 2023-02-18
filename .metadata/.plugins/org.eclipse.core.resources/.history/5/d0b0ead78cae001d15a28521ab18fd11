package cliente;

import java.io.*;
import java.rmi.*;

import Comun.HolaInt;

/*
 * La clase representa un cliente de objeto distribuido
 * @autor: rsanchez628@alumno.uned.es
 */

public class Cliente {
	
	private static final Integer PUERTO = 2023;
	private static Integer nodo = 1;
	
	public static void main(String[] args) {
		try {
			nodo++;
			String snodo = nodo.toString();
			String spuerto = PUERTO.toString();
			String registro = "rmi://localhost"+ ":" + spuerto + "/hola";
			
			HolaInt h = (HolaInt)Naming.lookup(registro);
			
			String mensaje = h.decirHola("Pato Donald");
			System.out.println("Cliente "+nodo+" dice: "+mensaje);
		} catch (Exception e) {
			System.out.println("Excepcion: " + e);
		}
	}

}
