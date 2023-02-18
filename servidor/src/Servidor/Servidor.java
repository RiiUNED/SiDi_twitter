package Servidor;

import java.rmi.*;
import java.rmi.server.*;
import java.util.HashMap;

import Comun.ServerInt;
import Comun.Usuario;

import java.rmi.registry.Registry.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.LocateRegistry.*;
import java.rmi.registry.Registry;
import java.net.*;
import java.io.*;


/*
 * esta clase representa un servidor de objeto
 * @autor: rsanchez628@alumno.uned.es
 * 		Ricardo Sánchez
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
		ServerInt servidor = new ServerImp();
		/*
		if (UnicastRemoteObject.unexportObject(servidor, false)) {
		    System.out.println("El objeto ya estaba exportado y ha sido desexportado");
		}*/
		UnicastRemoteObject.unexportObject(servidor, false);
		servidor = (ServerInt) UnicastRemoteObject.exportObject(servidor, 0);
		Naming.rebind("rmi://localhost:" + puerto + "/" + ServerInt.class.getCanonicalName(), servidor);
		
		//salida por consola de las interfaces que pueden consumir los clientes
		System.out.println("Interfaces publicadas");
		for (String name : registry.list()) {
			System.out.println(name);
		}
			
	}

}
