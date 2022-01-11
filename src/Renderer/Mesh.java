package Renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

public class Mesh {
	public int VBO, VAO, EBO, textures_VBO, NormalVBO;
	public float[] vertices;
	public float[] normal;
	public int[] indices;
	public int vertex_count;
	public float[] tex_position;

	// for debug the meshes
	public Vector3f bounding_min, bounding_max;
	
	public Mesh(float[] vertices, int[] indices, float[] tex_position) {
		this.vertex_count = indices.length;
		this.vertices = vertices;
		this.normal = normal;
		this.indices = indices;
		this.tex_position = tex_position;
		
		FloatBuffer floatBuffer = MemoryUtil.memAllocFloat(tex_position.length);
		
		this.VAO = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(this.VAO);
		
		this.VBO = GL30.glGenBuffers();
		this.EBO = GL30.glGenBuffers();
		this.textures_VBO = GL30.glGenBuffers();
		this.NormalVBO = GL30.glGenBuffers();
		
		GL30.glBindVertexArray(this.VAO);
		
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, this.VBO);
		GL30.glBufferData(GL30.GL_ARRAY_BUFFER, this.vertices, GL30.GL_STATIC_DRAW);
		GL30.glEnableVertexAttribArray(0);
		GL30.glVertexAttribPointer(0, 3, GL30.GL_FLOAT, false, 0, 0);
		
//		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, this.textures_VBO);
//		GL30.glBufferData(GL30.GL_ARRAY_BUFFER, this.tex_position, GL30.GL_STATIC_DRAW);
//		GL30.glEnableVertexAttribArray(1);
//		GL30.glVertexAttribPointer(1, 3, GL30.GL_FLOAT, false, 0, 0);

//		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, this.textures_VBO);
//		GL30.glBufferData(GL30.GL_ARRAY_BUFFER, this.tex_position, GL30.GL_STATIC_DRAW);
//		GL30.glEnableVertexAttribArray(1);
//		GL30.glVertexAttribPointer(1, 3, GL30.GL_FLOAT, false, 0, 0);

		GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, this.EBO);
		GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, indices, GL30.GL_STATIC_DRAW);
		
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);

		init_debug_variable();
	}

	public void init_debug_variable() {
		float maxX = -1, minX = 10000;
		float maxY = -1, minY = 10000;
		float maxZ = -1, minZ = 10000;

		int len = vertices.length / 3;
		for (int i = 0; i < len - 3; i++) {
			float x = vertices[3 * i];
			float y = vertices[3 * i + 1];
			float z = vertices[3 * i + 2];

			if (x > maxX) maxX = x;
			if (y > maxY) maxY = y;
			if (z > maxZ) maxZ = z;

			if (x < minX) minX = x;
			if (y < minY) minY = y;
			if (z < minZ) minZ = z;
		}

		bounding_min = new Vector3f(minX, minY, minZ);
		bounding_max = new Vector3f(maxX, maxY, maxZ);

		System.out.println("[debug] bounding_min " + bounding_min + " bounding_max" + bounding_max);
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
	
	protected void finalize() {
		GL30.glDisableVertexAttribArray(0);
		
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
		GL30.glDeleteBuffers(this.VBO);
		GL30.glDeleteBuffers(this.EBO);
		
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(this.VAO);
	}
}
