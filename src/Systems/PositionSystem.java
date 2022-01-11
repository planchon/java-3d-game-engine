package Systems;

import Components.*;
import core.Engine;
import core.Entity;
import core.Family;
import org.joml.Vector3f;
import systems.ECSSystem;
import utils.ImmutableArray;

import java.util.Vector;

public class PositionSystem extends ECSSystem {
    public ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, InputComponent.class, CameraComponent.class).get());
        System.out.println(this.getClass().toString() + " added to the engine");
    }

    @Override
    public void update(double dt) {
        for (Entity e : this.entities) {
            PositionComponent positionComponent = e.getComponent(PositionComponent.class);
            InputComponent input = e.getComponent(InputComponent.class);

            float speed = 0.1f;
            float mouseSpeed = 0.001f;

            Vector3f pos = new Vector3f(input.keyboardVector.x * speed, input.keyboardVector.y * speed, input.keyboardVector.z * speed);
            Vector3f rot = new Vector3f(input.displayVector.x * mouseSpeed, input.displayVector.y * mouseSpeed, 0);

            positionComponent.updateMatrix();
        }
    }

    @Override
    public void removedFromEngine(Engine engine) {
        System.out.println("ps remove from engine");
        entities = null;
    }
}
