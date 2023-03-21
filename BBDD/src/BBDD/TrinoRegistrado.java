package BBDD;

import java.time.*;

class TrinoRegistrado {
	private String nick;
	private Instant timeStamp;
	
	public TrinoRegistrado(String nick) {
		this.nick = nick;
		this.timeStamp = Instant.now();
	}
	
	public String getnick() {
		return this.nick;
	}
	
	public Instant getTimestamp() {
		return this.timeStamp;
	}
	
	public void show() {
		System.out.println(this.nick+" "+this.timeStamp.toEpochMilli());
	}
}
