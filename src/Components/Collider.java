package Components;

import core.Component;
import core.Entity;
import org.joml.Matrix3f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import phys.AABB;
import phys.OBB;

public class Collider implements Component {
    public String type;
    public AABB aabb;
    public OBB obb;
    public boolean is_colliding = false;
    private OBB obb1;

    public Collider(String type) {
        this.type = type;
        this.is_colliding = false;

        switch (type) {
            case "AABB":
                this.aabb = new AABB(new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
                break;
            case "OBB":
                this.obb = new OBB(new Vector3f(), new Vector3f(), new Matrix3f());
                break;
        }
    }

    public void update_collider(Entity e) {
        PositionComponent pos = e.getComponent(PositionComponent.class);

        Vector3f scale = new Vector3f(pos.scale).mul(0.5f);
        Vector3f position = new Vector3f(scale);

        switch (this.type) {
            case "AABB":
                this.aabb.pos = position.add(pos.pos);
                this.aabb.size = scale;
                break;
            case "OBB":
                Matrix3f rot = new Matrix3f();
                rot.rotateX(pos.rotation.x);
                rot.rotateY(pos.rotation.y);
                rot.rotateZ(pos.rotation.z);

                Vector4f tmp = new Vector4f(position.x, position.y, position.z, 1);
                tmp = tmp.mul(pos.worldMatrix);
                Vector3f tmp2 = new Vector3f(tmp.x, tmp.y, tmp.z);

                this.obb.pos = tmp2;
                this.obb.size = scale;
                this.obb.rot = rot;
                break;
        }
    }
}
