package de.futjikato.javahasen.ui;

import java.io.IOException;
import java.net.URL;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import de.futjikato.javahasen.App;
import de.futjikato.javahasen.RendererException;
import de.futjikato.javahasen.menu.MenuRenderer;
import de.futjikato.javahasen.simulation.Simulator;
import de.matthiasmann.twl.*;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;

public class SimulationUI extends Widget implements UserInterface  {
	
	private GUI gui;
	private ThemeManager theme;
	
	private static SimulationUI instance;
	
	public ToggleButton btnPause;
	
	private  SimulationUI() throws LWJGLException {
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
	
	public static SimulationUI getInstance() throws LWJGLException {
		if(SimulationUI.instance == null) {
			SimulationUI.instance = new SimulationUI();
		}
		
		return SimulationUI.instance;
	}
	
	private void initUi() {
		this.btnPause = new ToggleButton();
		this.btnPause.setText("Pause");
		this.btnPause.addCallback(new Runnable() {
			@Override
			public void run() {
				Simulator.getInstance().togglePause();
				
				ToggleButton currBtn = null;
				try {
					currBtn = SimulationUI.getInstance().btnPause;
				} catch (LWJGLException e) {
					e.printStackTrace();
				}
				
				if(currBtn != null && currBtn.getText() == "Pause") {
					currBtn.setText("Resume");
				} else {
					currBtn.setText("Pause");
				}
			}
		});
        this.add(this.btnPause);
	}
	
	@Override
	protected void layout() {
		// play btn
		this.btnPause.setPosition(Display.getWidth() - 220, 20);
		this.btnPause.adjustSize();
	}

	public void update() {
		this.gui.update();
	}
	
	public void destroy() {
		
	}
}
