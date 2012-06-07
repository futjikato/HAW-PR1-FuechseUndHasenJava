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
		simulat.populate("Tiger", 10);
		
		// add 30 rabbits to the field
		simulat.populate("Rabbit", 30);
		
		// start the simulation
		renderer.start();
	}

}