package Components;

import core.Component;
import org.joml.Vector3f;

import java.util.Vector;

public class RandomRotation implements Component {
    public Vector3f rot;
    public RandomRotation(){
        this.rot = new Vector3f(1, 0, 0);
    }

    public RandomRotation(Vector3f vec) {
        this.rot = vec;
    }
}
