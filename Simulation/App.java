package Simulation;
/**
 * @author moritzspindelhirn
 *
 */
public class App {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// init all components we need
		Field battlefield = new Field(50);
		Simulator simulat = Simulator.getInstance();
		simulat.setField(battlefield);
		Renderer renderer = new Renderer(500, 500); // fieldsize * 10px
		
		// add 20 tigers to the field
		try {
			simulat.populate("Simulation.Tiger", 20);
		} catch ( Exception e ) {
			e.printStackTrace();
			System.out.println("Failed to add tigers :(");
		}
		
		// add 30 rabbits to the field
		try {
			simulat.populate("Simulation.Rabbit", 40);
		} catch ( Exception e ) {
			e.printStackTrace();
			System.out.println("Failed to add rabbits :(");
		}
		
		// start the simulation
		renderer.start();
	}

}