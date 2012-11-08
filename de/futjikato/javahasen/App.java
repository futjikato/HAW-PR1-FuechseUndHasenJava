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
	
	// constants
	public static final int RUNFLAG_STOP = 0;
	public static final int RUNFLAG_MENU = 1;
	public static final int RUNFLAG_SIMULATION = 2;
	
	private int initalTigers = 0;
	private int initalRabbits = 0;
	private int fieldsize = 60;
	private int initalTrees = 0;
	
	public int getInitalTrees() {
		return initalTrees;
	}

	public void setInitalTrees(int initalTrees) {
		this.initalTrees = initalTrees;
	}

	public int getFieldsize() {
		return fieldsize;
	}

	public void setFieldsize(int fieldsize) {
		this.fieldsize = fieldsize;
	}

	public int getInitalTigers() {
		return initalTigers;
	}

	public void setInitalTigers(int initalTigers) {
		this.initalTigers = initalTigers;
	}

	public int getInitalRabbits() {
		return initalRabbits;
	}

	public void setInitalRabbits(int initalRabbits) {
		this.initalRabbits = initalRabbits;
	}

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
		Field battlefield = new Field(this.fieldsize);
		Simulator simulat = Simulator.getInstance();
		simulat.setField(battlefield);
		SimulationRenderer renderer = SimulationRenderer.getInstance();
		
		// add 20 tigers to the field
		try {
			simulat.populate("de.futjikato.javahasen.simulation.Tiger", this.initalTigers);
		} catch ( Exception e ) {
			e.printStackTrace();
			System.out.println("Failed to add tigers :(");
		}
		
		// add 30 rabbits to the field
		try {
			simulat.populate("de.futjikato.javahasen.simulation.Rabbit", this.initalRabbits);
			
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
		
		// grow some trees
		simulat.growTrees(this.initalTrees);
		
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