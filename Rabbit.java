import java.util.Collections;
import java.util.Stack;


public class Rabbit extends Creature {
	
	public Rabbit(Field field, boolean randAge, Position pos) {
		super(field, randAge, pos);
	}
	
	@Override
	public boolean process() throws Exception {
		// creature manages aging process
		if(!super.process()) {
			return false;
		}
		
		// get random free neighbor field
		Stack<Position> neighbor = this.field.getFreeNeighborPositions(this.pos);
		Collections.shuffle(neighbor);
		Position newPos = neighbor.pop();
		
		// move creature
		this.field.moveCreature(this, newPos);
		this.pos = newPos;
		
		return true;
	}

	@Override
	public int getMaxAge() {
		return 12;
	}

	@Override
	public float[] getColor() {
		float[] rgba = { 0.1f , 0.9f , 1.0f };
		return rgba;
	}

}
