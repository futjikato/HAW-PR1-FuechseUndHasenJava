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
		Simulator simulat = new Simulator(battlefield);
		Renderer renderer = new Renderer(simulat, 500, 500); // fieldsize * 10px
		
		// add 10 tigers to the field
		try {
			simulat.populate("Tiger", 10);
		} catch ( Exception e ) {
			e.printStackTrace();
			System.out.println("Failed to add tigers :(");
		}
		
		// add 30 rabbits to the field
		try {
			simulat.populate("Rabbit", 30);
		} catch ( Exception e ) {
			e.printStackTrace();
			System.out.println("Failed to add rabbits :(");
		}
		
		// start the simulation
		renderer.start();
	}

}