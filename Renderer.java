import java.util.Stack;

import org.lwjgl.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;

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
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glShadeModel(GL11.GL_SMOOTH);  
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();		
		GLU.gluPerspective(45.0f, ((float) 800) / ((float) 600), 0.1f, 100.0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
	}
	
	public void drawCreature(Creature creature) {
		// set the color of the quad (R,G,B)
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
			
		    GL11.glVertex3f(x1, 1, y1);
		    GL11.glVertex3f(x2, 1, y1);
		    GL11.glVertex3f(x2, 1, y2);
		    GL11.glVertex3f(x1, 1, y2);
		GL11.glEnd();
		GL11.glFlush();
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
		try {
			this.isRunning = true;
			// set an valid lastFrame time on start
			this.lastFrame = this.getTime();
			
			// render loop
			while(this.isRunning) {
				if(this.input.doClose()) {
					this.isRunning = false;
					break;
				}
				
				if(this.getDelta() > 1000) {
					Stack<Creature> creatures = this.simulator.simulateOneStep();
					this.render(creatures);
					this.lastFrame = this.getTime();
				}
			}
		} catch ( Exception e ) {
			// if something went wrong make a clean quit
			this.isRunning = false;
			System.out.println(e);
		}
		
		Display.destroy();
	}
	
	public void stop() {
		this.isRunning = false;
	}
}
