import org.lwjgl.opengl.*;

public class InputHandler {
	public InputHandler() {
		
	}
	
	public boolean doClose() {
		return Display.isCloseRequested();
	}
}
