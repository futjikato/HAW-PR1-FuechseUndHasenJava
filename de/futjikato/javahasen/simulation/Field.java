package de.futjikato.javahasen.simulation;

import java.util.Stack;

public class Field {
	
	protected int size;
	
	protected Fieldobject[][] fielddata;
	
	/** 
	 * Initialize a new field with the given size
	 * 
	 * @param size
	 */
	public Field(int size) {
		this.size = size;
		this.fielddata = new Fieldobject[size][size];
	}
	
	public int getSize() {
		return this.size;
	}
	
	protected boolean verify(Position pos) {
		return (pos.getContent().equals(this.fielddata[pos.getNewX()][pos.getNewY()]));
	}
	
	protected void setPosition(Fieldobject thing, Position pos) throws Exception {
		// check if field is empty
		if(this.fielddata[pos.getNewX()][pos.getNewY()] != null) {
			throw new Exception("Cannot set creature to already filled position");
		}
		
		this.fielddata[pos.getNewX()][pos.getNewY()] = thing;
	}
	
	protected void remove(Fieldobject thing, Position pos) throws Exception {
		// check if create is on given position
		if(this.fielddata[pos.getNewX()][pos.getNewY()] != thing) {
			throw new Exception("Cannot remove creature from position. Wrong position given.");
		}
		
		this.fielddata[pos.getNewX()][pos.getNewY()] = null;
	}
	
	public Stack<Position> getNeighborPositions(Position pos) {
		Stack<Position> retVal = new Stack<Position>();
		for(int i = -1 ; i <= 1 ; i++) {
			int newX = pos.getNewX() + i;
			if(newX >= this.size || newX < 0) continue;
			for(int j = -1 ; j <= 1 ; j++) {
				int newY = pos.getNewY() + j;
				if(newY >= this.size || newY < 0 || (i == 0 && j == 0)) continue;
				Position testpos = new Position(newX, newY, this.fielddata[newX][newY]);
				retVal.push(testpos);
			}
		}
		
		return retVal;
	}
	
	protected void move(int oldX, int oldY, int newX, int newY, Fieldobject content) throws Exception {
		// Check if old field is no empty
		Fieldobject temp = this.fielddata[oldX][oldY];
		if(temp == null) {
			throw new Exception("No creature found at old position");
		}
		
		// check if given creature is really on that field
		if(!temp.equals(content)) {
			throw new Exception("Invalid creature found at old position");
		}
		
		// Check that new field is empty
		Fieldobject atNew = this.fielddata[newX][newY];
		if(atNew != null) {
			throw new Exception("Target field is not empty");
		}
		
		// make changes if everything is fine
		this.fielddata[oldX][oldY] = null;
		this.fielddata[newX][newY] = temp;
	}
	
	public Stack<Position> getFreeNeightbors(Position pos) {
		Stack<Position> neighbors = this.getNeighborPositions(pos);
		Stack<Position> freeFields = new Stack<Position>();
		
		while (!neighbors.empty()) {
			Position position = neighbors.pop();
			if(position.isEmpty()) {
				freeFields.push(position);
			}
		}
		
		return freeFields;
	}
	
	public Stack<Position> getAllFreePositions() {
		// create array for maximum amount of positions
		Stack<Position> retVal = new Stack<Position>();
		
		int x = 0;
		for (Fieldobject[] inner : this.fielddata) {
			
			int y = 0;
			for (Fieldobject fieldContent : inner) {
				// collect all free fields
				if(fieldContent == null) {
					retVal.push(new Position(x, y, null));
				}
				
				y++;
			}
			
			x++;
		}
		
		return retVal;
	}
}
