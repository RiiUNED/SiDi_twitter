package cliente;

import Comun.*;

public class CallbackImpl implements CallbackInt{
	
	@Override
	public void publicar (Trino trino) throws java.rmi.RemoteException{
		String firma = trino.getSender().getNick();
		String m = trino.getMessage();
		
		System.out.println("El usuario: "+firma);
		System.out.println("publico el mensaje:");
		System.out.println(m);
	}

}
