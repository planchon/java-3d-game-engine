package Systems;

import Components.*;
import Renderer.Camera;
import core.Engine;
import core.Entity;
import core.Family;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL30;
import phys.OBB;
import systems.ECSSystem;
import utils.ImmutableArray;

public class DebugRender extends ECSSystem {
    public ImmutableArray<Entity> debugEntities;
    Engine engine;
    Camera camera;

    public void addedToEngine(Engine engine) {
        debugEntities = engine.getEntitiesFor(Family.all(DebugRendering.class).get());
        this.engine = engine;
    }

    public void update(double dt) {
        if (camera == null) {
            camera = engine.getEntitiesFor(Family.all(CameraComponent.class).get()).get(0).getComponent(CameraComponent.class).camera;
        }

        for (Entity e: this.debugEntities) {
            Collider col = e.getComponent(Collider.class);
            Matrix4f mat = new Matrix4f();
            boolean is_colliding = col.is_colliding;
            switch (col.type) {
                case "AABB":
                    break;
                case "OBB":
                    // transform 3x3 to 4x4 mat
                    Matrix4f tmp = new Matrix4f();
                    tmp.m00(col.obb.rot.m00);
                    tmp.m10(col.obb.rot.m10);
                    tmp.m20(col.obb.rot.m20);
                    tmp.m01(col.obb.rot.m01);
                    tmp.m11(col.obb.rot.m11);
                    tmp.m21(col.obb.rot.m21);
                    tmp.m02(col.obb.rot.m02);
                    tmp.m12(col.obb.rot.m12);
                    tmp.m22(col.obb.rot.m22);

                    mat = new Matrix4f();
                    Vector3f scaledref = new Vector3f(col.obb.size).mul(col.obb.rot);
                    mat = mat.mul(new Matrix4f().translate( new Vector3f(col.obb.pos).sub(scaledref) ));
                    mat = mat.mul(tmp);
//                    System.out.println(mat);
                    break;
            }

            DebugRendering debugRendering = e.getComponent(DebugRendering.class);

            debugRendering.debugShader.use();
            debugRendering.debugShader.setMatrix("worldMatrix", mat);
            debugRendering.debugShader.setMatrix("projectionMatrix", camera.perspective);
            debugRendering.debugShader.setMatrix("viewMatrix", camera.getViewMatrix());

            if (is_colliding) {
                debugRendering.debugShader.setColor("color", new Vector3f(0, 0, 1));
            } else {
                debugRendering.debugShader.setColor("color", new Vector3f(0, 1, 0));
            }

            GL30.glBindVertexArray(debugRendering.debugMesh.VAO);
            GL30.glDrawElements(GL30.GL_LINE_LOOP, debugRendering.debugMesh.vertex_count, GL30.GL_UNSIGNED_INT, 0);
        }
    }
}
