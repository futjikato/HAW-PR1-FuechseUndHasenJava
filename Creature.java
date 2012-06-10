
public abstract class Creature {
	
	protected boolean alive = true;
	
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
		
		if(randAge) {
			this.age = (int)Math.random() * this.getMaxAge();
		}
	}
	
	public boolean process() throws Exception {
		this.age++;
		if(this.age > this.getMaxAge()) {
			this.die();
			return false;
		}
		
		return true;
	}
	
	public abstract int getMaxAge();
	public abstract float[] getColor();
	
	protected void die() throws Exception {
//		this.alive = false;
//		this.field.removeCreature(this, this.pos);
	}
	
	public boolean isAlive() {
		return this.alive;
	}
	
	public Position getPosition() {
		return this.pos;
	}
}
