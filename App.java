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
		Renderer renderer = new Renderer(battlefield, 500, 500); // fieldsize * 10px
		Simulator simulat = new Simulator(battlefield, renderer);
		
		// add 10 tigers to the field
		simulat.populate("Tiger", 10);
		simulat.run(1);
	}

}