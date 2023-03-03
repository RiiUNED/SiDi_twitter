package cliente;

import java.rmi.RemoteException;
import java.util.*;

import Comun.*;

//Esta clase va fuera del proyecto
class Debug {
	
	public static void registrarUsuario(AutentificarInt servicioAutentificar, Scanner sc) throws RemoteException {
		// Crear usuario
        List<List<Usuario>> usuarios = crearUsuario();
        //Registrar usuario
        System.out.println("Usuario a registrar: 1 Ri, 2 Ro, 3 B1.");
        int opcion2 = sc.nextInt();
        switch (opcion2) {
        case 1:
        	Usuario userRi = usuarios.get(0).get(0);
        	//servicioAutentificar.registrar(userRi);
        	AuxCliente.registrar(servicioAutentificar, userRi);
        	break;
        case 2:
        	Usuario userR2 = usuarios.get(0).get(1);
        	//servicioAutentificar.registrar(userR2);
        	AuxCliente.registrar(servicioAutentificar, userR2);
        	break;
        case 3:
        	Usuario userB1 = usuarios.get(1).get(0);
        	//servicioAutentificar.registrar(userB1);
        	AuxCliente.registrar(servicioAutentificar, userB1);
        break;
        }
	}
	
	// crear usuarios
		public static List<List<Usuario>> crearUsuario() {

			List<List<Usuario>> u = new LinkedList<>();
			List<Usuario> usuarios = new LinkedList<>();
			List<Usuario> bloqueados = new LinkedList<>();
			List<Usuario> seguidores = new LinkedList<>();

			// Crear usuarios
			System.out.println("creando los usuarios...");
			System.out.println();
			String name = "Ricardo";
			String nick = "I000";
			String pass = "SiDi_2023";
			Usuario userRi = new Usuario(name, nick, pass);
			usuarios.add(userRi);

			name = "Roberto";
			nick = "O999";
			pass = "SiDi_2024";
			Usuario userRo = new Usuario(name, nick, pass);
			usuarios.add(userRo);

			// Usuarios bloqueados
			name = "Bloqueado1";
			nick = "BLQ1";
			pass = "SiDi_2024";
			Usuario blq1 = new Usuario(name, nick, pass);
			name = "Bloqueado2";
			nick = "BLQ2";
			pass = "SiDi_2024";
			Usuario blq2 = new Usuario(name, nick, pass);
			name = "Bloqueado3";
			nick = "BLQ3";
			pass = "SiDi_2024";
			Usuario blq3 = new Usuario(name, nick, pass);
			bloqueados.add(blq1);
			bloqueados.add(blq2);
			bloqueados.add(blq3);

			// Seguidores
			name = "Seguidor1";
			nick = "SGD1";
			pass = "SiDi_2024";
			Usuario sgd1 = new Usuario(name, nick, pass);
			name = "Seguidor2";
			nick = "SGD2";
			pass = "SiDi_2024";
			Usuario sgd2 = new Usuario(name, nick, pass);
			name = "Seguidor3";
			nick = "SGD3";
			pass = "SiDi_2024";
			Usuario sgd3 = new Usuario(name, nick, pass);
			seguidores.add(sgd1);
			seguidores.add(sgd2);
			seguidores.add(sgd3);

			u.add(usuarios);
			u.add(bloqueados);
			u.add(seguidores);

			return u;
		}
		
		// Crear trinos
		public static List<Trino> crearTrinos(List<Usuario> usuarios) {

			List<Trino> trinos = new LinkedList<>();

			Usuario userRi = usuarios.get(0);
			Usuario userRo = usuarios.get(1);
			System.out.println("creando los trinos...");
			System.out.println();
			String messageRi1 = "Hola, me llamo RICARDO.";
			Trino trinoRi1 = new Trino(userRi, messageRi1);
			trinos.add(trinoRi1);
			String messageRi2 = "RICARDO publica su segundo trino.";
			Trino trinoRi2 = new Trino(userRi, messageRi2);
			trinos.add(trinoRi2);
			String messageRo1 = "Hola, me llamo ROBERTO.";
			Trino trinoRo1 = new Trino(userRo, messageRo1);
			trinos.add(trinoRo1);
			String messageRo2 = "ROBERTO publica su segundo trino.";
			Trino trinoRo2 = new Trino(userRo, messageRo2);
			trinos.add(trinoRo2);
			String messageRo3 = "ROBERTO publica su tercer trino.";
			Trino trinoRo3 = new Trino(userRo, messageRo3);
			trinos.add(trinoRo3);

			return trinos;
		}

}
