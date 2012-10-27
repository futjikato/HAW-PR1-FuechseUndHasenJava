package de.futjikato.javahasen.simulation;

import java.util.Stack;

public class Rabbit extends Creature {
	
	private boolean poisoned = false;
	private boolean immune = false;
	
	public Rabbit(Field field, boolean randAge, Position pos) throws Exception {
		super(field, randAge, pos);
	}
	
	public void poison() {
		if(this.immune) {
			return;
		}
		this.poisoned = true;
	}
	
	public void immune() {
		this.immune = true;
	}
	
	private void infectNeighbor() {
		Position[] rabbitPos = this.field.getNeighborPositions(this.pos, this);
		Position neighborPos = rabbitPos[0];
		
		if(neighborPos != null) {
			Rabbit neighbor = (Rabbit) this.field.getCreatureFromPosition(neighborPos);
			neighbor.poison();
		}
	}
	
	@Override
	public boolean process() throws Exception {
		// creature manages aging process
		if(!super.process()) {
			return false;
		}
		
		// infect a neighbor if their is one and the rabbit is poisoned
		if(this.poisoned) {
			this.infectNeighbor();
		}
		
		// rabbits die when they are < 3 year old and no > 3 year old rabbit is a neightbor
		if(this.age < 3) {
			Position[] rabbitPos = this.field.getNeighborPositions(this.pos, this);
			for(Position pos : rabbitPos) {
				if(pos != null) {
					Creature possibleParent = this.field.getCreatureFromPosition(pos);
					if(possibleParent.getAge() > 3) {
						return true;
					}
				} else {
					// if null is found end of array
					break;
				}
			}
			
			// return false if no > 3 "parent"
			return false;
		}
		
		return true;
	}

	@Override
	public int getMaxAge() {
		// poisoned rabbits die after 4 years
		if(this.poisoned) {
			return 4;
		}
		
		return 5;
	}

	@Override
	public float[] getColor() {
		float[] rgba = { 0.1f , 0.9f , 1.0f };
		
		// change color if poisoned
		if(this.poisoned) {
			rgba[0] = 0.9f;
		}
		
		return rgba;
	}

	@Override
	protected String[] getFood() {
		return new String[]{};
	}
	
	@Override
	protected int getInitFoodLevel() {
		return 1;
	}
	
	@Override
	public void spawnChild() throws Exception {
		Position[] neighbor = this.field.getNeighborPositions(this.pos);
		for(Position n : neighbor) {
			Creature content = this.field.getCreatureFromPosition(n);
			if(content != null && content instanceof Tiger) {
				return;
			}
		}
		
		Stack<Position> newPos = this.field.getRandomFreeNeightborArray(this.pos);
		if(newPos != null && newPos.size() > 2 && this.age >= 3) {
			Rabbit child = new Rabbit(this.field, false, newPos.firstElement());
			
			// chance of posion child if parent is already inffected
			if(this.poisoned) {
				double rand = Math.random();
				if(rand > 0.4) {
					child.poison();
				} else {
					child.immune();
				}
			}
			
			Simulator.getInstance().addCreature(child);
		}
	}
	
	@Override
	public String getFieldCodeChar() {
		return "H";
	}
}
