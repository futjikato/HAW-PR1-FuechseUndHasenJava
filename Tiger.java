public class Tiger extends Creature {
	public Tiger(Field field, boolean randAge, Position pos) {
		super(field, randAge, pos);
	}
	
	public void process() {
		
		// get random free neighbor field
		Position[] neighbor = this.field.getNeighborPositions(this.pos);
		
		// if now free field is available the tiger dies :(
		if(neighbor.length <= 0) {
			this.alive = false;
			return;
		}
		
		Position free;
		do {
			free = neighbor[(int)(Math.random()*neighbor.length)];
		} while(!this.field.isFieldFree(free));
		
		// move creature
		this.field.moveCreature(this, free);
		this.pos = free;
	}
	
	public String toString() {
		return "Tiger Pos(" + this.pos.getX() + "/" + this.pos.getY() + ") Age:" + this.age;
	}
	
	public int getMaxAge() {
		return 20;
	}
	
	public float[] getColor() {
		float[] rgba = { 0.5f , 0.5f , 1.0f };
		return rgba;
	}
}
