package World;

import org.joml.*;
import org.lwjgl.opengl.GL30;

import Renderer.Mesh;
import Renderer.Shader;
import Renderer.Texture;
import Renderer.Texture3D;

public class CubeObject extends WorldObject {
	public Vector3f pos, size;
	Texture texture_test;
	Mesh mesh;
	
	public CubeObject(Vector3f pos, Vector3f size) {
		super(pos);
		this.pos = pos;
		this.size = size;
		
		this.texture_test = (Texture) new Texture3D("/Users/paulplanchon/Dropbox/Dev copie/3D/res/images/room.png", 9);
		
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
	}
	
	public Matrix4f worldMatrix() {
		Matrix4f world = new Matrix4f();
		world.translate(pos);
		return world;
	}
	
	public void render(Shader shader) {
		this.texture_test.bind();
		shader.setMatrix("worldMatrix", this.worldMatrix()); 
		GL30.glBindVertexArray(this.mesh.VAO);
		GL30.glDrawElements(GL30.GL_TRIANGLES, this.mesh.vertex_count, GL30.GL_UNSIGNED_INT, 0);
	}
}
