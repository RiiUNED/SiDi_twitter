/**
 * Autor: 	Ricardo Sanchez Fernadez
 * Email:	rsanchez628@alumno.uned.es
 */
package BBDD;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import Interfaces.*;
import Datos.*;
import Servicios.*;

/*
 * Implementacion de los servicios autentificar del servidor
 */
public class ServicioDatosImpl extends UnicastRemoteObject implements ServicioDatosInterface {

	private List<Usuario> registrados;
	private List<Sesion> logueados;
	private List<Usuario> baneados;
	private List<TrinoRegistrado> trinoR;
	private HashMap<Usuario, List<Trino>> trinos;
	private HashMap<Usuario, List<Trino>> trinosB;
	private HashMap<Usuario, List<Usuario>> seguidores;
	private HashMap<Trino, List<Usuario>> pendientes;

	protected ServicioDatosImpl() throws RemoteException {
		super();
		this.registrados = new LinkedList<Usuario>();
		this.logueados = new LinkedList<Sesion>();
		this.baneados = new LinkedList<Usuario>();
		this.trinoR = new LinkedList<TrinoRegistrado>();
		this.trinos = new HashMap<Usuario, List<Trino>>();
		this.trinosB = new HashMap<Usuario, List<Trino>>();
		this.seguidores = new HashMap<Usuario, List<Usuario>>();
		this.pendientes = new HashMap<Trino, List<Usuario>>();
	}

	//publica los trinos en los timeline correspondientes
	@Override
	public void imprimirTrinos() {
		for (TrinoRegistrado tR : this.trinoR) {
			tR.show();
		}
	}

	/*
	 * Duvuelve una estructura de datos con los usuario logueados que siguen al usuario que 
	 * se le pasa por parametro
	 * param:
	 * u:		usuario del que se quieren conseguir los seguidores logueados
	 * return: estructura de datos con los seguidores logueados
	 */
	@Override
	public List<CallbackUsuarioInterface> getActivos(Usuario u) throws java.rmi.RemoteException {

		List<CallbackUsuarioInterface> activos = new LinkedList<CallbackUsuarioInterface>();
		HashMap<Usuario, List<Usuario>> seguidores = this.seguidores;
		List<Usuario> misSeguidores = Auxiliar.getMisSeguidores(seguidores, u);

		if(misSeguidores!=null) {
			for (Usuario seguidor : misSeguidores) {
				for (Sesion s : this.logueados) {
					if (seguidor.identico(s.getUser())) {
						activos.add(s.getServidor());
					}
				}
			}	
		}

		return activos;
	}

	/*
	 * Actualiza la estructura de datos que relaciona trinos y usuarios que no 
	 * estaban logueados y deben recibir el trino cuando se logueen
	 * param:
	 * 	u:		usuario que trina
	 * 	t:		trino que publica el usuario
	 * return:
	 * estructura de datos actualizada
	 */
	@Override
	public void updatePendientes(Usuario u, Trino t) throws java.rmi.RemoteException {
		List<Usuario> inactivos = new LinkedList<Usuario>();
		List<Usuario> misSeguidores = Auxiliar.getMisSeguidores(seguidores, u);
		inactivos = AuxDatos.getInactivos(misSeguidores, logueados);
		this.pendientes.put(t, inactivos);
	}

	/*
	 * Añade un usuario a la estructura de datos que guarda los datos de los
	 * usuarios registrados
	 * param:
	 * 	u: 	usuario que acaba de registrarse
	 * return:
	 * estructra de datos actualizada
	 */
	@Override
	public boolean registrar(Usuario u) throws java.rmi.RemoteException {
		if (!AuxDatos.containsU(this.registrados, u)) {
			this.registrados.add(u);
			return true;
		} else {
			return false;
		}
	}

	// devuelve la lista de los usuarios registrados en el sistema
	@Override
	public List<Usuario> getRegistrados() throws java.rmi.RemoteException {
		return this.registrados;
	}

	// devuelve la lista de los usuarios logueados en el sistema
	@Override
	public List<Sesion> getLogueados() throws java.rmi.RemoteException {
		return this.logueados;
	}

	/*
	 * chequea si un usuario esta registrado en el sistema
	 * param:
	 * 	u: 		usuario del que se quiere comprobar si esta registrado en el 
	 * 			sistema
	 * return:
	 * 	true	-> el usuario esta registrado en el sistema
	 * 	false	-> el usuario no esta registrado en el sistema
	 */
	public boolean checkRegistro(Usuario u) throws java.rmi.RemoteException {
		return AuxDatos.containsU(this.registrados, u);
	}

	/*
	 * Añade un usuario a la estructura de datos que guarda los datos de los
	 * usuarios logueados
	 * param:
	 * 	s: 		datos del usuario que se loguea
	 * return:
	 * true		-> el usuario se loguea con exito
	 * false	-> el usuario no se loguea con exito
	 */
	@Override
	public boolean loguear(Sesion s) throws java.rmi.RemoteException {
		List<Sesion> l = this.logueados;
		if (!AuxDatos.checkLog(s, l)) {
			this.logueados.add(s);
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Devuelve una estructura de datos con los trinos que no recibio
	 * un usuario mientras estuvo deslogueado
	 * param:
	 * 	s: 		datos encapsulados del usuario deslogueado
	 * return: estructura de datos con los trinos no recibidos
	 */
	@Override
	public List<Trino> getTrinos(Sesion s) throws java.rmi.RemoteException {
		List<Trino> sinPublicar = new LinkedList<Trino>();
		Usuario u = s.getUser();

		sinPublicar = AuxDatos.getTargetedKeyTU(this.pendientes, u);

		return sinPublicar;
	};

	/*
	 * Elimina un usuario de la estructura de datos que guarda los datos de los
	 * usuarios logueados
	 * param:
	 * 	s: 		datos del usuario que elimina
	 * return:
	 * true		-> el usuario se desloguea con exito
	 * false	-> el usuario no se desloguea con exito
	 */
	@Override
	public boolean desloguear(Sesion s) throws java.rmi.RemoteException {

		for (Iterator<Sesion> iter = this.logueados.iterator(); iter.hasNext();) {
			Sesion s1 = iter.next();
			if (s1.identica(s)) {
				iter.remove();
			}
		}
		return !AuxDatos.containsS(this.logueados, s);
	};

	/*
	 * Bloquea a un usuario añadiendole a la correspondiente estructura de datos
	 * para que no pueda enviar mas trinos
	 * param:
	 * 	u:		usuario que se bloquea
	 * return: 
	 * 	true	-> usuario bloqueado con exito
	 * 	flase	-> usuario no bloqueado con exito
	 */
	@Override
	public boolean banear(Usuario u) throws java.rmi.RemoteException {
		if (AuxDatos.containsU(this.baneados, u)) {
			return false;
		} else {
			this.baneados.add(u);
			return true;
		}
	}

	/*
	 * Levanta el bloqueo a un usuario eliminandole de la correspondiente 
	 * estructura de datos para que pueda enviar trinos
	 * param:
	 * 	u:		usuario que se bloquea
	 * return: 
	 * 	estructura de datos con trinos
	 */
	@Override
	public List<Trino> unban(Usuario u) throws java.rmi.RemoteException {
		AuxDatos.removeU(this.baneados, u);
		return AuxDatos.getTargetedKeyUT(this.trinosB, u);
	}

	/*
	 * Regitra el trino enviado en la estructura de datos que relaciona 
	 * usuarios y trinos
	 * param:
	 * 	u:		usuario que publica el trino
	 * 	t:		trino publicado
	 * return: estructura de datos actualizada
	 */
	@Override
	public void trinar(Usuario u, Trino t) throws java.rmi.RemoteException {
		List<Usuario> parlantes = new LinkedList<Usuario>(this.trinos.keySet());
		if (AuxDatos.containsU(parlantes, u)) {
			List<Trino> lt = Auxiliar.getMisTrinos(this.trinos, u);
			lt.add(t);
		} else {
			List<Trino> l = new LinkedList<>();
			l.add(t);
			this.trinos.put(u, l);
		}
	}

	/*
	 * Actualiza la lista de los trinos publicados
	 * param:
	 * 	u:		usuario que publica el trino
	 * return: estructura de datos actualizada
	 */
	@Override
	public void registrarTrino(Usuario u) throws java.rmi.RemoteException {
		String nick = u.getNick();
		TrinoRegistrado tR = new TrinoRegistrado(nick);
		this.trinoR.add(tR);
	}

	/*
	 * Un usuario de hace seguidor de otro
	 * param:
	 * 	lider:		usuario al que se va a seguir
	 * 	seguidor:	usuario que recibira los trinos del lider
	 * return: estructuras de datos actualizadas
	 */
	@Override
	public void seguir(Usuario lider, Usuario seguidor) throws java.rmi.RemoteException {
		if (Auxiliar.checkLlavesUU(this.seguidores, lider)) {
			List<Usuario> misSeguidores = Auxiliar.getMisSeguidores(this.seguidores, lider);
			misSeguidores.add(seguidor);
		} else {
			List<Usuario> l = new LinkedList<Usuario>();
			l.add(seguidor);
			this.seguidores.put(lider, l);
		}

	}

	/*
	 * Actualiza la estrutura de datos que registra los trinos bloqueados
	 * param:
	 * 	u:		usuario que trina
	 * 	t:		trino
	 * return: estructura de datos actualizada
	 */
	@Override
	public void putTrinoB(Usuario u, Trino t) throws java.rmi.RemoteException {
		if (Auxiliar.checkLlavesUT(this.trinosB, u)) {
			List<Trino> misTrinosB = Auxiliar.getMisTrinos(this.trinosB, u);
			misTrinosB.add(t);
		} else {
			List<Trino> l = new LinkedList<Trino>();
			l.add(t);
			this.trinosB.put(u, l);
		}
	}

	/*
	 * Un usuario deja de seguir a otro
	 * param:
	 * 	ex:			datos del usuario que deja de seguir al otro
	 * 	lider:		usuario que es abandonado
	 * return: estructuras de datos actualizadas
	 */
	@Override
	public void abandonar(Sesion ex, Usuario lider) throws java.rmi.RemoteException {
		Usuario exU = ex.getUser();
		List<Usuario> misSeguidores = Auxiliar.getMisSeguidores(this.seguidores, lider);
		AuxDatos.removeU(misSeguidores, exU);
		if(misSeguidores.isEmpty()) {this.seguidores.remove(lider);}
	}

	/*
	 * Borra el trino de un usuario en la BBDD y evita que le llegue a los seguidores
	 * que no esten desloguados
	 * param:
	 * 	s:		datos del usuario que quiere borrara el trino
	 * 	t: 		trino que se quiere borrar
	 * return: estructuras de datos actualizadas
	 */
	@Override
	public void borrarTrino(Sesion s, Trino t) throws java.rmi.RemoteException {
		HashMap<Usuario, List<Trino>> ts = this.trinos;
		HashMap<Trino, List<Usuario>> p = this.pendientes;
		Usuario u = s.getUser();
		AuxDatos.borrarRegistro(u, t, ts);
		AuxDatos.borrarPendientes(t, p);
	}

	/*
	 * chequea si un usuario fue baneado
	 * param:
	 * 	u: 		usuario que se quiere banear
	 * return:
	 * 	true:	-> el usuario ha sido baneado
	 * 	false:	-> el usuario no ha sido baneado
	 */
	@Override
	public boolean checkBan(Usuario u) throws java.rmi.RemoteException {
		return AuxDatos.containsU(this.baneados, u);
	}

	/*
	 * devuelve el usuario registrado del que se le pase nick
	 * param:
	 * 	nick:	nick del usuario que se busca
	 * return: 
	 * 	usuario registrado con el nick, si existe
	 * 	null, no existe usuario registrado con ese nick 
	 */
	@Override
	public Usuario getUser(String nick) throws java.rmi.RemoteException {
		Usuario user = null;
		List<Usuario> registrados = this.registrados;
		for (Usuario u : registrados) {
			String n = u.getNick();
			if (n.equals(nick)) {
				user = u;
			}
		}
		return user;
	}

	/*
	 * Comprueba si el password facilitada por el usuario esta registrada
	 * en la BBDD para el nick
	 * param:
	 * sesion:		datos del cliente proporcionados por el usuario
	 * return:
	 * 	true		-> el password es correcto
	 * 	false		-> el password es incorrecto 
	 */
	@Override
	public boolean checkPass(Sesion sesion) throws java.rmi.RemoteException{
		boolean check = false;
		List<Usuario> registrados = this.registrados;
		Usuario user = sesion.getUser();
		String pass = user.getPassword();
		for(Usuario u :registrados) {
			if (u.equals(user)){
				String passResgister = u.getPassword();
				if(passResgister.equals(pass)) {check = true;}
			}
		}
		return check;
	}
	
}
