public class Tiger extends Creature {
	public Tiger(Field field, boolean randAge, Position pos) {
		super(field, randAge, pos);
	}

	public void process() {
		System.out.println(this);
	}
	
	public String toString() {
		return "Tiger Pos(" + this.pos.getX() + "/" + this.pos.getY() + ") Age:" + this.age;
	}
	
	public int getMaxAge() {
		return 20;
	}
}
