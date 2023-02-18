package Servidor;

import java.rmi.*;
import java.rmi.server.*;

import Comun.HolaInt;

/*
 * la clase imlementa la interfaz remota HolaInt
 * @autor: rsanchez628@alumno.uned.es
 */

public class HolaImp extends UnicastRemoteObject implements HolaInt {
	
	public HolaImp() throws RemoteException{
		super();
	}
	
	public String decirHola (String nombre) throws RemoteException {
		return "Hola " + nombre;
	}

} // fin clase
