package de.futjikato.javahasen.simulation;

public abstract class Fieldobject {
	
	private Position pos;
	
	public Position getPosition() {
		return this.pos;
	}
	
	public void setPosition(Position pos) {
		this.pos = pos;
	}
	
	public void delete() throws Exception {
		Field field = Simulator.getInstance().getField();
		field.remove(this, this.pos);
	}
}
