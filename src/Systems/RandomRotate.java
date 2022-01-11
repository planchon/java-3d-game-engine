package Systems;

import Components.*;
import core.Engine;
import core.Entity;
import core.Family;
import phys.OBB;
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

            double dtt = dt / 2;

            pos.pos.x = (float) (random.rot.x * Math.sin(dtt));
            pos.pos.y = (float) (random.rot.y * Math.sin(dtt));
            pos.pos.z = (float) (random.rot.z * Math.sin(dtt));

            pos.updateMatrix();

            Collider collider = e.getComponent(Collider.class);
            if (collider != null) collider.update_collider(e);
        }
    }

    @Override
    public void removedFromEngine(Engine engine) {
        entities = null;
    }
}
