package Components;

import Window.Window;
import core.Component;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class InputComponent implements Component {
    public Vector2d previousMouse = new Vector2d(-1, -1);
    public Vector2d currentMouse = new Vector2d(0, 0);;
    public Vector2f displayVector = new Vector2f(0, 0);
    public boolean has_focus;
    public boolean leftMouseButtonPressed;
    public boolean rightMouseButtonPressed;

    public Vector3f keyboardVector = new Vector3f(0, 0, 0);
    public Window win;

    public final int W_KEY = 87;
    public final int S_KEY = 83;
    public final int A_KEY = 65;
    public final int D_KEY = 68;

    public final int SPACE_KEY = 32;
    public final int LSHIFT_KEY = 340;

    public InputComponent(Window win) {
        this.win = win;

        glfwSetCursorPosCallback(win.getWindowHandle(), (windowHandle, xpos, ypos) -> {
            currentMouse.x = xpos;
            currentMouse.y = ypos;
        });

        glfwSetCursorEnterCallback(win.getWindowHandle(), (windowHandle, entered) -> {
            has_focus = entered;
        });

        glfwSetMouseButtonCallback(win.getWindowHandle(), (windowHandle, button, action, mode) -> {
            leftMouseButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
            rightMouseButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
        });
    }
}
