package de.futjikato.javahasen.simulation;
import java.util.Collections;
import java.util.Stack;


public class Field {
	
	protected int size;
	
	protected Creature[][] fielddata;
	
	/** 
	 * Initialize a new field with the given size
	 * 
	 * @param size
	 */
	public Field(int size) {
		this.size = size;
		this.fielddata = new Creature[size][size];
	}
	
	public int getSize() {
		return this.size;
	}
	
	public void setPosition(Creature creature, Position pos) {
		this.fielddata[pos.getX()][pos.getY()] = creature;
	}
	
	public void removeCreature(Position pos) {
		this.fielddata[pos.getX()][pos.getY()] = null;
	}
	
	public Creature getRandomNeightbor(Position pos, String[] foodCreatures) {
		Position[] neighbors = this.getNeighborPositions(pos);
		Stack<Creature> fields = new Stack<Creature>();
		
		for (Position position : neighbors) {
			for (String className : foodCreatures) {
				if(
					this.fielddata[position.getX()][position.getY()] != null &&
					this.fielddata[position.getX()][position.getY()].getClass().toString().equals("class " + className)
				) { 
					fields.push(this.fielddata[position.getX()][position.getY()]);
				}
				break;
			}
		}
		
		Collections.shuffle(fields);
		if(fields.size() > 0) {
			return fields.pop();
		} else {
			return null;
		}
	}
	
	public boolean isFieldFree(Position pos) {
		return !(this.fielddata[pos.getX()][pos.getY()] instanceof Creature);
	}
	
	/**
	 * Improve me !
	 * 
	 * @param givenCreature
	 * @param newPos
	 */
	public void moveCreature(Creature givenCreature, Position newPos) {
		this.fielddata[givenCreature.getPosition().getX()][givenCreature.getPosition().getY()] = null;
		this.fielddata[newPos.getX()][newPos.getY()] = givenCreature;
	}
	
	public Position[] getNeighborPositions(Position pos) {
		Stack<Position> retVal = new Stack<Position>();
		for(int i = -1 ; i <= 1 ; i++) {
			int newX = pos.getX() + i;
			if(newX >= this.size || newX < 0) continue;
			for(int j = -1 ; j <= 1 ; j++) {
				int newY = pos.getY() + j;
				if(newY >= this.size || newY < 0 || (i == 0 && j == 0)) continue;
				Position testpos = new Position(newX,newY);
				retVal.push(testpos);
			}
		}
		
		//TODO improve this shit
		Position[] retArray = new Position[retVal.size()];
		int index = 0;
		while(!retVal.empty()) {
			retArray[index] = retVal.pop();
			index++;
		}
		
		return retArray;
	}
	
	public Creature getCreatureFromPosition(Position pos) {
		return this.fielddata[pos.x][pos.y];
	}
	
	public Position getRandomFreeNeightbor(Position pos) {
		Position[] neighbors = this.getNeighborPositions(pos);
		Stack<Position> freeFields = new Stack<Position>();
		
		for (Position position : neighbors) {
			if(this.isFieldFree(position)) {
				freeFields.push(position);
			}
		}
		
		Collections.shuffle(freeFields);
		if(freeFields.size() > 0) {
			return freeFields.pop();
		} else {
			return null;
		}
	}
	
	public Stack<Position> getRandomFreeNeightborArray(Position pos) {
		Position[] neighbors = this.getNeighborPositions(pos);
		Stack<Position> freeFields = new Stack<Position>();
		
		for (Position position : neighbors) {
			if(this.isFieldFree(position)) {
				freeFields.push(position);
			}
		}
		
		Collections.shuffle(freeFields);
		return freeFields;
	}
	
	public Position[] getNeighborWithCreature(Position pos, Creature cre) {
		Position[] source = this.getNeighborPositions(pos);
		Position[] retVal = new Position[8];
		int index = 0;
		
		for (Position position : source) {
			if(this.fielddata[position.getX()][position.getY()] != null && this.fielddata[position.getX()][position.getY()].getClass().equals(cre.getClass())) {
				retVal[index] = position;
				index++;
			}
		}
		return retVal;
	}
	
	public Position[] getAllFreePositions() {
		// create array for maximum amount of positions
		Position[] retVal = new Position[this.size*this.size];
		
		int index = 0;
		int x = 0;
		
		for (Creature[] inner : this.fielddata) {
			int y = 0;
			
			for (Creature fieldContent : inner) {
				// collect all free fields
				if(fieldContent == null) {
					retVal[index] = new Position(x, y);
					index++;
				}
				y++;
			}
			
			x++;
		}
		
		return retVal;
	}
}
