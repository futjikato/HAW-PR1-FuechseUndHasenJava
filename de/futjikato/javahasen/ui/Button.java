package de.futjikato.javahasen.ui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public abstract class Button {
	
	protected boolean mouseHovering = false;
	
	public abstract Texture getTexture();
	
	public abstract int getX();
	
	public abstract int getY();
	
	public abstract int getWidth();
	
	public abstract int getHeight();
	
	/**
	 * Implement logic for each button
	 */
	public void onClick() {}
	
	public void checkEvents() {
		if(this.isClicked()) {
			this.onClick();
			return;
		}
		
		if(this.isHover()) {
			this.mouseHovering = true;
		} else {
			this.mouseHovering = false;
		}
	}
	
	protected boolean isHover() {
		int x = Mouse.getX();
		int y = Mouse.getY();
		
		return (
			x > this.getX() &&
			x < (this.getX() + this.getWidth()) &&
			y > (Display.getHeight() - (this.getY() + this.getHeight())) &&
			y < (Display.getHeight() - this.getY())
		);
	}
	
	protected boolean isClicked() {
		return (this.isHover() && Mouse.isButtonDown(0));
	}

	public void draw() throws Exception {
		Texture texture = this.getTexture();
		
		// fetch loading error
		if(texture == null) {
			throw new Exception("Unable to load Texture");
		}
		
		GL11.glPushMatrix();
		
		texture.bind();
		
		GL11.glTranslatef(this.getX(), this.getY(), 0);
		GL11.glColor3f(1, 1, 1);
		GL11.glBegin(GL11.GL_QUADS);
	
		      GL11.glVertex2f(0, 0);
		      GL11.glTexCoord2f(1, 0);
		      
		      GL11.glVertex2f(this.getWidth(), 0);
		      GL11.glTexCoord2f(1, 1);
		      
		      GL11.glVertex2f(this.getWidth(), this.getHeight());
		      GL11.glTexCoord2f(0, 1);
		      
		      GL11.glVertex2f(0, this.getHeight());
		      GL11.glTexCoord2f(0, 0);
		
		GL11.glEnd();
		
		GL11.glPopMatrix();
	}
}
