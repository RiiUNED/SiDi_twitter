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
	
	public static boolean checkLlaves(HashMap<Usuario, List<Usuario>> seguidores, Usuario lider) {
		boolean existe = false;
		
			List<Usuario> llaves = new LinkedList<Usuario>(seguidores.keySet());
			
			for(Usuario llave : llaves) {
				existe = existe || llave.identico(lider);
			}
		return existe;
	}

}
