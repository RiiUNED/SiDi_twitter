package BBDD;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Comun.*;;

public class BBDD {

	public static void main(String[] args) throws Exception {
		
		// registro en el puerto por defecto de rmi
		int puerto = Registry.REGISTRY_PORT;
		Registry registry = LocateRegistry.getRegistry(puerto);
		try {
			registry.list();
			System.out.println("se ha registrado la BBDD en el puerto: " + puerto);

		} catch (Exception e) {
			registry = LocateRegistry.createRegistry(puerto);
			System.out.println("se ha creado la BBDD en el puerto: " + puerto);
		}

		// Exportar objeto
		DatosInt servidorDatos = new DatosImpl();
		UnicastRemoteObject.unexportObject(servidorDatos, false);
		servidorDatos = (DatosInt) UnicastRemoteObject.exportObject(servidorDatos, 0);
		Naming.rebind("rmi://localhost:" + puerto + "/" + DatosInt.class.getCanonicalName(), servidorDatos);

		// salida por consola de las interfaces que pueden consumir los clientes
		System.out.println("Interfaces publicadas");
		for (String name : registry.list()) {
			System.out.println(name);
		}

	}

}
