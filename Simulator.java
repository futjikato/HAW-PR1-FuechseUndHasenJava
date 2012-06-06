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
	
	public void run(int steps) {
		// iterate over all creatures in stack
		Stack<Creature> oldstack = this.creatures;
		for(int i = 0 ; i <= steps ; i++) {
			Stack<Creature> newstack = new Stack<Creature>();
			while(!oldstack.empty()) {
				Creature currentCreature = oldstack.pop();
				currentCreature.process();
				
				if(currentCreature.isAlive()) {
					newstack.push(currentCreature);
				}
			}
			
			this.creatures = newstack;
		}
	}
}
