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
		return "Tiger Pos(" + this.pos.getX() + "/" + this.pos.getY() + ") Age:" + this.age;
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
	
	@Override
	protected String[] getFood() {
		return new String[]{"Rabbit"};
	}
	
	@Override
	protected int getInitFoodLevel() {
		return 4;
	}
	
	@Override 
	protected void eat(Creature foodObj) throws Exception {
		this.foodLevel += 2;
		this.spawnChild();
	}
	
	@Override
	public void spawnChild() throws Exception {
		if(this.foodLevel >= 3 && this.age > 4) {
			Position newPos = this.field.getRandomFreeNeightbor(this.pos);
			if(newPos != null) {
				Tiger child = new Tiger(this.field, false, newPos);
				Simulator.getInstance().addCreature(child);
			}
		}
	}

	@Override
	public String getFieldCodeChar() {
		return "T";
	}
}
