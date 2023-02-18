package Servidor;

import java.rmi.*;
import java.rmi.server.*;

import Comun.HolaInt;

import java.rmi.registry.Registry.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.LocateRegistry.*;
import java.rmi.registry.Registry;
import java.net.*;
import java.io.*;


/*
 * esta clase representa un servidor de objeto
 * @autor: rsanchez628@alumno.uned.es
 */

public class Servidor {
	
	private static final int PUERTO = 2023;
	
	public static void main(String args[]){
		
		//servicios.setCodeBase(HolaInt.class);
		
		try {
			
			arrancarRegistro(PUERTO);
			HolaImp objExportado = new HolaImp();
			Integer ipuerto = (Integer)PUERTO;
			String spuerto = ipuerto.toString();
			String URLRegistro = "rmi://localhost:"+ spuerto + "/hola";
			Naming.rebind(URLRegistro, objExportado);
			System.out.println("se ha levantado el servidor");
			
		} catch (Exception e) {
			System.out.println("Excepcion en el main del servidor" + e);
		}
			
	}
	
	/*
	 * Arranca un registro en la máquina locañ
	 */
	public static void arrancarRegistro(int numeroPuerto) throws RemoteException{
		try {
			Registry registro = LocateRegistry.getRegistry(numeroPuerto);
			registro.list();
		} catch (RemoteException e) {
			LocateRegistry.createRegistry(numeroPuerto);
			System.out.println("Registro RMI creado en el puerto: " + numeroPuerto);
		}
		
	}

}
