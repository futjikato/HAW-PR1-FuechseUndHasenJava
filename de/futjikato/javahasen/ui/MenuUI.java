package de.futjikato.javahasen.ui;

import java.io.IOException;
import java.net.URL;

import org.lwjgl.LWJGLException;

import com.sun.org.apache.xerces.internal.impl.dv.ValidatedInfo;

import sun.awt.HorizBagLayout;

import de.futjikato.javahasen.App;
import de.futjikato.javahasen.RendererException;
import de.futjikato.javahasen.menu.MenuRenderer;
import de.matthiasmann.twl.*;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;

public class MenuUI extends Widget implements UserInterface, Runnable  {
	
	private GUI gui;
	private ThemeManager theme;
	
	public ToggleButton btnPlay;
	public Scrollbar densityScroll;
	public Label densityValueLabel;
	public Label densityLabel;
	
	private static MenuUI instance;
	
	private MenuUI() throws LWJGLException {
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
	
	public static MenuUI getInstance() {
		if(MenuUI.instance == null) {
			try {
				MenuUI.instance = new MenuUI();
			} catch (LWJGLException e) {
				e.printStackTrace();
			}
		}
		
		return MenuUI.instance;
	}
	
	private void initUi() {
		
		// Play Button
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
        
        // Dencity Scrollbar
        this.densityScroll = new Scrollbar(Scrollbar.Orientation.HORIZONTAL);
        this.densityScroll.setMinMaxValue(0, 100);
        this.densityScroll.setStepSize(1);
        this.densityScroll.addCallback(new Runnable() {
			@Override
			public void run() {
				// update ui
				MenuUI ui = MenuUI.getInstance();
				int val = ui.densityScroll.getValue();
				ui.densityValueLabel.setText(Integer.toString(val));
				
				// set density in app
				App.getInstance().setCreatureDensity(val);
			}
		});
        this.add(this.densityScroll);
        
        // value label
        this.densityValueLabel = new Label();
        this.densityValueLabel.setTheme("valuelabel");
        this.densityValueLabel.setText("0");
        this.add(this.densityValueLabel);
        
        // info label
        this.densityLabel = new Label();
        this.densityLabel.setTheme("label");
        this.densityLabel.setText("Creature density:");
        this.add(this.densityLabel);
	}
	
	@Override
	protected void layout() {
		// play btn
		this.btnPlay.setPosition(20, 20);
		this.btnPlay.adjustSize();
		
		// density scroller
		this.densityScroll.setPosition(20, 100);
		this.densityScroll.adjustSize();
		
		// density label
		this.densityLabel.setPosition(20, 80);
		this.densityLabel.adjustSize();
		
		// density value label
		this.densityValueLabel.setPosition(220, 100);
		this.densityValueLabel.adjustSize();
	}

	public void update() {
		this.gui.update();
	}
	
	public void cleanup() {
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
