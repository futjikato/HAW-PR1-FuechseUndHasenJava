
public class Field {
	
	protected int size;
	
	protected Creature[][] fielddata;
	
	/** 
	 * Initialize a new field with the given size
	 * 
	 * @param size
	 */
	public Field(int size) {
		this.size = size;
		this.fielddata = new Creature[size][size];
	}
	
	public void setPosition(Creature creature, Position pos) {
		this.fielddata[pos.getX()][pos.getY()] = creature;
	}
	
	public Position getPositionOfCreature(Creature givenCreature) {
		int x = 0;
		for (Creature[] inner : this.fielddata) {
			int y = 0;
			for (Creature creature : inner) {
				if(creature.equals(givenCreature)) {
					return new Position(x, y);
				}
				y++;
			}
			x++;
		}
		return null;
	}
	
	public boolean isFieldFree(Position pos) {
		return !(this.fielddata[pos.getX()][pos.getY()] instanceof Creature);
	}
	
	public Position[] getNightbornPositions(Position pos) {
		Position[] retVal = new Position[8];
		int index = 0;
		for(int i = -1 ; i <= 1 ; i++) {
			if(i > this.size || i < 0) continue;
			for(int j = -1 ; i <= 1 ; j++) {
				if(j > this.size || j < 0) continue;
				Position testpos = new Position(i,j);
				retVal[index] = testpos;
				index++;
			}
		}
		
		return retVal;
	}
	
	public Position[] getNightbornWithCreature(Position pos, Creature cre) {
		Position[] source = this.getNightbornPositions(pos);
		Position[] retVal = new Position[8];
		int index = 0;
		
		for (Position position : source) {
			if(this.fielddata[position.getX()][position.getY()].getClass().equals(cre.getClass())) {
				retVal[index] = position;
				index++;
			}
		}
		return retVal;
	}
}
