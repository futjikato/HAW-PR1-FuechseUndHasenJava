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
	
	public ToggleButton btnPlay;
	
	// Tiger scroller
	public Scrollbar tigerDensityScroll;
	public Label tigerDensityValueLabel;
	public Label tigerDensityLabel;
	
	// Rabbit scroller
	public Scrollbar rabbitDensityScroll;
	public Label rabbitDensityValueLabel;
	public Label rabbitDensityLabel;
	
	// fieldsize scroller
	public Scrollbar fieldsizeScroll;
	public Label fieldsizeValueLabel;
	public Label fieldsizeLabel;
	
	// treedensity scroller
	public Scrollbar treeDensityScroll;
	public Label treeDensityValueLabel;
	public Label treeDensityLabel;
	
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
        
        // Tiger Scrollbar
        this.tigerDensityScroll = new Scrollbar(Scrollbar.Orientation.HORIZONTAL);
        this.tigerDensityScroll.setMinMaxValue(0, 500);
        this.tigerDensityScroll.setStepSize(5);
        this.tigerDensityScroll.addCallback(new Runnable() {
			@Override
			public void run() {
				// update ui
				MenuUI ui = MenuUI.getInstance();
				int val = ui.tigerDensityScroll.getValue();
				ui.tigerDensityValueLabel.setText(Integer.toString(val));
				
				// set density in app
				App.getInstance().setInitalTigers(val);
			}
		});
        this.add(this.tigerDensityScroll);
        
        // Tiger value label
        this.tigerDensityValueLabel = new Label();
        this.tigerDensityValueLabel.setTheme("valuelabel");
        this.tigerDensityValueLabel.setText("0");
        this.add(this.tigerDensityValueLabel);
        
        // Tiger info label
        this.tigerDensityLabel = new Label();
        this.tigerDensityLabel.setTheme("label");
        this.tigerDensityLabel.setText("Tiger density:");
        this.add(this.tigerDensityLabel);
        
    	// Rabbit Scrollbar
        this.rabbitDensityScroll = new Scrollbar(Scrollbar.Orientation.HORIZONTAL);
        this.rabbitDensityScroll.setMinMaxValue(0, 500);
        this.rabbitDensityScroll.setStepSize(5);
        this.rabbitDensityScroll.addCallback(new Runnable() {
			@Override
			public void run() {
				// update ui
				MenuUI ui = MenuUI.getInstance();
				int val = ui.rabbitDensityScroll.getValue();
				ui.rabbitDensityValueLabel.setText(Integer.toString(val));
				
				// set density in app
				App.getInstance().setInitalRabbits(val);
			}
		});
        this.add(this.rabbitDensityScroll);
        
        // Rabbit value label
        this.rabbitDensityValueLabel = new Label();
        this.rabbitDensityValueLabel.setTheme("valuelabel");
        this.rabbitDensityValueLabel.setText("0");
        this.add(this.rabbitDensityValueLabel);
        
        // Rabbit info label
        this.rabbitDensityLabel = new Label();
        this.rabbitDensityLabel.setTheme("label");
        this.rabbitDensityLabel.setText("Rabbit density:");
        this.add(this.rabbitDensityLabel);
        
    	// fieldsize scroller
        this.fieldsizeScroll = new Scrollbar(Scrollbar.Orientation.HORIZONTAL);
        this.fieldsizeScroll.setMinMaxValue(0, 150);
        this.fieldsizeScroll.setStepSize(10);
        this.fieldsizeScroll.addCallback(new Runnable() {
			@Override
			public void run() {
				// update ui
				MenuUI ui = MenuUI.getInstance();
				int val = ui.fieldsizeScroll.getValue();
				ui.fieldsizeValueLabel.setText(Integer.toString(val) + "*" + Integer.toString(val));
				
				// set density in app
				App.getInstance().setFieldsize(val);
			}
		});
        this.add(this.fieldsizeScroll);
        
        // fieldsize label
        this.fieldsizeValueLabel = new Label();
        this.fieldsizeValueLabel.setTheme("valuelabel");
        this.fieldsizeValueLabel.setText("0");
        this.add(this.fieldsizeValueLabel);
        
        // fieldsize label
        this.fieldsizeLabel = new Label();
        this.fieldsizeLabel.setTheme("label");
        this.fieldsizeLabel.setText("Fieldsize:");
        this.add(this.fieldsizeLabel);  
        
        // tree scroller
        this.treeDensityScroll = new Scrollbar(Scrollbar.Orientation.HORIZONTAL);
        this.treeDensityScroll.setMinMaxValue(0, 50);
        this.treeDensityScroll.setStepSize(1);
        this.treeDensityScroll.addCallback(new Runnable() {
			@Override
			public void run() {
				// update ui
				MenuUI ui = MenuUI.getInstance();
				int val = ui.treeDensityScroll.getValue();
				ui.treeDensityValueLabel.setText(Integer.toString(val));
				
				// set density in app
				App.getInstance().setInitalTrees(val);
			}
		});
        this.add(this.treeDensityScroll);
        
        // tree label
        this.treeDensityValueLabel = new Label();
        this.treeDensityValueLabel.setTheme("valuelabel");
        this.treeDensityValueLabel.setText("0");
        this.add(this.treeDensityValueLabel);
        
        // tree label
        this.treeDensityLabel = new Label();
        this.treeDensityLabel.setTheme("label");
        this.treeDensityLabel.setText("Trees:");
        this.add(this.treeDensityLabel);  
	}
	
	@Override
	protected void layout() {
		// play btn
		this.btnPlay.setPosition(20, 20);
		this.btnPlay.adjustSize();
		
		// density scroller
		this.tigerDensityScroll.setPosition(20, 100);
		this.tigerDensityScroll.adjustSize();
		
		// density label
		this.tigerDensityLabel.setPosition(20, 80);
		this.tigerDensityLabel.adjustSize();
		
		// density value label
		this.tigerDensityValueLabel.setPosition(220, 100);
		this.tigerDensityValueLabel.adjustSize();
		
		// density scroller
		this.rabbitDensityScroll.setPosition(20, 180);
		this.rabbitDensityScroll.adjustSize();
		
		// density label
		this.rabbitDensityLabel.setPosition(20, 160);
		this.rabbitDensityLabel.adjustSize();
		
		// density value label
		this.rabbitDensityValueLabel.setPosition(220, 180);
		this.rabbitDensityValueLabel.adjustSize();
		
		// fieldsize scroller
		this.fieldsizeScroll.setPosition(260, 100);
		this.fieldsizeScroll.adjustSize();
		
		// fieldsize label
		this.fieldsizeLabel.setPosition(260, 80);
		this.fieldsizeLabel.adjustSize();
		
		// fieldsize value label
		this.fieldsizeValueLabel.setPosition(460, 100);
		this.fieldsizeValueLabel.adjustSize();
		
		// tree density scroller
		this.treeDensityScroll.setPosition(260, 180);
		this.treeDensityScroll.adjustSize();
				
		// tree density label
		this.treeDensityLabel.setPosition(260, 160);
		this.treeDensityLabel.adjustSize();
		
		// tree density value label
		this.treeDensityValueLabel.setPosition(460, 180);
		this.treeDensityValueLabel.adjustSize();
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
