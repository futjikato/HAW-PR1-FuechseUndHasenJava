package de.futjikato.javahasen.simulation;

import java.util.Collections;
import java.util.Stack;

public class Tiger extends Creature {
	public Tiger(Field field, boolean randAge, Position pos) throws Exception {
		super(field, randAge, pos);
	}
	
	@Override
	public boolean process() throws Exception {
		// creature manages aging process
		if(!super.process()) {
			return false;
		}
		
		return true;
	}
	
	public String toString() {
		return "Tiger Pos(" + this.pos.getNewX() + "/" + this.pos.getNewY() + ") Age:" + this.age;
	}
	
	@Override
	public int getMaxAge() {
		return 10;
	}
	
	@Override
	public float[] getColor() {
		float[] rgba = { 0.5f , 0.5f , 1.0f };
		return rgba;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected Class[] getFood() {
		return new Class[]{Rabbit.class};
	}
	
	@Override
	protected int getInitFoodLevel() {
		return 4;
	}
	
	@Override 
	protected void eat(Creature foodObj) throws Exception {
		this.foodLevel += 1;
	}
	
	@Override
	public void spawnChild() throws Exception {
		Stack<Position> neighbor = this.field.getNeighborPositions(this.pos);
		for(Position n : neighbor) {
			Creature content = this.field.getCreatureFromPosition(n);
			if(content != null && content instanceof Tiger) {
				return;
			}
		}
		
		if(this.foodLevel < 3) {
			return;
		}
		
		if(this.age <= 4) {
			return;
		}
		
		Stack<Position> newPos = this.field.getFreeNeightbors(this.pos);
		if(newPos.size() > 0) {
			Collections.shuffle(newPos);
			Tiger child = new Tiger(this.field, false, newPos.pop());
			Simulator.getInstance().addCreature(child);
		}
	}

	@Override
	public String getFieldCodeChar() {
		return "T";
	}
}
