/**
 * Autor:	Ricardo Sanchez Fernandez
 * email:	rsanchez628@alumno.uned.es
 */

package cliente;

import java.rmi.*;
import java.rmi.registry.Registry;

import Interfaces.*;

/*
 * Punto de entrada al cliente
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
