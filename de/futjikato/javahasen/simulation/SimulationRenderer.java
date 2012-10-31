package de.futjikato.javahasen.simulation;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Stack;

import org.lwjgl.opengl.*;
import de.futjikato.javahasen.App;
import de.futjikato.javahasen.Renderer;
import de.futjikato.javahasen.RendererException;
import de.futjikato.javahasen.ui.Button;
import de.futjikato.javahasen.ui.PauseButton;

public class SimulationRenderer extends Renderer {
	
	private InputHandler input;
	
	private float cx = 0;
	private float cy = 0;
	
	protected long lastGeneratione;
	
	private float camera_x = 25;
	private float camera_y = 120;
	private float camera_rotation = 0;
	
	private int stepInterval = 100;
	private static SimulationRenderer instane;
	
	protected Button[] buttons = new Button[] {
		new PauseButton(5, 5)
	};
	
	private SimulationRenderer() {
		
		// Singleton
		SimulationRenderer.instane = this;
		
		Field field = Simulator.getInstance().getField();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Display.setResizable(true);
		
		// init window width
		this.width = field.getSize() * 10;
		if(this.width > dim.getWidth()) {
			this.width = (int) dim.getWidth();
		}
		
		// init window height
		this.height = field.getSize() * 10;
		if(this.height > dim.getHeight()) {
			this.height = (int) dim.getHeight();
		}
		
		this.input = new InputHandler();
	}
	
	public static SimulationRenderer getInstance() {
		if(SimulationRenderer.instane == null) {
			SimulationRenderer.instane = new SimulationRenderer();
		}
		
		return SimulationRenderer.instane;
	}
	
	protected void drawCube(float size) {
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glVertex3f(size, 1, -size); // Top Right Of The Quad (Top)
		GL11.glVertex3f(-size, 1, -size); // Top Left Of The Quad (Top)
		GL11.glVertex3f(-size, 1, size); // Bottom Left Of The Quad (Top)
		GL11.glVertex3f(size, 1, size); // Bottom Right Of The Quad (Top)

		GL11.glVertex3f(size, -1, size); // Top Right Of The Quad (Bottom)
		GL11.glVertex3f(-size, -1, size); // Top Left Of The Quad (Bottom)
		GL11.glVertex3f(-size, -1, -size); // Bottom Left Of The Quad (Bottom)
		GL11.glVertex3f(size, -1, -size); // Bottom Right Of The Quad (Bottom)

		GL11.glVertex3f(size, 1, size); // Top Right Of The Quad (Front)
		GL11.glVertex3f(-size, 1, size); // Top Left Of The Quad (Front)
		GL11.glVertex3f(-size, -1, size); // Bottom Left Of The Quad (Front)
		GL11.glVertex3f(size, -1, size); // Bottom Right Of The Quad (Front)

		GL11.glVertex3f(size, -1, -size); // Bottom Left Of The Quad (Back)
		GL11.glVertex3f(-size, -1, -size); // Bottom Right Of The Quad (Back)
		GL11.glVertex3f(-size, 1, -size); // Top Right Of The Quad (Back)
		GL11.glVertex3f(size, 1, -size); // Top Left Of The Quad (Back)

		GL11.glVertex3f(-size, 1, size); // Top Right Of The Quad (Left)
		GL11.glVertex3f(-size, 1, -size); // Top Left Of The Quad (Left)
		GL11.glVertex3f(-size, -1, -size); // Bottom Left Of The Quad (Left)
		GL11.glVertex3f(-size, -1, size); // Bottom Right Of The Quad (Left)

		GL11.glVertex3f(size, 1, -size); // Top Right Of The Quad (Right)
		GL11.glVertex3f(size, 1, size); // Top Left Of The Quad (Right)
		GL11.glVertex3f(size, -1, size); // Bottom Left Of The Quad (Right)
		GL11.glVertex3f(size, -1, -size); // Bottom Right Of The Quad (Right)
		
		GL11.glEnd();
		GL11.glFlush();
	}
	
	public void drawField() {
		// set field color to some green
		GL11.glColor3f(0.1f, 0.9f, 0.25f);
		
		// move render pencil to 0/0
		this.moveTo(0, 0);
		int fieldSize = Simulator.getInstance().getField().getSize();
		
		GL11.glBegin(GL11.GL_QUADS);
		
			GL11.glVertex3f(0, -0.5f, 0); // far left edge
			GL11.glVertex3f(fieldSize, -0.5f, 0); // near left edge
			GL11.glVertex3f(fieldSize, -0.5f, fieldSize); // near right edge
			GL11.glVertex3f(0, -0.5f, fieldSize); // far right edge
		
		GL11.glEnd();
		GL11.glFlush();
	}
	
	public void drawCreature(Creature creature) {
		
		// do not draw dead animals
		if(!creature.isAlive()) {
			return;
		}
		
		// set the color of the quad (R,G,B)
		float[] rgba = creature.getColor();
		GL11.glColor3f(rgba[0], rgba[1], rgba[2]);
			
		// get delta 
		float progress = (float)this.getGenerationDelta() / this.stepInterval;
		
		// get current position in animation
		Position pos = creature.pos;
		float animX = pos.getLastX() + ((pos.getNewX() - pos.getLastX()) * progress);
		float animY = pos.getLastY() + ((pos.getNewY() - pos.getLastY()) * progress);
		
		this.moveTo(animX, animY);
		
		// draw cubes
		this.drawCube(0.25f);
	}
	
	public void drawCreatures(Stack<Creature> creatures) {
		while(!creatures.empty()) {
			Creature currCreature = creatures.pop();
			this.drawCreature(currCreature);
		}
	}

	public void moveTo(float x, float y) {
		float diffx = (x - 25) - this.cx;
		float diffy = (y - 120) - this.cy;
		GL11.glTranslatef(diffx, 0, diffy);
		this.cx = (x - 25);
		this.cy = (y - 120);
	}

	@Override
	protected void render3D() throws RendererException {
		
		Simulator sim = Simulator.getInstance();
		
		if(this.getGenerationDelta() > this.stepInterval) {
			this.lastGeneratione = this.getTime();
			
			boolean succ = sim.nextStep();
			if(!succ) {
				throw new RendererException("Something went wrong while generating the next generation. Stop simulation");
			}
			
		}
		
		// get creatures from simulation
		Stack<Creature> creatures = sim.getCreatures();
		
		// set perspective
		int fieldSize = Simulator.getInstance().getField().getSize();
		this.moveTo(0, 0);
		GL11.glRotatef(40, 1, 0, 0);
		GL11.glTranslatef(0, (20/50*fieldSize), (-0.25f*fieldSize));
		
		// draw field
		this.drawField();
		
		// draw everything on the field
		this.drawCreatures(creatures);
		
		// move viewport/camera
		this.camera_x = this.input.handleCameraX(this.camera_x);
		this.camera_y = this.input.handleCameraY(this.camera_y);
		this.camera_rotation = this.input.handleRotation(this.camera_rotation);
		
		GL11.glLoadIdentity();
		GL11.glRotatef(this.camera_rotation, 0, 1, 0);
		this.moveTo(this.camera_x, this.camera_y);
	}
	
	/** 
	 * Calculate how many milliseconds have passed 
	 * since last frame.
	 * 
	 * @return milliseconds passed since last frame 
	 */
	public long getGenerationDelta() {
	    long time = getTime();
	    long delta = (time - this.lastGeneratione);
	 
	    return delta;
	}

	protected void drawUi() throws RendererException {
		for(Button btn : this.buttons) {
			btn.checkEvents();
			btn.draw();
		}
	}
	
	@Override
	protected void render2D() throws RendererException {
		this.drawUi();
	}

	@Override
	protected int getAppRunflag() {
		return App.RUNFLAG_MENU;
	}

	@Override
	protected void printFPS(int fps) {
		System.out.println("FPS : " + fps);
	}
}
