public class Rabbit extends Creature {
	
	protected int spawnInterval = 2;
	
	public Rabbit(Field field, boolean randAge, Position pos) throws Exception {
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

	@Override
	public int getMaxAge() {
		return 3;
	}

	@Override
	public float[] getColor() {
		float[] rgba = { 0.1f , 0.9f , 1.0f };
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
	protected void eat(Creature foodObj) {
		
	}
	
	@Override
	public void spawnChild() throws Exception {
		Position newPos = this.field.getRandomFreeNeightbor(this.pos);
		spawnInterval--;
		if(newPos != null && spawnInterval <= 0) {
			this.spawnInterval = 2;
			Rabbit child = new Rabbit(this.field, false, newPos);
			Simulator.getInstance().addCreature(child);
		}
	}
}
