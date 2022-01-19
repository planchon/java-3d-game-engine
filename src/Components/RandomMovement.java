package Components;

import core.Component;
import org.joml.Vector3f;

public class RandomMovement implements Component {
    public Vector3f rot;
    public Vector3f pos;

    public RandomMovement(){
        this.rot = new Vector3f(0, 0, 0);
    }

    public RandomMovement(Vector3f pos) {
        this.pos = pos;
        this.rot = new Vector3f(0);
    }

    public RandomMovement(Vector3f pos, Vector3f rot) {
        this.pos = pos;
        this.rot = rot;
    }
}
