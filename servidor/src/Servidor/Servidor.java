package Servidor;

import java.rmi.*;
import java.rmi.server.*;
import java.util.HashMap;

import Comun.*;

import java.rmi.registry.Registry.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.LocateRegistry.*;
import java.rmi.registry.Registry;
import java.net.*;
import java.io.*;


/*
 * esta clase representa un servidor de objeto
 * @autor: rsanchez628@alumno.uned.es
 * 		Ricardo S�nchez
 */

public class Servidor {
	
	public static void main(String args[]) throws Exception{
		
		//registro en el puerto por defecto de rmi
		int puerto = Registry.REGISTRY_PORT;
		Registry registry = LocateRegistry.getRegistry(puerto);
		try {
			registry.list();
			System.out.println("se ha registrado el servidor en el puerto: "+puerto);
			
		} catch (Exception e) {
			registry = LocateRegistry.createRegistry(puerto);
			System.out.println("se ha creado el servidor en el puerto: "+puerto);
		}
		
		//Exportar objeto
		AutentificarInt servidor1 = new AutentificarImpl();
		GestorInt servidor2 = new GestorImpl();
		UnicastRemoteObject.unexportObject(servidor1, false);
		UnicastRemoteObject.unexportObject(servidor2, false);
		servidor1 = (AutentificarInt) UnicastRemoteObject.exportObject(servidor1, 0);
		servidor2 = (GestorInt) UnicastRemoteObject.exportObject(servidor2, 0);
		Naming.rebind("rmi://localhost:" + puerto + "/" + AutentificarInt.class.getCanonicalName(), servidor1);
		Naming.rebind("rmi://localhost:" + puerto + "/" + GestorInt.class.getCanonicalName(), servidor2);
		
		//salida por consola de las interfaces que pueden consumir los clientes
		System.out.println("Interfaces publicadas");
		for (String name : registry.list()) {
			System.out.println(name);
		}
			
	}

}
