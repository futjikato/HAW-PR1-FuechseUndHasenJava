/**
 * @author moritzspindelhirn
 *
 */
public class App {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Field battlefield = new Field(50);
		Simulator simulat = new Simulator(battlefield);
		
		simulat.populate("Tiger", 10);
		
		simulat.run(1);
	}

}