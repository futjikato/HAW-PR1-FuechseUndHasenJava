package de.futjikato.javahasen.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import de.futjikato.javahasen.App;
import de.futjikato.javahasen.menu.MenuRenderer;

public class QuitButton extends Button {

	protected int x;
	protected int y;
	
	public QuitButton(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void onClick() {
		// stop menu
		MenuRenderer.stop();
		
		// say app to go to the simulation after closing menu
		App.getInstance().setNext(App.RUNFLAG_STOP);
	}

	@Override
	public Texture getTexture() {
		try {
			return TextureLoader.getTexture("PNG", new FileInputStream("resources/img/btn_quit.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public int getWidth() {
		return 200;
	}

	@Override
	public int getHeight() {
		return 200;
	}

}
