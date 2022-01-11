package Systems;

import Components.CameraComponent;
import Components.MeshComponent;
import Components.PositionComponent;
import Components.ShaderComponent;
import Renderer.Shader;
import core.Engine;
import core.Entity;
import core.Family;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL30;
import systems.ECSSystem;
import utils.ImmutableArray;

public class RenderSystem extends ECSSystem {
    public ImmutableArray<Entity> entities;
    Engine engine;

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, MeshComponent.class, ShaderComponent.class).get());
        this.engine = engine;
    }

    @Override
    public void update(double dt) {
        Entity cameraEntity = this.engine.getEntitiesFor(Family.all(CameraComponent.class).get()).get(0);
        CameraComponent cameraComponent = cameraEntity.getComponent(CameraComponent.class);

        for (Entity e : this.entities) {
            MeshComponent testMesh = e.getComponent(MeshComponent.class);
            ShaderComponent shaderComponent = e.getComponent(ShaderComponent.class);
            PositionComponent positionComponent = e.getComponent(PositionComponent.class);

            shaderComponent.shader.use();
            shaderComponent.shader.setMatrix("worldMatrix", positionComponent.worldMatrix);
            shaderComponent.shader.setMatrix("projectionMatrix", cameraComponent.camera.perspective);
            shaderComponent.shader.setMatrix("viewMatrix", cameraComponent.camera.getViewMatrix());
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
