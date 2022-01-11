package World;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL30;

import Renderer.Mesh;
import Renderer.Shader;
import Renderer.Texture;
import Renderer.Texture3D;

public class SphereObject extends WorldObject {
	public Vector3f pos;
	public float radius;
	
	public Texture texture_test;
	private Mesh mesh;
	
	public SphereObject(Vector3f pos, float radius) {
		super(pos);
		this.pos = pos;
		this.radius = radius;
		
		this.texture_test = (Texture) new Texture3D("/Users/paulplanchon/Dropbox/Dev copie/3D/res/images/room.png", 9);
		
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
	
	private Matrix4f worldMatrix() {
		Matrix4f mat = new Matrix4f();
		mat.translate(pos);
		return mat;
	}
	
	public void render(Shader shader) {
		this.texture_test.bind();
		shader.setMatrix("worldMatrix", this.worldMatrix()); 
		GL30.glBindVertexArray(this.mesh.VAO);
		GL30.glDrawElements(GL30.GL_TRIANGLES, this.mesh.vertex_count, GL30.GL_UNSIGNED_INT, 0);
	}
}
