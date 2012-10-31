package de.futjikato.javahasen.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import de.futjikato.javahasen.App;
import de.futjikato.javahasen.Renderer;
import de.futjikato.javahasen.simulation.Simulator;

public class PauseButton extends Button {

	protected int x;
	protected int y;
	
	public PauseButton(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public Texture getTexture() {
		try {
			return TextureLoader.getTexture("PNG", new FileInputStream("resources/img/btn_pause.png"));
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
		return 50;
	}

	@Override
	public int getHeight() {
		return 50;
	}
	
	@Override
	public void onClick() {
		Simulator.getInstance().togglePause();
	}

}
