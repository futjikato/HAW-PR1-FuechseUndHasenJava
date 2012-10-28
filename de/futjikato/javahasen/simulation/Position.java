package de.futjikato.javahasen.simulation;

public class Position {
	private int x;
	private int y;
	
	private int endX;
	private int endY;
	
	private Creature content;
	
	public Position(int x, int y, Creature content) {
		this.x = x;
		this.endX = x;
		
		this.y = y;
		this.endY = y;
		
		this.content = content;
	}

	public int getLastX() {
		return this.x;
	}
	
	public int getNewX() {
		return this.endX;
	}

	public void setX(int x) {
		this.endX = x;
	}

	public int getLastY() {
		return this.y;
	}
	
	public int getNewY() {
		return this.endY;
	}

	public void setNewPosition(int x, int y) throws Exception {
		// reached last position
		this.reachedNewPosition();
		
		// inform field
		Simulator.getInstance().getField().moveCreature(this.endX, this.endY, x, y, this.content);
		
		// set new destination
		this.endX = x;
		this.endY = y;
	}
	
	protected void reachedNewPosition() {
		this.y = this.endY;
		this.x = this.endX;
	}
	
	public Creature getContent() {
		return this.content;
	}
	
	protected void updateContent() {
		//TODO implement
	}
	
	protected boolean isEmpty() {
		return this.content == null;
	}
}
