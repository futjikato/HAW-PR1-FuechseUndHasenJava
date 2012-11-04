package de.futjikato.javahasen;

import java.util.Collections;
import java.util.Stack;

import de.futjikato.javahasen.menu.MenuRenderer;
import de.futjikato.javahasen.simulation.Creature;
import de.futjikato.javahasen.simulation.Field;
import de.futjikato.javahasen.simulation.Rabbit;
import de.futjikato.javahasen.simulation.SimulationRenderer;
import de.futjikato.javahasen.simulation.Simulator;

public class App {
	
	protected static App instance;
	
	protected int startOnNext = 1; // start with menu
	
	protected int initalSickRabbits = 10;
	
	private int creatureDensity = 0;
	
	// constants
	public static final int RUNFLAG_STOP = 0;
	public static final int RUNFLAG_MENU = 1;
	public static final int RUNFLAG_SIMULATION = 2;
	
	public static final int CREATURE_INIT_MAX_RABBITS = 200;
	public static final int CREATURE_INIT_MAX_TIGERS = 250;
	
	public static void main(String[] args) {
		// start new app
		App app = App.getInstance();
		
		try {
			app.next();
		} catch (Exception e) {
			e.printStackTrace();
			app.setNext(App.RUNFLAG_STOP);
		}
	}
	
	public static App getInstance() {
		if(App.instance == null) {
			App.instance = new App();
		}
		
		return App.instance;
	}
	
	public int getCreatureDensity() {
		return creatureDensity;
	}

	public void setCreatureDensity(int creatureDensity) {
		this.creatureDensity = creatureDensity;
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
		try {
			MenuRenderer menuRenderer = MenuRenderer.getInstance();
			menuRenderer.start();
		} catch (Exception e) {
			// stop on error
			this.setNext(App.RUNFLAG_STOP);
			
			e.printStackTrace();
		}
	}
	
	public void startSimulation() {
		// init all components we need
		Field battlefield = new Field(60);
		Simulator simulat = Simulator.getInstance();
		simulat.setField(battlefield);
		SimulationRenderer renderer = SimulationRenderer.getInstance();
		
		// add 20 tigers to the field
		try {
			simulat.populate("de.futjikato.javahasen.simulation.Tiger", (App.CREATURE_INIT_MAX_RABBITS/100*this.creatureDensity));
		} catch ( Exception e ) {
			e.printStackTrace();
			System.out.println("Failed to add tigers :(");
		}
		
		// add 30 rabbits to the field
		try {
			simulat.populate("de.futjikato.javahasen.simulation.Rabbit", (App.CREATURE_INIT_MAX_TIGERS/100*this.creatureDensity));
			
			Stack<Creature> creatures = simulat.getCreatures();
			// collect all creatures of the given type
			Stack<Creature> matches = new Stack<Creature>();
			for(Creature testCrature : creatures) {
				if(testCrature.getClass() == Rabbit.class) {
					matches.add(testCrature);
				}
			}
			Collections.shuffle(matches);
			
			// get a random rabbit and make it sick
			for(int i = 0 ; i < this.initalSickRabbits ; i++) {
				if(matches.size() == 0) {
					break;
				}
				
				Rabbit selected = (Rabbit)matches.pop();
				selected.poison();
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			System.out.println("Failed to add rabbits :(");
		}
		
		try {
			// start the simulation
			renderer.start();
		} catch ( RendererException e ) {
			// stop on error
			this.setNext(App.RUNFLAG_STOP);
			
			e.printStackTrace();
		}
	}

	public Renderer getActiveRenderer() {
		
		if(this.startOnNext == App.RUNFLAG_MENU) {
			return MenuRenderer.getInstance();
		}
		
		if(this.startOnNext == App.RUNFLAG_SIMULATION) {
			return SimulationRenderer.getInstance();
		}
		
		return null;
	}

}