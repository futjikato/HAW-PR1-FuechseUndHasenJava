package de.futjikato.javahasen;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import de.futjikato.javahasen.ui.UserInterface;

abstract public class Renderer {

	// Window settings
	protected String title = "Javahasen";
	protected int width = 0;
	protected int height = 0;
	
	// FPS stuff
	private long lastFrame;
	private long lastFPS;
	private int fps = 0;
	
	// Flags
	private boolean isRunning = false;
	private boolean hasError = false;
	
	public void stop() throws RendererException {
		if(!this.isRunning) {
			throw new RendererException("Renderer is not running");
		}
		
		this.isRunning = false;
	}
	
	public void start() throws RendererException {
		if(this.isRunning) {
			throw new RendererException("Renderer is already running");
		}
		
		this.isRunning = true;
		this.renderLoop();
	}
	
	public boolean hasError() {
		return this.hasError;
	}
	
	/**
	 * ++++++++++++++++++++++++++++++++++++++++++++
	 * Rendering
	 * ++++++++++++++++++++++++++++++++++++++++++++
	 */
	
	private void renderLoop() {
		try {
			// init
			this.initFPS();
			this.initWindow();
			
			// render loop
			while(this.isRunning) {
				
				if(Display.isCloseRequested()) {
					this.isRunning = false;
					
					// send app whats up next
					App.getInstance().setNext(this.getAppRunflag());
					break;
				}
				
				// clear
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
				GL11.glLoadIdentity();
				
				// init basic rendering stuff
				this.initRendering();
				// render scene
				this.render3D();
				
				this.getUI().update();
				
				// update screen
				Display.update();

				// update fps
				this.updateFPS();
				this.lastFrame = this.getTime();
			}
		} catch ( Exception e ) {
			// if something went wrong make a clean quit
			this.isRunning = false;
			this.hasError = true;
			
			e.printStackTrace();
		}

		this.getUI().destroy();
		Display.destroy();
	}
	
	private void initWindow() throws RendererException, LWJGLException {
		// check if with & height is set
		if(this.width == 0 || this.height == 0) {
			throw new RendererException("No dimensions set before calling render !");
		}
		
		Display.setDisplayMode(new DisplayMode(this.width,this.height));
		Display.setTitle(this.title);
		Display.create();
		
		GL11.glViewport(0, 0, width, height);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Black Background
		
		GL11.glShadeModel(GL11.GL_SMOOTH); // Enables Smooth Shading
		GL11.glDepthFunc(GL11.GL_LEQUAL); // The Type Of Depth Test To Do
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST); // Really Nice Perspective Calculations
		GL11.glClearDepth(1.0f); // Depth Buffer Setup
	}
	
	private void initRendering() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(45.0f, ((float) this.width) / ((float) this.height), 0.1f, 200.0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	protected abstract void render3D() throws RendererException;
	protected abstract UserInterface getUI();
	
	protected abstract int getAppRunflag();
	
	
	/**
	 * ++++++++++++++++++++++++++++++++++++++++++++
	 * FPS calculation and stuff
	 * ++++++++++++++++++++++++++++++++++++++++++++
	 */
	
	/**
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	public long getTime() {
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	/** 
	 * Calculate how many milliseconds have passed 
	 * since last frame.
	 * 
	 * @return milliseconds passed since last frame 
	 */
	public int getDelta() {
	    long time = getTime();
	    int delta = (int) (time - this.lastFrame);
	 
	    return delta;
	}
	
	/**
	 * Calculate the FPS and set it in the title bar
	 */
	private void updateFPS() {
	    if (getTime() - this.lastFPS > 1000) {
	        this.printFPS(this.fps);
	        this.fps = 0; //reset the FPS counter
	        this.lastFPS += 1000; //add one second
	    }
	    fps++;
	}
	
	protected void initFPS() {
		// init fps variables
		this.lastFrame = this.getTime();
		this.lastFPS = this.getTime();
		this.fps = 0;
	}
	
	protected abstract void printFPS(int fps);
}
