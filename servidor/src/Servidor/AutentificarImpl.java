package Servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import Comun.AutentificarInt;
import Comun.ServerInt;
import Comun.Usuario;

/*
 * Implementacion de los servicios autentificar
 * Registro
 * Login
 * consumidos por los usuarios del servicio
 * @autor: rsanchez628@alumno.uned.es
 * 			Ricardo Sanchez
 */
public class AutentificarImpl extends UnicastRemoteObject implements AutentificarInt{
		
		//BBDD para probar autenticar antes de desligarla a otro servidor
		private HashMap<String, Usuario> bbdd = new HashMap<>();
		
		public AutentificarImpl() throws RemoteException{
			super();
		}
		
		/*
		 * Registra usuarios en la aplicacion
		 * @autor: rsanchez628@alumno.uned.es
		 * 			Ricardo Sanchez
		 */
		//autenticar
		@Override
		public Boolean registro(Usuario u) throws java.rmi.RemoteException{
			if(!bbdd.containsKey(u.getNick())) {
				this.bbdd.put(u.getNick(), u);
				return true;
			} else {
				return false;
			}
		}
		
		/*
		 * Loguea los usuarios en la aplicacion
		 * @autor: rsanchez628@alumno.uned.es
		 * 			Ricardo Sanchez
		 */
		public Boolean login(Usuario u) throws java.rmi.RemoteException{
			if(bbdd.containsKey(u.getNick())) {
				Usuario valor = bbdd.get(u.getNick());
				return valor.equal(u);
			} else {
				return false;
			}
		}
		
		//auxiliar para pruebas. borrar después
		public HashMap<String, Usuario> getBBDD() throws java.rmi.RemoteException{
			return this.bbdd;
		}

}
