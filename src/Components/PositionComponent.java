package Components;

import core.Component;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class PositionComponent implements Component {
    public Vector3f pos;
    public Vector3f rotation;
    public Vector3f scale;

    public Matrix4f worldMatrix = new Matrix4f();

    public PositionComponent(Vector3f pos, Vector3f rot) {
        this.pos = pos;
        this.rotation = rot;
        this.scale = new Vector3f(1, 1, 1);
        this.updateMatrix();
    }

    public PositionComponent(Vector3f pos) {
        this.pos = pos;
        this.rotation = new Vector3f();
        this.scale = new Vector3f(1, 1, 1);
        this.updateMatrix();
    }

    // TODO: regarder si c'est ok niveau ECS
    public void updateMatrix() {
        this.worldMatrix = new Matrix4f();
        this.worldMatrix = this.worldMatrix.scale(this.scale);
        this.worldMatrix = this.worldMatrix.translate(this.pos);
        this.worldMatrix = this.worldMatrix.rotateX(this.rotation.x);
        this.worldMatrix = this.worldMatrix.rotateY(this.rotation.y);
        this.worldMatrix = this.worldMatrix.rotateZ(this.rotation.z);
    }
}
