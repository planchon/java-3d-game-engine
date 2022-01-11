package Systems;

import Components.*;
import Renderer.Camera;
import Renderer.Mesh;
import Renderer.Shader;
import core.Engine;
import core.Entity;
import core.Family;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL30;
import systems.ECSSystem;
import utils.ImmutableArray;

public class RenderSystem extends ECSSystem {
    public ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> debugEntities;
    private ImmutableArray<Entity> lights;
    private ImmutableArray<Entity> cameraEntity;
    Engine engine;

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, MeshComponent.class, ShaderComponent.class).get());
        debugEntities = engine.getEntitiesFor(Family.all(PositionComponent.class, MeshComponent.class, ShaderComponent.class, DebugRendering.class).get());
        lights = engine.getEntitiesFor(Family.all(EmitLight.class).get());
        cameraEntity = engine.getEntitiesFor(Family.all(CameraComponent.class).get());

        this.engine = engine;
    }

    @Override
    public void update(double dt) {
        Camera camera = cameraEntity.get(0).getComponent(CameraComponent.class).camera;

        for (Entity e : this.entities) {
            MeshComponent testMesh = e.getComponent(MeshComponent.class);
            ShaderComponent shaderComponent = e.getComponent(ShaderComponent.class);
            PositionComponent positionComponent = e.getComponent(PositionComponent.class);
            DebugRendering debugRendering = e.getComponent(DebugRendering.class);

            shaderComponent.shader.use();
            shaderComponent.shader.setMatrix("worldMatrix", positionComponent.worldMatrix);
            shaderComponent.shader.setMatrix("projectionMatrix", camera.perspective);
            shaderComponent.shader.setMatrix("viewMatrix", camera.getViewMatrix());
            testMesh.texture.bind();
            GL30.glBindVertexArray(testMesh.mesh.VAO);
            GL30.glDrawElements(GL30.GL_TRIANGLES, testMesh.mesh.vertex_count, GL30.GL_UNSIGNED_INT, 0);
        }
    }

    @Override
    public void removedFromEngine(Engine engine) {
        System.out.println("ps remove from engine");
        entities = null;
    }
}
