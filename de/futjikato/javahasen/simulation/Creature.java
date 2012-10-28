package de.futjikato.javahasen.simulation;

import java.util.Collections;
import java.util.Stack;

public abstract class Creature {
	
	protected boolean alive = true;
	
	protected int foodLevel;
	
	protected int age = 0;
	
	protected Field field;
	
	protected Position pos;
	
	public Creature(Field field, boolean randAge, Position pos) throws Exception {
		this.pos = pos;
		field.setPosition(this, pos);
		this.field = field;
		this.foodLevel = this.getInitFoodLevel();
		
		if(randAge) {
			this.age = (int)Math.random() * this.getMaxAge();
		}
	}
	
	public abstract void spawnChild() throws Exception;
	
	public boolean process() throws Exception {
		this.age++;
		if(this.age > this.getMaxAge()) {
			this.die();
			return false;
		}
		
		/**
		 * moving
		 * 1) try to find something to eat
		 * 2) try to get a random position
		 * 3) die
		 */
		Class[] food = this.getFood();
		Position newPos = null;
		if(food.length > 0) {
			Stack<Position> neighbors = this.field.getNeighborPositions(this.pos);
			
			// search for eatable creatures in near fields
			Collections.shuffle(neighbors);
			eatloop:
			while(!neighbors.empty()) {
				Position testPos = neighbors.pop();
				
				// free field
				if(testPos.isEmpty()) {
					continue;
				}
				
				for(Class eatClass : food) {
					Creature eatCreature = testPos.getContent();
					if(eatCreature.getClass().equals(food)) {
						newPos = eatCreature.getPosition();
						this.eat(eatCreature);
						break eatloop;
					}
				}
				
				// nothing eatable found
				this.foodLevel--;
			}
			
			if(this.foodLevel <= 0) {
				this.die();
				return false;
			}
		}
		
		if(newPos == null){
			// get random free neighbor field
			Stack<Position> freeNei = this.field.getFreeNeightbors(this.pos);
			Collections.shuffle(freeNei);
			newPos = freeNei.pop();
		}
		
		if(newPos == null){
			this.die();
			return false;
		}
		
		// spawn child
		this.spawnChild();
		
		// move creature
		this.pos.setNewPosition(newPos.getNewX(), newPos.getNewY());
		
		return true;
	}
	
	public abstract int getMaxAge();
	public abstract float[] getColor();
	protected abstract Class[] getFood();
	protected abstract int getInitFoodLevel();
	public abstract String getFieldCodeChar();
	
	protected void eat(Creature foodObj) throws Exception {
		// do nothing on default
	}
	
	public void die() throws Exception {
		this.alive = false;
		this.field.removeCreature(this, this.pos);
	}
	
	public boolean isAlive() {
		return this.alive;
	}
	
	public int getAge() {
		return this.age;
	}
	
	public Position getPosition() {
		return this.pos;
	}
}
