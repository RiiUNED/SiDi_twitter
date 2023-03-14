package Comun;

import java.util.*;;

public class Auxiliar {
	
	public static void showSeguidores(HashMap<Usuario, List<Usuario>> seguidores) {
		List<Usuario> llaves = new LinkedList<Usuario>(seguidores.keySet());
		for(Usuario k : llaves) {
			System.out.println("el usuario");
			k.show();
			System.out.println("tiene como seguidores");
			List<Usuario> s = seguidores.get(k);
			for(Usuario u : s) {
				u.show();
			}
		}
	}
	
	public static void showPendientes(HashMap<Trino, List<Usuario>> pendientes) {
		List<Trino> llaves = new LinkedList<Trino>(pendientes.keySet());
		for(Trino k : llaves) {
			System.out.println("el trino");
			k.showTrino();
			System.out.println("no fue enviado a los usuarios");
			List<Usuario> s = pendientes.get(k);
			for(Usuario u : s) {
				u.show();
			}
		}
	}
	
	public static void showUsuarios(List<Usuario> l) {
		for(Usuario u :l) {
			u.show();
		}
	}
	
	public static void showSesiones(List<Sesion> sesiones) {
		for(Sesion s : sesiones) {
			s.show();
		}
	}
	
	public static List<Usuario> getMisSeguidores(
			HashMap<Usuario, List<Usuario>> seguidores, 
			Usuario user){
		List<Usuario> misSeguidores = new LinkedList<Usuario>();
		
		List<Usuario> llaves = new LinkedList<Usuario>(seguidores.keySet());
		for(Usuario llave : llaves) {
			if (llave.identico(user)){
				misSeguidores = seguidores.get(llave);
			}
		}
		
		return misSeguidores;
	}
	
	public static boolean checkLlavesU(HashMap<Usuario, List<Usuario>> seguidores, Usuario lider) {
		boolean existe = false;
		
			List<Usuario> llaves = new LinkedList<Usuario>(seguidores.keySet());
			
			for(Usuario llave : llaves) {
				existe = existe || llave.identico(lider);
			}
		return existe;
	}

}
