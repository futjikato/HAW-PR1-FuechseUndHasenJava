package de.futjikato.javahasen.simulation;

import org.lwjgl.opengl.GL11;

public class Tree extends StaticObject {

	public Tree(Position pos) throws Exception {
		pos.setContent(this);
		this.setPosition(pos);
	}
	
	@Override
	public void render() {
		this.drawTrunk();
		this.drawTreetop();
	}
	
	public void drawTrunk() {
		
		GL11.glColor3f(0.75f, 0.4f, 0);
		
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glVertex3f(0.25f, 3, -0.25f); // Top Right Of The Quad (Top)
		GL11.glVertex3f(-0.25f, 3, -0.25f); // Top Left Of The Quad (Top)
		GL11.glVertex3f(-0.25f, 3, 0.25f); // Bottom Left Of The Quad (Top)
		GL11.glVertex3f(0.25f, 3, 0.25f); // Bottom Right Of The Quad (Top)

		GL11.glVertex3f(0.25f, -1, 0.25f); // Top Right Of The Quad (Bottom)
		GL11.glVertex3f(-0.25f, -1, 0.25f); // Top Left Of The Quad (Bottom)
		GL11.glVertex3f(-0.25f, -1, -0.25f); // Bottom Left Of The Quad (Bottom)
		GL11.glVertex3f(0.25f, -1, -0.25f); // Bottom Right Of The Quad (Bottom)

		GL11.glVertex3f(0.25f, 3, 0.25f); // Top Right Of The Quad (Front)
		GL11.glVertex3f(-0.25f, 3, 0.25f); // Top Left Of The Quad (Front)
		GL11.glVertex3f(-0.25f, -1, 0.25f); // Bottom Left Of The Quad (Front)
		GL11.glVertex3f(0.25f, -1, 0.25f); // Bottom Right Of The Quad (Front)

		GL11.glVertex3f(0.25f, -1, -0.25f); // Bottom Left Of The Quad (Back)
		GL11.glVertex3f(-0.25f, -1, -0.25f); // Bottom Right Of The Quad (Back)
		GL11.glVertex3f(-0.25f, 3, -0.25f); // Top Right Of The Quad (Back)
		GL11.glVertex3f(0.25f, 3, -0.25f); // Top Left Of The Quad (Back)

		GL11.glVertex3f(-0.25f, 3, 0.25f); // Top Right Of The Quad (Left)
		GL11.glVertex3f(-0.25f, 3, -0.25f); // Top Left Of The Quad (Left)
		GL11.glVertex3f(-0.25f, -1, -0.25f); // Bottom Left Of The Quad (Left)
		GL11.glVertex3f(-0.25f, -1, 0.25f); // Bottom Right Of The Quad (Left)

		GL11.glVertex3f(0.25f, 3, -0.25f); // Top Right Of The Quad (Right)
		GL11.glVertex3f(0.25f, 3, 0.25f); // Top Left Of The Quad (Right)
		GL11.glVertex3f(0.25f, -1, 0.25f); // Bottom Left Of The Quad (Right)
		GL11.glVertex3f(0.25f, -1, -0.25f); // Bottom Right Of The Quad (Right)
		
		GL11.glEnd();
		GL11.glFlush();
	}
	
	public void drawTreetop() {
		
		GL11.glColor3f(0, 0.8f, 0);
		
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glVertex3f(1f, 4, -1f); // Top Right Of The Quad (Top)
		GL11.glVertex3f(-1f, 4, -1f); // Top Left Of The Quad (Top)
		GL11.glVertex3f(-1f, 4, 1f); // Bottom Left Of The Quad (Top)
		GL11.glVertex3f(1f, 4, 1f); // Bottom Right Of The Quad (Top)

		GL11.glVertex3f(1f, 3, 1f); // Top Right Of The Quad (Bottom)
		GL11.glVertex3f(-1f, 3, 1f); // Top Left Of The Quad (Bottom)
		GL11.glVertex3f(-1f, 3, -1f); // Bottom Left Of The Quad (Bottom)
		GL11.glVertex3f(1f, 3, -1f); // Bottom Right Of The Quad (Bottom)

		GL11.glVertex3f(1f, 4, 1f); // Top Right Of The Quad (Front)
		GL11.glVertex3f(-1f, 4, 1f); // Top Left Of The Quad (Front)
		GL11.glVertex3f(-1f, 3, 1f); // Bottom Left Of The Quad (Front)
		GL11.glVertex3f(1f, 3, 1f); // Bottom Right Of The Quad (Front)

		GL11.glVertex3f(1f, 3, -1f); // Bottom Left Of The Quad (Back)
		GL11.glVertex3f(-1f, 3, -1f); // Bottom Right Of The Quad (Back)
		GL11.glVertex3f(-1f, 4, -1f); // Top Right Of The Quad (Back)
		GL11.glVertex3f(1f, 4, -1f); // Top Left Of The Quad (Back)

		GL11.glVertex3f(-1f, 4, 1f); // Top Right Of The Quad (Left)
		GL11.glVertex3f(-1f, 4, -1f); // Top Left Of The Quad (Left)
		GL11.glVertex3f(-1f, 3, -1f); // Bottom Left Of The Quad (Left)
		GL11.glVertex3f(-1f, 3, 1f); // Bottom Right Of The Quad (Left)

		GL11.glVertex3f(1f, 4, -1f); // Top Right Of The Quad (Right)
		GL11.glVertex3f(1f, 4, 1f); // Top Left Of The Quad (Right)
		GL11.glVertex3f(1f, 3, 1f); // Bottom Left Of The Quad (Right)
		GL11.glVertex3f(1f, 3, -1f); // Bottom Right Of The Quad (Right)
		
		GL11.glEnd();
		GL11.glFlush();
	}

}
