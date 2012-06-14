import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;

public class InputHandler {
	
	protected int mouse_pos_x = 0;
	protected boolean rotate_enabled = false;
	
	public boolean doClose() {
		return Display.isCloseRequested();
	}
	
	public float handleCameraX(float cx) {
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			cx -= 0.25f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			cx += 0.25f;
		}
		return cx;
	}
	
	public float handleCameraY(float cy) {
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			cy -= 0.25f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			cy += 0.25f;
		}
		return cy;
	}
	
	public float handleRotation(float cr) {
		if(rotate_enabled) {
			if(!Mouse.isButtonDown(0)) {
				this.rotate_enabled = false;
			} else {
				int cmpx = Mouse.getX();
				int mp_diff = cmpx - this.mouse_pos_x;
				
				cr += mp_diff / 100;
			}
		} else {
			if(Mouse.isButtonDown(0)) {
				this.mouse_pos_x = Mouse.getX();
				this.rotate_enabled = true;
			}
		}
		return cr;
	}
}
