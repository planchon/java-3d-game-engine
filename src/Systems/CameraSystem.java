package Systems;

import Components.CameraComponent;
import Components.InputComponent;
import Components.PositionComponent;
import core.Engine;
import core.Entity;
import core.Family;
import systems.ECSSystem;
import utils.ImmutableArray;

public class CameraSystem extends ECSSystem {
    public ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, InputComponent.class, CameraComponent.class).get());
    }

    @Override
    public void update(double dt) {
        for (Entity e : this.entities) {
            CameraComponent cam = e.getComponent(CameraComponent.class);
            PositionComponent pos = e.getComponent(PositionComponent.class);
            InputComponent input = e.getComponent(InputComponent.class);

            float KEYBOARD_SPEED = 0.3f;
            float MOUSE_SPEED = 0.3f;

            cam.camera.movePosition(input.keyboardVector, KEYBOARD_SPEED);
            cam.camera.moveRotation(input.displayVector.x * MOUSE_SPEED, input.displayVector.y * MOUSE_SPEED, 0);
        }
    }

    @Override
    public void removedFromEngine(Engine engine) {
        System.out.println("ps remove from engine");
        entities = null;
    }
}
