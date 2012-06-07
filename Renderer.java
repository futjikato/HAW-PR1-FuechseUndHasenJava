import java.util.Stack;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.*;

public class Renderer {
	
	protected int width;
	protected int height;
	protected Simulator simulator;
	protected boolean isRunning = false;
	protected InputHandler input;
	protected long lastFrame;

	public Renderer(Simulator simulator, int width, int height) {
		this.width = width;
		this.height = height;
		this.simulator = simulator;
		this.input = new InputHandler();
		
		this.init();
	}
	
	public void init() {
		try {
			Display.setDisplayMode(new DisplayMode(this.width,this.height));
			Display.setTitle("FŸchse und Hasen - Java");
			Display.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// init OpenGL
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 0, 600, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	
	public void drawCreature(Creature creature) {
		// set the color of the quad (R,G,B,A)
		float[] rgba = creature.getColor();
		GL11.glColor3f(rgba[0], rgba[1], rgba[2]);
			
		Position pos = creature.pos;
		
		// draw quad
		GL11.glBegin(GL11.GL_QUADS);
			
			// coords where to draw the quad
			int x1 = pos.getX() * 10 - 1;
			int x2 = pos.getX() * 10 + 9;
			int y1 = pos.getY() * 10 + 1;
			int y2 = pos.getY() * 10 + 9;
			
		    GL11.glVertex2f(x1,y1);
		    GL11.glVertex2f(x2,y1);
		    GL11.glVertex2f(x2,y2);
		    GL11.glVertex2f(x1,y2);
		GL11.glEnd();
	}
	
	public void drawCreatures(Stack<Creature> creatures) {
		while(!creatures.empty()) {
			Creature currCreature = creatures.pop();
			this.drawCreature(currCreature);
		}
	}
	
	public void render(Stack<Creature> creatures) {
		// Clear the screen and depth buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);	
		
		// draw everything on the field
		this.drawCreatures(creatures);
		
		// update screen
		Display.update();
	}
	
	/** 
	 * Calculate how many milliseconds have passed 
	 * since last frame.
	 * 
	 * @return milliseconds passed since last frame 
	 */
	public int getDelta() {
	    long time = getTime();
	    int delta = (int) (time - lastFrame);
	 
	    return delta;
	}
	
	/**
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	public long getTime() {
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public void start() {
		this.isRunning = true;
		// set an valid lastFrame time on start
		this.lastFrame = this.getTime();
		
		// render loop
		while(this.isRunning) {
			if(this.input.doClose()) {
				this.isRunning = false;
				break;
			}
			
			if(this.getDelta() > 2000) {
				Stack<Creature> creatures = this.simulator.simulateOneStep();
				this.render(creatures);
				System.out.println("Render field");
				this.lastFrame = this.getTime();
			}
		}
		
		Display.destroy();
	}
	
	public void stop() {
		this.isRunning = false;
	}
}
