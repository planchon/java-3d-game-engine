package Renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

public class Mesh {
	public int VBO, VAO, EBO, textures_VBO, TBO;
	public float[] vertices;
	public int[] indices;
	public int vertex_count;
	public float[] tex_position;
	
	public Mesh(float[] vertices, int[] indices, float[] tex_position) {
		this.vertex_count = indices.length;
		this.vertices = vertices;
		this.indices = indices;
		this.tex_position = tex_position;
		
		FloatBuffer floatBuffer = MemoryUtil.memAllocFloat(tex_position.length);
		
		this.VAO = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(this.VAO);
		
		this.VBO = GL30.glGenBuffers();
		this.EBO = GL30.glGenBuffers();
		this.textures_VBO = GL30.glGenBuffers();
		
		GL30.glBindVertexArray(this.VAO);
		
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, this.VBO);
		GL30.glBufferData(GL30.GL_ARRAY_BUFFER, this.vertices, GL30.GL_STATIC_DRAW);
		GL30.glEnableVertexAttribArray(0);
		GL30.glVertexAttribPointer(0, 3, GL30.GL_FLOAT, false, 0, 0);
		
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, this.textures_VBO);
		GL30.glBufferData(GL30.GL_ARRAY_BUFFER, this.tex_position, GL30.GL_STATIC_DRAW);
		GL30.glEnableVertexAttribArray(1);
		GL30.glVertexAttribPointer(1, 3, GL30.GL_FLOAT, false, 0, 0);
		
		GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, this.EBO);
		GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, indices, GL30.GL_STATIC_DRAW);
		
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
	}	
	
	public Mesh(float vertices[], int[] indices, int[] tile_indices) {
		this.vertex_count = indices.length;
		this.vertices = vertices;
		this.indices = indices;

		this.VAO = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(this.VAO);
		
		this.VBO = GL30.glGenBuffers(); // vertice buffer object
		this.TBO = GL30.glGenBuffers(); // tile buffer object
		this.EBO = GL30.glGenBuffers(); // element buffer object
		
		// vertice
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, this.VBO);
		GL30.glBufferData(GL30.GL_ARRAY_BUFFER, this.vertices, GL30.GL_STATIC_DRAW);
		GL30.glEnableVertexAttribArray(0);
		GL30.glVertexAttribPointer(0, 3, GL30.GL_FLOAT, false, 0, 0);
		
		// tile
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, this.TBO);
		GL30.glBufferData(GL30.GL_ARRAY_BUFFER, tile_indices, GL30.GL_STATIC_DRAW);
		GL30.glEnableVertexAttribArray(1);
		GL30.glVertexAttribPointer(1, 1, GL30.GL_INT, false, 0, 0);
		
		GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, this.EBO);
		GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, indices, GL30.GL_STATIC_DRAW);
		
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
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
