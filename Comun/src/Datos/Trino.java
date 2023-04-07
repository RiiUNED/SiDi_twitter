/**
 * Clase proporcionada por el ED a traves del Curso Virtual
 */

package Datos;

import java.io.Serializable;
import java.util.Date;

public class Trino implements Serializable{

	private static final long serialVersionUID = 2L;
	private String trino;
	private String nickPropietario;	//Ojo no pueden haber varios usuarios con el mismo nick
	private long timestamp; //momento en el que se produce el evento (tiempo en el servidor)
	
	public Trino(String nickPropietario, String trino)
	{
		this.trino=trino;
		this.nickPropietario=nickPropietario;
		Date date = new Date();
		this.timestamp=date.getTime();
	}
	public String GetTrino()
	{
		return (trino);
	}
	public String GetNickPropietario()
	{
		return(nickPropietario);
	}
	public long GetTimestamp()
	{
		return (timestamp);
	}
	public String toString(){
		return (getClass().getName()+"@"+trino+"|"+nickPropietario+"|"+timestamp+"|");
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nickPropietario == null) ? 0 : nickPropietario.hashCode());
		result = prime * result + ((trino == null) ? 0 : trino.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trino other = (Trino) obj;
		if (nickPropietario == null) {
			if (other.nickPropietario != null)
				return false;
		} else if (!nickPropietario.equals(other.nickPropietario))
			return false;
		if (trino == null) {
			if (other.trino != null)
				return false;
		} else if (!trino.equals(other.trino))
			return false;
		return true;
	}
	
	
}
