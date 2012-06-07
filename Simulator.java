import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Simulator {
	
	protected Stack<Creature> creatures = new Stack<Creature>();
	
	protected Field battlefield;
	
	public Simulator(Field battlefield){
		this.battlefield = battlefield;
	}
	
	public void addCreature(Creature createure) {
		this.creatures.push(createure);
	}
	
	public Stack<Creature> getCreatures() {
		return this.creatures;
	}
	
	@SuppressWarnings("unchecked")
	public Stack<Creature> simulateOneStep() {
		// iterate over all creatures in stack
		Stack<Creature> oldstack = this.creatures;
		Stack<Creature> newstack = new Stack<Creature>();
		while(!oldstack.empty()) {
			Creature currentCreature = oldstack.pop();
			currentCreature.process();
			
			if(currentCreature.isAlive()) {
				newstack.push(currentCreature);
			}
		}
		
		System.out.println(newstack.size());
		
		// ... save & return new stack
		this.creatures = newstack;
		return (Stack<Creature>)newstack.clone();
	}
	
	public boolean populate(String baseClass, int number) {
		ClassLoader myClassLoader = ClassLoader.getSystemClassLoader();
		try {
			Class<?> creatureClass = myClassLoader.loadClass(baseClass);
			
			//TODO Check if the given class extends Creature
			
			// randomize free positions
			// and do it the RIGHT way :-) http://blog.ryanrampersad.com/2012/03/03/more-on-shuffling-an-array-correctly/
			Position[] freeArray = this.battlefield.getAllFreePositions();
			ArrayList<Position> freeList = new ArrayList<Position>();
			for (Position position : freeArray) {
				freeList.add(position);
			}
			Collections.shuffle(freeList);
			
			for(int i = 0 ; i < number ; i++) {
				// get free positions from list
				if(freeList.isEmpty()) {
					throw new Exception("No more free positions on the field");
				}
				Position pos = freeList.get(0);
				freeList.remove(0);
				
				// create instance of the class
				Constructor<?>[] constructors = creatureClass.getDeclaredConstructors();
				Constructor<?> constructor = constructors[0];
				Creature inst = (Creature)creatureClass.getConstructor(constructor.getParameterTypes()).newInstance(this.battlefield, true, pos);
				
				this.creatures.push(inst);
			}
		} catch (Exception e) {
			System.out.print(e.toString() + "\n");
			return false;
		}
		return true;
	}
}
