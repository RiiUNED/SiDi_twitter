package Interfaces;

import java.rmi.Remote;

import Datos.*;

public interface CallbackInt extends Remote{
	public void publicar (Trino trino) throws java.rmi.RemoteException;
}
