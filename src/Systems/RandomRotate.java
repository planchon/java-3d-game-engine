package Systems;

import Components.MeshComponent;
import Components.PositionComponent;
import Components.RandomRotation;
import Components.ShaderComponent;
import core.Engine;
import core.Entity;
import core.Family;
import systems.ECSSystem;
import utils.ImmutableArray;

public class RandomRotate extends ECSSystem {
    public ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, RandomRotation.class).get());
    }

    @Override
    public void update(double dt) {
        for (Entity e : this.entities) {
            PositionComponent pos = e.getComponent(PositionComponent.class);
            RandomRotation random = e.getComponent(RandomRotation.class);
            float speed = 0.1f;
            pos.rotation.x += random.rot.x * speed;
            pos.rotation.y += random.rot.y * speed;
            pos.rotation.z += random.rot.z * speed;
            pos.updateMatrix();
        }
    }

    @Override
    public void removedFromEngine(Engine engine) {
        entities = null;
    }
}
