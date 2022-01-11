package Systems;

import Components.Collider;
import Components.PositionComponent;
import core.Engine;
import core.Entity;
import core.Family;
import systems.ECSSystem;
import utils.ImmutableArray;

public class CollisionSystem extends ECSSystem {
    public ImmutableArray<Entity> OBBEntities;
    public ImmutableArray<Entity> entities;
    public boolean is_init = false;

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(Collider.class, PositionComponent.class).get());
    }

    @Override
    public void update(double dt) {
        if (is_init == false) {
            for (Entity e : this.entities) {
                Collider collider = e.getComponent(Collider.class);
                collider.update_collider(e);
            }
        }

        is_init = true;

        for (Entity a : this.entities) {
            boolean are_collinding = false;
            Collider a_collider = a.getComponent(Collider.class);
            for (Entity b : this.entities) {
                if (a != b) {
                    Collider b_collider = b.getComponent(Collider.class);
                    if (a_collider.type == "AABB" && b_collider.type == "AABB") are_collinding = a_collider.aabb.intersect(b_collider.aabb);
                    if (a_collider.type == "OBB" && b_collider.type == "OBB") are_collinding = a_collider.obb.intersect(b_collider.obb);

                    a_collider.is_colliding = are_collinding;
                    b_collider.is_colliding = are_collinding;
                }
            }
        }
    }

    @Override
    public void removedFromEngine(Engine engine) {
        entities = null;
    }
}
