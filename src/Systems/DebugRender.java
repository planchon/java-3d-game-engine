package Systems;

import Components.*;
import Renderer.Camera;
import core.Engine;
import core.Entity;
import core.Family;
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
            PositionComponent pos = e.getComponent(PositionComponent.class);
            Matrix4f mat = new Matrix4f();
            boolean is_colliding = col.is_colliding;
            switch (col.type) {
                case "AABB":
                    mat = pos.worldMatrix;
                    break;
                case "OBB":
                    mat = new Matrix4f();
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
