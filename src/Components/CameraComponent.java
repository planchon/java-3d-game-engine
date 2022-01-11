package Components;

import Renderer.Camera;
import core.Component;

public class CameraComponent implements Component {
    public Camera camera;

    public CameraComponent(float fov, int width, int height, float zNear, float zFar) {
        this.camera = new Camera(fov, width, height, zNear, zFar);
    }
}
