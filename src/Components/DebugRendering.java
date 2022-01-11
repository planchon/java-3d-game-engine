package Components;

import Renderer.Mesh;
import Renderer.Shader;
import core.Component;
import org.joml.Vector3f;

public class DebugRendering implements Component {
    public Mesh debugMesh;
    public Shader debugShader;

    public DebugRendering(Shader debugShader) {
        MeshComponent mesh = new MeshComponent();
        mesh.asCube();
        this.debugMesh = mesh.mesh;
        this.debugShader = debugShader;
    }
}
