package de.futjikato.javahasen.simulation;
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.Stack;

public class Simulator {
	
	protected Stack<Creature> creatures = new Stack<Creature>();
	
	private Stack<Class<Creature>> existingCreatureClasses = new Stack<Class<Creature>>();
	
	private int simulationCreatureIndex = 0;
	
	protected Field battlefield;
	
	protected static Simulator instance;
	
	protected Simulator() {
		Simulator.instance = this;
	}
	
	public static Simulator getInstance() {
		if(Simulator.instance == null) {
			Simulator.instance = new Simulator();
		}
		return Simulator.instance;
	}
	
	public void setField(Field battlefield) {
		this.battlefield = battlefield;
	}
	
	public void addCreature(Creature createure) {
		this.creatures.push(createure);
	}
	
	@SuppressWarnings("unchecked")
	public Stack<Creature> getCreatures() {
		return (Stack<Creature>)this.creatures.clone();
	}
	
	public Creature getRandomCreate(Class<Creature> createClass) {
		// collect all creatures of the given type
		Stack<Creature> matches = new Stack<Creature>();
		for(Creature testCrature : this.creatures) {
			if(testCrature.getClass() == createClass) {
				matches.add(testCrature);
			}
		}
		
		if(matches.size() == 0) {
			return null;
		}
		
		// shuffle and return random creature
		Collections.shuffle(matches);
		return matches.pop();
	}
	
	private boolean simulateOneStep() {
		try {
			// iterate over all creatures in stack
			Stack<Creature> oldstack = this.creatures;
			Stack<Creature> newstack = new Stack<Creature>();
			
			// current creature type to process
			Class<Creature> processCreature = this.existingCreatureClasses.get(this.simulationCreatureIndex);
			
			while(!oldstack.empty()) {
				Creature currentCreature = oldstack.pop();
				
				currentCreature.getPosition().reachedNewPosition();
				
				// process creature of given class
				if(currentCreature.getClass().equals(processCreature)) {
					currentCreature.process();
				}
				
				if(currentCreature.isAlive()) {
					newstack.push(currentCreature);
				}
			}
			
			this.creatures = newstack;
		} catch ( Exception e ) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			return false;
		}
		
		return true;
	}
	
	public boolean populate(String baseClass, int number) {
		ClassLoader myClassLoader = ClassLoader.getSystemClassLoader();
		try {
			
			//TODO may improve loading here ? how to check if the class extends creature ?
			@SuppressWarnings("unchecked")
			Class<Creature> creatureClass = (Class<Creature>) myClassLoader.loadClass(baseClass);
			
			// add class to list of using creatures if not already added
			if(!this.existingCreatureClasses.contains(creatureClass)) {
				this.existingCreatureClasses.push(creatureClass);
			}
			
			// randomize free positions
			// and do it the RIGHT way :-) http://blog.ryanrampersad.com/2012/03/03/more-on-shuffling-an-array-correctly/
			Stack<Position> freeList = this.battlefield.getAllFreePositions();
			Collections.shuffle(freeList);
			
			for(int i = 0 ; i <= number ; i++) {
				// get free positions from list
				if(freeList.isEmpty()) {
					throw new Exception("No more free positions on the field");
				}
				
				Position pos = freeList.pop();
				
				// create instance of the class
				Constructor<?>[] constructors = creatureClass.getDeclaredConstructors();
				Constructor<?> constructor = constructors[0];
				Creature inst = creatureClass.getConstructor(constructor.getParameterTypes()).newInstance(this.battlefield, true, pos);
				
				this.addCreature(inst);
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		return true;
	}
	
	public Field getField() {
		return this.battlefield;
	}
	
	protected boolean nextStep() {
		
		// no create in field -> stop
		if(this.existingCreatureClasses.size() == 0) {
			return false;
		}
		
		// manage create to simulate step for
		if(this.simulationCreatureIndex > this.existingCreatureClasses.size() - 1) {
			this.simulationCreatureIndex = 0;
		}
		
		// make simulation
		boolean succ = this.simulateOneStep();
		
		// increase creature index for next call
		this.simulationCreatureIndex++;
		
		return succ;
	}
}
