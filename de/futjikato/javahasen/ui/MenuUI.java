package de.futjikato.javahasen.ui;

import java.io.IOException;
import java.net.URL;

import org.lwjgl.LWJGLException;

import de.futjikato.javahasen.App;
import de.futjikato.javahasen.RendererException;
import de.futjikato.javahasen.menu.MenuRenderer;
import de.matthiasmann.twl.*;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;

public class MenuUI extends Widget implements UserInterface, Runnable  {
	
	private GUI gui;
	private ThemeManager theme;
	
	private ToggleButton btnPlay;
	
	public MenuUI() throws LWJGLException {
		this.initUi();
		
		LWJGLRenderer renderer = new LWJGLRenderer();
		this.gui = new GUI(this, renderer);
		
		try {
			URL res = App.class.getClassLoader().getResource("resources/themes/default/JavaHasen.xml");
			this.theme = ThemeManager.createThemeManager(res, renderer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.gui.applyTheme(this.theme);
	}
	
	private void initUi() {
		this.btnPlay = new ToggleButton();
		this.btnPlay.setText("Play !");
		this.btnPlay.addCallback(new Runnable() {
			@Override
			public void run() {
				App.getInstance().setNext(App.RUNFLAG_SIMULATION);
				try {
					MenuRenderer.getInstance().stop();
				} catch (RendererException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        this.add(this.btnPlay);
	}
	
	@Override
	protected void layout() {
		// play btn
		this.btnPlay.setPosition(20, 20);
		this.btnPlay.adjustSize();
	}

	public void update() {
		this.gui.update();
	}
	
	public void destroy() {
		this.gui.destroy();
		this.theme.destroy();
	}

	@Override
	public void run() {
		System.out.println("Click");
		App.getInstance().setNext(App.RUNFLAG_SIMULATION);
		try {
			MenuRenderer.getInstance().stop();
		} catch (RendererException e) {
			e.printStackTrace();
		}
	}
}
