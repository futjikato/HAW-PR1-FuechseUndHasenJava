import java.util.Stack;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Renderer {
	
	protected int width;
	protected int height;
	protected Field field;
	protected Simulator simulator;

	public Renderer(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}
	
	public Simulator getSimulator() {
		return simulator;
	}

	public void setSimulator(Simulator simulator) {
		this.simulator = simulator;
	}
	
	public void init() {
		try {
			Display.setDisplayMode(new DisplayMode(this.width,this.height));
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
			int x2 = pos.getX() * 10 + 8;
			int y1 = pos.getY() * 10 + 1;
			int y2 = pos.getY() * 10 + 8;
			
		    GL11.glVertex2f(x1,y1);
		    GL11.glVertex2f(x2,y1);
		    GL11.glVertex2f(x2,y2);
		    GL11.glVertex2f(x1,y2);
		GL11.glEnd();
	}
	
	public void drawField() {
		Stack<Creature> creatures = this.simulator.getCreatures();
		while(!creatures.empty()) {
			Creature currCreature = creatures.pop();
			this.drawCreature(currCreature);
		}
	}
	
	public void render() {
		// Clear the screen and depth buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);	
		
		// draw everything on the field
		this.drawField();
		
		// update screen
		Display.update();
	}
}
