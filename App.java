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
		Renderer renderer = new Renderer(500, 500); // fieldsize * 10px
		renderer.init();
		renderer.setField(battlefield);
		renderer.setSimulator(simulat);
		
		// add 10 tigers to the field
		simulat.populate("Tiger", 10);
		
		// render the scene
		renderer.render();
	}

}