package de.futjikato.javahasen.menu;

import org.lwjgl.*;
import org.lwjgl.opengl.*;

import de.futjikato.javahasen.App;
import de.futjikato.javahasen.ui.Button;
import de.futjikato.javahasen.ui.PlayButton;
import de.futjikato.javahasen.ui.QuitButton;

public class MenuRenderer {
	
	protected int width;
	protected int height;
	protected long lastFrame;
	protected long lastFPS;
	protected int fps = 0;
	protected static boolean keepRunning = false;
	protected float cx = 0;
	protected float cy = 0;
	
	protected Button[] buttons = new Button[] {
		new PlayButton(15, 50),
		new QuitButton(285, 50)
	};
	
	public MenuRenderer() {
		Display.setResizable(true);
		
		this.width = 500;
		this.height = 500;
		
		this.init();
	}
	
	public void init() {
		try {
			Display.setDisplayMode(new DisplayMode(this.width,this.height));
			Display.setTitle("JavaHasen - Menu");
			Display.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, this.width, this.height);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, this.width, this.height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Black Background
 
	}
	
	protected void drawUi() {
		try {
			for(Button btn : this.buttons) {
				btn.checkEvents();
				btn.draw();
			}
		} catch (Exception e) {
			e.printStackTrace();
			MenuRenderer.keepRunning = false;
		}
	}
	
	public void render() {
		// Clear the screen and depth buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glLoadIdentity();
		
		this.drawUi();
		
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
		this.lastFrame = this.getTime();
		this.lastFPS = this.getTime();
		
		try {
			MenuRenderer.keepRunning = true;
			
			// render loop
			while(MenuRenderer.keepRunning) {
				
				if(Display.isCloseRequested()) {
					MenuRenderer.keepRunning = false;
					App.getInstance().setNext(App.RUNFLAG_STOP);
					break;
				}
				
				this.render();
			}
		} catch ( Exception e ) {
			// if something went wrong make a clean quit
			MenuRenderer.keepRunning = false;
			System.out.println(e);
		}
		
		Display.destroy();
	}
	
	public static void stop() {
		MenuRenderer.keepRunning = false;
	}
}
