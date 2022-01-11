package Game;

import org.joml.*;

import Window.Window;

public class KeyboardInputHandler {
	public Vector3f keyboardVector;
	
	public final int W_KEY = 87;
	public final int S_KEY = 83;
	public final int A_KEY = 65;
	public final int D_KEY = 68;
	
	public final int SPACE_KEY = 32;
	public final int LSHIFT_KEY = 340;
	
	public KeyboardInputHandler() {
		keyboardVector = new Vector3f();
	}
	
	public void input(Window win) {
		keyboardVector.set(0,0,0);
		if (win.isKeyPressed(W_KEY)) {
			keyboardVector.z = -1;
		}
		if (win.isKeyPressed(S_KEY)) {
			keyboardVector.z = 1;
		}
		if (win.isKeyPressed(A_KEY)) {
			keyboardVector.x = -1;
		}
		if (win.isKeyPressed(D_KEY)) {
			keyboardVector.x = 1;
		}
		if (win.isKeyPressed(SPACE_KEY)) {
			keyboardVector.y = 1;
		}
		if (win.isKeyPressed(LSHIFT_KEY)) {
			keyboardVector.y = -1;
		}
	}
}
