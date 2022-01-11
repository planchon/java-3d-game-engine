package Components;

import Renderer.Shader;
import core.Component;

public class ShaderComponent implements Component {
    public Shader shader;

    public ShaderComponent(Shader shader) {
        this.shader = shader;
    }
}
