package de.futjikato.javahasen.simulation;

import java.util.Collections;
import java.util.Stack;

public class Rabbit extends Creature {
	
	private boolean poisoned = false;
	private boolean immune = false;
	
	public Rabbit(Field field, boolean randAge, Position pos) throws Exception {
		super(field, randAge, pos);
	}
	
	public void poison() {
//		if(this.immune) {
//			return;
//		}
//		this.poisoned = true;
	}
	
	public void immune() {
		this.immune = true;
	}
	
	private void infectNeighbor() {
		Stack<Position> rabbitPos = this.field.getNeighborPositions(this.pos);
		Collections.shuffle(rabbitPos);
		
		while(!rabbitPos.empty()) {
			Position singlePos = rabbitPos.pop();
			
			if(!singlePos.isEmpty() && singlePos.getContent() instanceof Rabbit) {
				Rabbit neiRab = (Rabbit) singlePos.getContent();
				
				neiRab.poison();
				return;
			}
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
			Stack<Position> neighborPositions = this.field.getNeighborPositions(this.pos);
			int rabbitCount = 0;
			
			while(!neighborPositions.empty()) {
				Position testPos = neighborPositions.pop();
				
				if(!testPos.isEmpty() && testPos.getContent() instanceof Rabbit) {
					Rabbit rabbit = (Rabbit)testPos.getContent();
					if(rabbit.getAge() > 3) {
						rabbitCount++;
					}
				}
			}
			
			return (rabbitCount > 0);
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

	@SuppressWarnings("rawtypes")
	@Override
	protected Class[] getFood() {
		return new Class[]{};
	}
	
	@Override
	protected int getInitFoodLevel() {
		return 1;
	}
	
	@Override
	public void spawnChild() throws Exception {
		Stack<Position> neighbor = this.field.getNeighborPositions(this.pos);
		while(!neighbor.empty()) {
			Position n = neighbor.pop();
			Fieldobject content = n.getContent();
			if(content != null && content instanceof Tiger) {
				return;
			}
		}
		
		Stack<Position> newPos = this.field.getFreeNeightbors(this.pos);
		if(newPos != null && newPos.size() > 2 && this.age >= 3) {
			Collections.shuffle(newPos);
			Rabbit child = new Rabbit(this.field, false, newPos.pop());
			
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
