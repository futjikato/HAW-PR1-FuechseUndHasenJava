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
		
		GL11.glViewport(0, 0, width, height);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();		
		GLU.gluPerspective(45.0f, ((float) this.width) / ((float) this.height), 0.1f, 100.0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		
		GL11.glShadeModel(GL11.GL_SMOOTH); // Enables Smooth Shading
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Black Background
		GL11.glClearDepth(1.0f); // Depth Buffer Setup
		GL11.glEnable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
		GL11.glDepthFunc(GL11.GL_LEQUAL); // The Type Of Depth Test To Do
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST); // Really Nice Perspective Calculations
 
	}
	
	public void drawCreature(Creature creature) {
		// set the color of the quad (R,G,B)
		float[] rgba = creature.getColor();
		GL11.glColor3f(rgba[0], rgba[1], rgba[2]);
			
		// move to the right position
		Position pos = creature.pos;
		GL11.glTranslatef(pos.getX()*-0.25f, 0.0f, pos.getY()*-0.25f);
		
		// draw quad
		GL11.glBegin(GL11.GL_QUADS);
			
			GL11.glVertex3f(0.25f, 0.25f, -0.25f); // Top Right Of The Quad (Top)
			GL11.glVertex3f(-0.25f, 0.25f, -0.25f); // Top Left Of The Quad (Top)
			GL11.glVertex3f(-0.25f, 0.25f, 0.25f); // Bottom Left Of The Quad (Top)
			GL11.glVertex3f(0.25f, 0.25f, 0.25f); // Bottom Right Of The Quad (Top)
	
			GL11.glVertex3f(0.25f, -0.25f, 0.25f); // Top Right Of The Quad (Bottom)
			GL11.glVertex3f(-0.25f, -0.25f, 0.25f); // Top Left Of The Quad (Bottom)
			GL11.glVertex3f(-0.25f, -0.25f, -0.25f); // Bottom Left Of The Quad (Bottom)
			GL11.glVertex3f(0.25f, -0.25f, -0.25f); // Bottom Right Of The Quad (Bottom)
	
			GL11.glVertex3f(0.25f, 0.25f, 0.25f); // Top Right Of The Quad (Front)
			GL11.glVertex3f(-0.25f, 0.25f, 0.25f); // Top Left Of The Quad (Front)
			GL11.glVertex3f(-0.25f, -0.25f, 0.25f); // Bottom Left Of The Quad (Front)
			GL11.glVertex3f(0.25f, -0.25f, 0.25f); // Bottom Right Of The Quad (Front)
	
			GL11.glVertex3f(0.25f, -0.25f, -0.25f); // Bottom Left Of The Quad (Back)
			GL11.glVertex3f(-0.25f, -0.25f, -0.25f); // Bottom Right Of The Quad (Back)
			GL11.glVertex3f(-0.25f, 0.25f, -0.25f); // Top Right Of The Quad (Back)
			GL11.glVertex3f(0.25f, 0.25f, -0.25f); // Top Left Of The Quad (Back)
	
			GL11.glVertex3f(-0.25f, 0.25f, 0.25f); // Top Right Of The Quad (Left)
			GL11.glVertex3f(-0.25f, 0.25f, -0.25f); // Top Left Of The Quad (Left)
			GL11.glVertex3f(-0.25f, -0.25f, -0.25f); // Bottom Left Of The Quad (Left)
			GL11.glVertex3f(-0.25f, -0.25f, 0.25f); // Bottom Right Of The Quad (Left)
	
			GL11.glVertex3f(0.25f, 0.25f, -0.25f); // Top Right Of The Quad (Right)
			GL11.glVertex3f(0.25f, 0.25f, 0.25f); // Top Left Of The Quad (Right)
			GL11.glVertex3f(0.25f, -0.25f, 0.25f); // Bottom Left Of The Quad (Right)
			GL11.glVertex3f(0.25f, -0.25f, -0.25f); // Bottom Right Of The Quad (Right)
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
		GL11.glLoadIdentity();
		
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
		
		// render inital state
		this.render(this.simulator.getCreatures());
		this.lastFrame = this.getTime();
		
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
					System.out.print("render...");
					this.render(creatures);
					System.out.print("done\n");
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
