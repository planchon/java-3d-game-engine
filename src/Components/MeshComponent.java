package Components;

import Renderer.Mesh;
import Renderer.Texture;
import Renderer.Texture3D;
import core.Component;

public class MeshComponent implements Component {
    public Mesh mesh;
    public Texture texture;
    public String name;

    public void asCube() {
        float[] vertex = {
                // back
                0, 0, 0,
                1, 0, 0,
                1, 1, 0,
                0, 1, 0,

                // front
                0, 0, 1,
                1, 0, 1,
                1, 1, 1,
                0, 1, 1,

                // left
                1, 0, 0,
                1, 0, 1,
                1, 1, 1,
                1, 1, 0,

                // right
                0, 0, 0,
                0, 0, 1,
                0, 1, 1,
                0, 1, 0,

                // top
                0, 1, 0,
                1, 1, 0,
                1, 1, 1,
                0, 1, 1,

                // bottom,
                0, 0, 0,
                1, 0, 0,
                1, 0, 1,
                0, 0, 1,
        };
        int[] indices = {
                // back
                0, 1, 3,
                1, 3, 2,

                // front
                4, 7, 5,
                5, 7, 6,

                // right
                8, 9, 10,
                8, 10, 11,

                // left
                12, 13, 14,
                12, 14, 15,

                // top
                16, 17, 18,
                16, 18, 19,

                // bottom
                20, 21, 22,
                20, 22, 23
        };

        float[] texture = {
                // back
                0, 0, 0,
                1, 0, 0,
                1, 1, 0,
                0, 1, 0,

                // front
                0, 0, 0,
                1, 0, 0,
                1, 1, 0,
                0, 1, 0,

                // left
                0, 0, 0,
                1, 0, 0,
                1, 1, 0,
                0, 1, 0,

                // right
                0, 0, 0,
                1, 0, 0,
                1, 1, 0,
                0, 1, 0,

                // bottom
                0, 0, 0,
                1, 0, 0,
                1, 1, 0,
                0, 1, 0,

                // top
                0, 0, 0,
                1, 0, 0,
                1, 1, 0,
                0, 1, 0,
        };

        this.mesh = new Mesh(vertex, indices, texture);
        this.texture = (Texture) new Texture3D("/Users/paulplanchon/Dropbox/Dev copie/3D/res/images/room.png", 9);
    }

    public void asSphere() {

    }
}
