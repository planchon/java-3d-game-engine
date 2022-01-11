package Systems;

import Components.InputComponent;
import Components.MeshComponent;
import Components.PositionComponent;
import Components.ShaderComponent;
import core.Engine;
import core.Entity;
import core.Family;
import org.lwjgl.BufferUtils;
import systems.ECSSystem;
import utils.ImmutableArray;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class InputSystem extends ECSSystem {
    public ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(InputComponent.class).get());
    }

    @Override
    public void update(double dt) {
        // update pour le keyboard
        InputComponent ic = entities.get(0).getComponent(InputComponent.class);

        ic.keyboardVector.set(0,0,0);
        if (ic.win.isKeyPressed(ic.W_KEY)) {
            ic.keyboardVector.z = -1;
        }
        if (ic.win.isKeyPressed(ic.S_KEY)) {
            ic.keyboardVector.z = 1;
        }
        if (ic.win.isKeyPressed(ic.A_KEY)) {
            ic.keyboardVector.x = -1;
        }
        if (ic.win.isKeyPressed(ic.D_KEY)) {
            ic.keyboardVector.x = 1;
        }
        if (ic.win.isKeyPressed(ic.SPACE_KEY)) {
            ic.keyboardVector.y = 1;
        }
        if (ic.win.isKeyPressed(ic.LSHIFT_KEY)) {
            ic.keyboardVector.y = -1;
        }

        // update pour la mouse
        DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer y = BufferUtils.createDoubleBuffer(1);

        glfwGetCursorPos(ic.win.window_id, x, y);
        x.rewind();
        y.rewind();

        ic.displayVector.x = 0;
        ic.displayVector.y = 0;

        double newX = x.get();
        double newY = y.get();

        if (newX > 0 && newY > 0 && ic.has_focus) {
            double deltax = newX - ic.win.width / 2;
            double deltay = newY - ic.win.height / 2;
            boolean rotateX = deltax != 0;
            boolean rotateY = deltay != 0;
            if (rotateX) {
                ic.displayVector.y = (float) deltax;
            }
            if (rotateY) {
                ic.displayVector.x = (float) deltay;
            }
        }
        ic.previousMouse.x = ic.currentMouse.x;
        ic.previousMouse.y = ic.currentMouse.y;

        glfwSetCursorPos(ic.win.window_id, ic.win.width / 2, ic.win.height / 2);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        System.out.println("ps remove from engine");
        entities = null;
    }
}
