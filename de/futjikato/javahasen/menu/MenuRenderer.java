package de.futjikato.javahasen.menu;

import org.lwjgl.opengl.*;

import de.futjikato.javahasen.App;
import de.futjikato.javahasen.Renderer;
import de.futjikato.javahasen.ui.MenuUI;
import de.futjikato.javahasen.ui.UserInterface;

public class MenuRenderer extends Renderer {
	
	private static MenuRenderer instance;
	
	private MenuRenderer()  {
		
		MenuRenderer.instance = this;
		
		Display.setResizable(true);
		
		this.width = 500;
		this.height = 500;
	}
	
	public static MenuRenderer getInstance() {
		if(MenuRenderer.instance == null) {
			MenuRenderer.instance = new MenuRenderer();
		}
		
		return MenuRenderer.instance;
	}

	@Override
	protected void render3D() {
		// menu does not have any 3d elements yet
		return;
	}

	@Override
	protected int getAppRunflag() {
		return App.RUNFLAG_STOP;
	}
	
	@Override
	protected void printFPS(int fps) {
		System.out.println("FPS in menu : " + fps);
	}

	@Override
	protected UserInterface getUI() {
		return MenuUI.getInstance();
	}
}
