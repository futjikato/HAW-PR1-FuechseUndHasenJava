public class Rabbit extends Creature {
	
	public Rabbit(Field field, boolean randAge, Position pos) throws Exception {
		super(field, randAge, pos);
	}
	
	@Override
	public boolean process() throws Exception {
		// creature manages aging process
		if(!super.process()) {
			return false;
		}
		
		// rabbits die when they are < 3 year old and no > 3 year old rabbit is a neightbor
		if(this.age < 3) {
			Position[] rabbitPos = this.field.getNeighborWithCreature(this.pos, this);
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
		return 5;
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
		//TODO fix me !
		Position newPos = this.field.getRandomFreeNeightbor(this.pos);
		if(newPos != null && this.age >= 3) {
			Rabbit child = new Rabbit(this.field, false, newPos);
			Simulator.getInstance().addCreature(child);
		}
	}
	
	@Override
	public String getFieldCodeChar() {
		return "H";
	}
}
