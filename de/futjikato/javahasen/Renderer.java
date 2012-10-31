package de.futjikato.javahasen;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

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
	private boolean isPaused = false;
	private boolean isRunning = false;
	private boolean hasError = false;
	
	// constants
	public static final int RENDER_2D = 2;
	public static final int RENDER_3D = 3;
	
	public void togglePause() {
		this.isPaused = !this.isPaused;
		
		// reset fps
		this.initFPS();
	}
	
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
				this.initRendering(RENDER_2D);
				// render ui or whatever 2d
				this.render2D();
				
				if(!this.isPaused) {
					// init basic rendering stuff
					this.initRendering(RENDER_3D);
					// render scene
					this.render3D();
				}
				
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
	}
	
	private void initRendering(int perspective) throws RendererException {
		GL11.glViewport(0, 0, width, height);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		
		// load and apply perspective
		switch(perspective) {
			case RENDER_2D:
				this.apply2DPerspective();
				break;
			case RENDER_3D:
				this.apply3DPerspective();
				break;
			default:
				throw new RendererException("Invalid perspective fllag given. Must be either 2D or 3D");
		}
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		
		GL11.glShadeModel(GL11.GL_SMOOTH); // Enables Smooth Shading
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Black Background
		GL11.glClearDepth(1.0f); // Depth Buffer Setup
		GL11.glEnable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
		GL11.glDepthFunc(GL11.GL_LEQUAL); // The Type Of Depth Test To Do
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST); // Really Nice Perspective Calculations
		GL11.glEnable(GL11.GL_TEXTURE_2D); // enable textures
	}

	protected void apply3DPerspective() {	
		GLU.gluPerspective(45.0f, ((float) this.width) / ((float) this.height), 0.1f, 200.0f);
	}

	protected void apply2DPerspective() {
		GL11.glOrtho(0, this.width, this.height, 0, 1, -1);
	}

	protected abstract void render3D() throws RendererException;
	protected abstract void render2D() throws RendererException;
	
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
