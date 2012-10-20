package de.futjikato.javahasen.simulation;
public abstract class Creature {
	
	protected boolean alive = true;
	
	protected int foodLevel;
	
	protected int age = 0;
	
	protected Field field;
	
	protected Position pos;
	
	public Creature(Field field, boolean randAge, Position pos) throws Exception {
		
		if(!(pos instanceof Position)) {
			throw new Exception("Invalid Position");
		}
		
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
		String[] food = this.getFood();
		Position newPos = null;
		if(food.length > 0) {
			Creature eatable = this.field.getRandomNeightbor(this.pos, food);
			if(eatable != null) {
				eatable.die();
				this.eat(eatable);
				newPos = eatable.getPosition();
			} else {
				this.foodLevel--;
			}
			
			if(this.foodLevel <= 0) {
				this.die();
				return false;
			}
		}
		
		if(newPos == null){
			// get random free neighbor field
			newPos = this.field.getRandomFreeNeightbor(this.pos);
		}
		
		if(newPos == null){
			this.die();
			return false;
		}
		
		// spawn child
		this.spawnChild();
		
		// move creature
		this.field.moveCreature(this, newPos);
		this.pos = newPos;
		
		return true;
	}
	
	public abstract int getMaxAge();
	public abstract float[] getColor();
	protected abstract String[] getFood();
	protected abstract int getInitFoodLevel();
	public abstract String getFieldCodeChar();
	
	protected void eat(Creature foodObj) throws Exception {
		// do nothing on default
	}
	
	public void die() throws Exception {
		this.alive = false;
		this.field.removeCreature(this.pos);
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
