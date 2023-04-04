package Servidor;

import java.rmi.*;
import java.rmi.server.*;
import java.util.HashMap;

import Interfaces.*;

import java.rmi.registry.Registry.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.LocateRegistry.*;
import java.rmi.registry.Registry;
import java.net.*;
import java.io.*;

import java.util.*;

/*
 * esta clase representa un servidor de objeto
 * @autor: rsanchez628@alumno.uned.es
 * 		Ricardo Sánchez
 */

public class Servidor {
	
	public static void main(String args[]) throws Exception{
		
		//registro en el puerto por defecto de rmi
		int puerto = Registry.REGISTRY_PORT;
		
		GestorInt servidor = AuxServidor.configurar(puerto);
		
		DatosInt servicioDatos = AuxServidor.getServicioDatos(puerto);
		
		AuxServidor.menu(servidor, servicioDatos);
		
		System.exit(0);
			
	}
}
