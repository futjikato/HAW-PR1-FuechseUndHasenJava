package de.futjikato.javahasen;

import de.futjikato.javahasen.menu.MenuRenderer;
import de.futjikato.javahasen.simulation.Field;
import de.futjikato.javahasen.simulation.Renderer;
import de.futjikato.javahasen.simulation.Simulator;

public class App {
	
	protected static App instance;
	
	protected int startOnNext = 1; // start with menu
	
	// constants
	public static final int RUNFLAG_STOP = 0;
	public static final int RUNFLAG_MENU = 1;
	public static final int RUNFLAG_SIMULATION = 2;
	
	public static void main(String[] args) {
		// start new app
		App app = App.getInstance();
		
		try {
			app.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static App getInstance() {
		if(App.instance == null) {
			App.instance = new App();
		}
		
		return App.instance;
	}
	
	protected void next() throws Exception {
		switch(this.startOnNext) {
		
			case App.RUNFLAG_STOP:
				return;
		
			case App.RUNFLAG_MENU:
				this.startMenu();
				break;
				
			case App.RUNFLAG_SIMULATION:
				this.startSimulation();
				break;
				
			default:
				throw new Exception("Unknown startflag found");
		}
		
		this.next();
	}
	
	public void setNext(int flag) {
		this.startOnNext = flag;
	}
	
	public void startMenu() {
		MenuRenderer menuRenderer = new MenuRenderer();
		menuRenderer.start();
	}
	
	public void startSimulation() {
		// init all components we need
		Field battlefield = new Field(60);
		Simulator simulat = Simulator.getInstance();
		simulat.setField(battlefield);
		Renderer renderer = new Renderer();
		
		// add 20 tigers to the field
		try {
			simulat.populate("de.futjikato.javahasen.simulation.Tiger", 80);
		} catch ( Exception e ) {
			e.printStackTrace();
			System.out.println("Failed to add tigers :(");
		}
		
		// add 30 rabbits to the field
		try {
			simulat.populate("de.futjikato.javahasen.simulation.Rabbit", 60);
		} catch ( Exception e ) {
			e.printStackTrace();
			System.out.println("Failed to add rabbits :(");
		}
		
		// start the simulation
		renderer.start();
	}

}