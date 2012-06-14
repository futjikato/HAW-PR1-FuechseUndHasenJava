import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;

public class InputHandler {
	public InputHandler() {
		
	}
	
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
}
