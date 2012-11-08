package de.futjikato.javahasen.simulation;

public class Position {
	private int x;
	private int y;
	
	private int endX;
	private int endY;
	
	private Fieldobject content;
	
	public Position(int x, int y, Fieldobject content) {
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

	public int getLastY() {
		return this.y;
	}
	
	public int getNewY() {
		return this.endY;
	}

	public Position setNewPosition(int x, int y) throws Exception {
		// reached last position
		this.reachedNewPosition();
		
		// inform field
		Simulator.getInstance().getField().move(this.endX, this.endY, x, y, this.content);
		
		// set new destination
		this.endX = x;
		this.endY = y;
		
		// enable chaining
		return this;
	}
	
	protected Position reachedNewPosition() {
		this.y = this.endY;
		this.x = this.endX;
		
		return this;
	}
	
	public Fieldobject getContent() {
		return this.content;
	}
	
	public boolean isCreature() {
		return !this.isEmpty() && this.content instanceof Creature;
	}
	
	public boolean isStatic() {
		return !this.isEmpty() && this.content instanceof StaticObject;
	}
	
	public Position setContent(Fieldobject content) throws Exception {
		Field field = Simulator.getInstance().getField();
		
		// may remove old content from field
		if(this.content != null) {
			field.remove(this.content, this);
		}
		
		this.content = content;
		
		// set new content onto the field
		field.setPosition(content, this);
		
		return this;
	}
	
	protected void updateContent() {
		//TODO implement
	}
	
	protected boolean isEmpty() {
		return this.content == null;
	}

	/**
	 * debugging purpose
	 */
	public void verify() throws Exception {
		Field field = Simulator.getInstance().getField();
		
		if(!field.verify(this)) {
			throw new Exception("Invalid position");
		}
	}

	public boolean isBorder() {
		Field field = Simulator.getInstance().getField();
		int size = field.getSize();
		
		return (
			this.endX == (size - 1) || 
			this.endX == 0 || 
			this.endY == (size - 1) || 
			this.endY == 0
		);
	}
}
