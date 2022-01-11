package Game;

import org.joml.*;
import org.lwjgl.BufferUtils;

import Window.Window;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

import java.nio.DoubleBuffer;

public class MouseInputHandler {
	private Vector2d previousMouse;
	private Vector2d currentMouse;
	public Vector2f displayVector;
	private boolean has_focus;
	private boolean leftMouseButtonPressed;
	private boolean rightMouseButtonPressed;
	
	public MouseInputHandler() {
		previousMouse = new Vector2d(-1, -1);
		currentMouse = new Vector2d(0, 0);
		displayVector = new Vector2f();
	}
	
	public void init(Window window) {
		glfwSetCursorPosCallback(window.getWindowHandle(), (windowHandle, xpos, ypos) -> {
			currentMouse.x = xpos;
			currentMouse.y = ypos;
        });
		
		glfwSetCursorEnterCallback(window.getWindowHandle(), (windowHandle, entered) -> {
			has_focus = entered;
        });

		glfwSetMouseButtonCallback(window.getWindowHandle(), (windowHandle, button, action, mode) -> {
			leftMouseButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
			rightMouseButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
        });
		
		System.out.println("[input] mouse input armed");
	}
	
	public void input(Window window) {
		DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer y = BufferUtils.createDoubleBuffer(1);

        glfwGetCursorPos(window.window_id, x, y);
        x.rewind();
        y.rewind();

		displayVector.x = 0;
		displayVector.y = 0;
		
		double newX = x.get();
		double newY = y.get();
		
		if (newX > 0 && newY > 0 && has_focus) {
            double deltax = newX - window.width / 2;
            double deltay = newY - window.height / 2;
            boolean rotateX = deltax != 0;
            boolean rotateY = deltay != 0;
            if (rotateX) {
            	displayVector.y = (float) deltax;
            }
            if (rotateY) {
            	displayVector.x = (float) deltay;
            }
        }
		previousMouse.x = currentMouse.x;
		previousMouse.y = currentMouse.y;
		
		glfwSetCursorPos(window.window_id, window.width / 2, window.height / 2);
	}
	
}
