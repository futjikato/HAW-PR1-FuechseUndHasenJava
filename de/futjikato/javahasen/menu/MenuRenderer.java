package de.futjikato.javahasen.menu;

import org.lwjgl.opengl.*;

import de.futjikato.javahasen.App;
import de.futjikato.javahasen.Renderer;
import de.futjikato.javahasen.RendererException;
import de.futjikato.javahasen.ui.Button;
import de.futjikato.javahasen.ui.PlayButton;
import de.futjikato.javahasen.ui.QuitButton;

public class MenuRenderer extends Renderer {
	protected Button[] buttons = new Button[] {
		new PlayButton(15, 50),
		new QuitButton(285, 50)
	};
	
	private static MenuRenderer instance;
	
	private MenuRenderer() {
		
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
	
	protected void drawUi() throws RendererException {
		for(Button btn : this.buttons) {
			btn.checkEvents();
			btn.draw();
		}
	}

	@Override
	protected void render3D() {
		// menu does not have any 3d elements yet
		return;
	}

	@Override
	protected void render2D() throws RendererException {
		this.drawUi();
	}

	@Override
	protected int getAppRunflag() {
		return App.RUNFLAG_STOP;
	}
	
	@Override
	protected void printFPS(int fps) {
		System.out.println("FPS in menu : " + fps);
	}
}
