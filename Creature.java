
public abstract class Creature {
	
	protected boolean alive = true;
	
	protected int age = 0;
	
	protected Field field;
	
	protected Position pos;
	
	public Creature(Field field, boolean randAge, Position pos) {
		this.pos = pos;
		this.field = field;
		
		if(randAge) {
			this.age = (int)Math.random() * this.getMaxAge();
		}
	}
	
	public abstract void process();
	public abstract int getMaxAge();
	public abstract float[] getColor();
	
	public boolean isAlive() {
		return true;
	}
	
	public Position getPosition() {
		return this.pos;
	}
}
