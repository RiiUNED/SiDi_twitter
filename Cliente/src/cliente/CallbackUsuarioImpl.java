package cliente;

import Interfaces.*;
import Datos.*;

public class CallbackUsuarioImpl implements CallbackUsuarioInterface{
	
	@Override
	public void publicar (Trino trino) throws java.rmi.RemoteException{
		String firma = trino.GetNickPropietario();
		String m = trino.GetTrino();
		
		/*
		System.out.println("El usuario: "+firma);
		System.out.println("publico el mensaje:");
		System.out.println(m); */
		
		System.out.println();
		System.out.print(firma+"# ");
		System.out.println(m);
		System.out.println();
	}

}
