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
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, RandomMovement.class).get());
    }

    @Override
    public void update(double dt) {
        for (Entity e : this.entities) {
            PositionComponent pos = e.getComponent(PositionComponent.class);
            RandomMovement random = e.getComponent(RandomMovement.class);

            double dtt = dt / 4;

            if (random.pos.x != 0) {
                pos.pos.x = (float) (random.pos.x * Math.sin(dtt));
            }
            if (random.pos.y != 0) {
                pos.pos.y = (float) (random.pos.y * Math.sin(dtt));
            }
            if (random.pos.z != 0) {
                pos.pos.z = (float) (random.pos.z * Math.sin(dtt));
            }

            if (random.rot.x != 0) {
                pos.rotation.x = (float) (random.rot.x * Math.sin(dtt));
            }
            if (random.rot.y != 0) {
                pos.rotation.y = (float) (random.rot.y * Math.sin(dtt));
            }
            if (random.rot.z != 0) {
                pos.rotation.z = (float) (random.rot.z * Math.sin(dtt));
            }

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
