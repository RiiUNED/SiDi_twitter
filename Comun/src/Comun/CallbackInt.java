package Comun;

import java.rmi.Remote;

public interface CallbackInt extends Remote{
	public void publicar (Trino trino) throws java.rmi.RemoteException;
}
