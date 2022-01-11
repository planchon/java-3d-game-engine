package Components;

import core.Component;
import org.joml.Vector3f;



public class EmitLight implements Component {
    public enum light_type {
        POINT_LIGHT,
        SPOT_LIGHT,
        DIRECTIONAL_LIGHT,
        AMBIENT_LIGHT
    };

    public light_type light_type;
    public Vector3f color;
    public float power;
    public float falloff;

    public EmitLight(Vector3f color, float power, light_type type, float falloff) {
        this.color = color;
        this.power = power;
        this.falloff = falloff;
        this.light_type = type;
    }

    public EmitLight() {
        this.color = new Vector3f(1, 1, 1);
        this.power = 3.f;
        this.falloff = 30.f;
        this.light_type = light_type.POINT_LIGHT;
    }
}
