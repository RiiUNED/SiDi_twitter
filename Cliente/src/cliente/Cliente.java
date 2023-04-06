package cliente;

//import java.io.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
//import java.util.Scanner;

import Interfaces.*;

/*
 * La clase representa un cliente de objeto distribuido
 * @autor: rsanchez628@alumno.uned.es
 * 			Ricardo Sanchez
 */

public class Cliente {

	public static void main(String[] args) throws RemoteException {

		int puerto = Registry.REGISTRY_PORT;
		String servicio = "rmi://localhost:" + puerto + "/" + CallbackUsuarioInterface.class.getCanonicalName();

		Configuracion setup = AuxCliente.configurar(puerto, servicio);

		AuxCliente.menu1(setup, servicio);

		System.out.println("Fuera de la aplicacion.");
		System.exit(0);

	}

}
