package Components;

import Renderer.Mesh;
import Renderer.Texture;
import Renderer.Texture3D;
import core.Component;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.assimp.Assimp.*;

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

    public void fromFile(String filepath, String textureDir) {
        int ASSIMP_FLAGS = aiProcess_JoinIdenticalVertices | aiProcess_Triangulate | aiProcess_FixInfacingNormals;
        try {
            AIScene scene = aiImportFile(filepath, ASSIMP_FLAGS);
            if (scene == null) {
                throw new Exception("error loading the model");
            }

            System.out.println("[mesh loader] stats : number_mat " + scene.mNumMaterials() + ", meshes " + scene.mNumMeshes());

            PointerBuffer mesh_test = scene.mMeshes();
            AIMesh aiMesh = AIMesh.create(mesh_test.get(0));
            this.mesh = this.processMesh(aiMesh);
            this.texture = (Texture) new Texture3D("/Users/paulplanchon/Dropbox/Dev copie/3D/res/images/room.png", 9);
        } catch (Exception e) {
            System.out.println("[mesh loader] error : " + e.toString());
        }
    }

    private Mesh processMesh(AIMesh mesh) {
        List<Float> vertices = new ArrayList<Float>();
        List<Float> normals = new ArrayList<Float>();
        List<Float> textures = new ArrayList<Float>();
        List<Integer> indices = new ArrayList<Integer>();

        AIVector3D.Buffer aiVertices = mesh.mVertices();
        while (aiVertices.remaining() > 0) {
            AIVector3D aiVertex = aiVertices.get();
            vertices.add(aiVertex.x());
            vertices.add(aiVertex.y());
            vertices.add(aiVertex.z());
        }

        AIVector3D.Buffer aiNormals = mesh.mNormals();
        while (aiVertices.remaining() > 0) {
            AIVector3D aiNormal = aiNormals.get();
            normals.add(aiNormal.x());
            normals.add(aiNormal.y());
            normals.add(aiNormal.z());
        }

        AIVector3D.Buffer textBuffer = mesh.mTextureCoords(0);
        if (textBuffer != null) {
            while (textBuffer.remaining() > 0) {
                AIVector3D aiTexture = textBuffer.get();
                textures.add(aiTexture.x());
                textures.add(1 - aiTexture.y());
            }
        }

        int numFaces = mesh.mNumFaces();
        AIFace.Buffer aiFaces = mesh.mFaces();
        if (numFaces > 0) {
            for (int i = 0; i < numFaces; i++) {
                AIFace tmpFace = aiFaces.get(i);
                IntBuffer buff = tmpFace.mIndices();
                while (buff.remaining() > 0) {
                    indices.add(buff.get());
                }
            }
        }

        return new Mesh(to_arrayf(vertices), to_arrayi(indices), to_arrayf(textures));
    }

    public void asSphere(float radius) {
        this.texture = (Texture) new Texture3D("/Users/paulplanchon/Dropbox/Dev copie/3D/res/images/room.png", 9);

        List<Float> vertex = new ArrayList<Float>();
        List<Integer> indices = new ArrayList<Integer>();
        List<Float> textures = new ArrayList<Float>();

        int sectorCount = 20;
        int stackCount = 20;

        float x, y, z, xy;
        float s, t;

        float sectorStep = 2 * (float) Math.PI / sectorCount;
        float stackStep = (float) Math.PI / stackCount;
        float sectorAngle, stackAngle;

        for (int i = 0; i <= stackCount; i++) {
            stackAngle = (float) Math.PI / 2 - i * stackStep;
            xy = radius * (float) Math.cos(stackAngle);
            z = radius * (float) Math.sin(stackAngle);

            for (int j = 0; j < sectorCount; j++) {
                sectorAngle = j * sectorStep;
                x = xy * (float) Math.cos(sectorAngle);
                y = xy * (float) Math.sin(sectorAngle);

                vertex.add(x);
                vertex.add(y);
                vertex.add(z);

                s = (float) j / sectorCount;
                t = (float) i / stackCount;
                textures.add(s);
                textures.add(t);
            }
        }

        int k1, k2;
        for (int i = 0; i < stackCount; i++) {
            k1 = i * (sectorCount + 1);
            k2 = k1 + sectorCount + 1;

            for (int j = 0; j < sectorCount; j++) {
                k1++;
                k2++;

                if (i != 0) {
                    indices.add(k1);
                    indices.add(k2);
                    indices.add(k1 + 1);
                }

                if (i != (stackCount - 1)) {
                    indices.add(k1 + 1);
                    indices.add(k2);
                    indices.add(k2 + 1);
                }
            }
        }

        this.mesh = new Mesh(this.to_arrayf(vertex), this.to_arrayi(indices), this.to_arrayf(textures));
    }

    public float[] to_arrayf(List<Float> tmp) {
        float[] test = new float[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            test[i] = tmp.get(i);
        }

        return test;
    }

    public int[] to_arrayi(List<Integer> tmp) {
        int[] test = new int[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            test[i] = tmp.get(i);
        }

        return test;
    }
}
