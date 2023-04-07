/**
 * Autor:	Ricardo Sanchez Fernandez
 * email:	rsanchez628@alumno.uned.es
 */

package Servidor;

import java.rmi.registry.Registry;

import Interfaces.*;

/*
 * Punto de entrada al cliente
 */
public class Servidor {
	
	public static void main(String args[]) throws Exception{
		
		//registro en el puerto por defecto de rmi
		int puerto = Registry.REGISTRY_PORT;
		
		String autenticar = "rmi://localhost:" + puerto + "/" + ServicioAutentificacionInterface.class.getCanonicalName();
		String gestor = "rmi://localhost:" + puerto + "/" + ServicioGestorInterface.class.getCanonicalName();
		
		ServicioGestorInterface servidor = AuxServidor.configurar(puerto, autenticar, gestor);		
		ServicioDatosInterface servicioDatos = AuxServidor.getServicioDatos(puerto);
		
		AuxServidor.menu(servidor, servicioDatos, autenticar, gestor);
		
		System.exit(0);
			
	}
}
