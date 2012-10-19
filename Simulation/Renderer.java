package Simulation;
import java.util.Stack;

import org.lwjgl.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;

public class Renderer {
	
	protected int width;
	protected int height;
	protected boolean isRunning = false;
	protected InputHandler input;
	protected long lastFrame;
	protected long lastGeneratione;
	protected long lastFPS;
	protected int fps = 0;
	
	protected float cx = 0;
	protected float cy = 0;
	
	protected float camera_x = 25;
	protected float camera_y = 120;
	protected float camera_rotation = 0;
	
	public Renderer(int width, int height) {
		this.width = width;
		this.height = height;
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
		GLU.gluPerspective(45.0f, ((float) this.width) / ((float) this.height), 0.1f, 200.0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		
		GL11.glShadeModel(GL11.GL_SMOOTH); // Enables Smooth Shading
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Black Background
		GL11.glClearDepth(1.0f); // Depth Buffer Setup
		GL11.glEnable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
		GL11.glDepthFunc(GL11.GL_LEQUAL); // The Type Of Depth Test To Do
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST); // Really Nice Perspective Calculations
 
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
		
		GL11.glBegin(GL11.GL_QUADS);
		
			GL11.glVertex3f(0, -0.5f, 0); // far left edge
			GL11.glVertex3f(50, -0.5f, 0); // near left edge
			GL11.glVertex3f(50, -0.5f, 50); // near right edge
			GL11.glVertex3f(0, -0.5f, 50); // far right edge
		
		GL11.glEnd();
		GL11.glFlush();
	}
	
	public void drawCreature(Creature creature) {
		// set the color of the quad (R,G,B)
		float[] rgba = creature.getColor();
		GL11.glColor3f(rgba[0], rgba[1], rgba[2]);
			
		// move to the right position
		Position pos = creature.pos;
		this.moveTo(pos.getX(), pos.getY());
		
		// draw cubes
		this.drawCube(0.25f);
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
		
		// set perspective
		this.moveTo(0, 0);
		GL11.glRotatef(40, 1, 0, 0);
		GL11.glTranslatef(0, 20, 0);
		
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
		
		// update screen
		Display.update();
		this.lastFrame = this.getTime();
		
		// update fps
		this.updateFPS();
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
	 * Calculate how many milliseconds have passed 
	 * since last frame.
	 * 
	 * @return milliseconds passed since last frame 
	 */
	public int getGenerationDelta() {
	    long time = getTime();
	    int delta = (int) (time - this.lastGeneratione);
	 
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
	
	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public void updateFPS() {
	    if (getTime() - this.lastFPS > 1000) {
	        System.out.println("FPS : " + this.fps);
	        this.fps = 0; //reset the FPS counter
	        this.lastFPS += 1000; //add one second
	    }
	    fps++;
	}
	
	public void moveTo(float x, float y) {
		float diffx = (x - 25) - this.cx;
		float diffy = (y - 120) - this.cy;
		GL11.glTranslatef(diffx, 0, diffy);
		this.cx = (x - 25);
		this.cy = (y - 120);
	}
	
	public void start() {
		
		// render inital state
		Simulator sim = Simulator.getInstance();
		this.render(sim.getCreatures());
		this.lastFrame = this.getTime();
		this.lastFPS = this.getTime();
		
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
				
				Stack<Creature> creatures = null;
				if(this.getGenerationDelta() > 1000) {
					creatures = sim.simulateOneStep();
					if(creatures == null) {
						System.out.println("-END- with error");
						this.stop();
						return;
					}
					
					this.lastGeneratione = this.getTime();
				} else {
					creatures = sim.getCreatures();
				}
				
				this.render(creatures);
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
