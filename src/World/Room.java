package World;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import org.joml.Matrix4f;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL30;

import Renderer.Mesh;
import Renderer.Shader;
import Renderer.Texture;
import Renderer.Texture3D;

public class Room extends WorldObject {
	public Mesh mesh;
	private int max_indices = 0;
	
	private Vector3d size;
	private List<Float> position = new ArrayList<Float>();
	private List<Integer> indices = new ArrayList<Integer>();
	private List<Float> texture = new ArrayList<Float>();
	
	Texture texture_test;
	
	public Room(Vector3d size) {
		super(new Vector3f(0, 0, 0));
		this.size = size;
		this.generate_buffers();
		this.texture_test = (Texture) new Texture3D("/Users/paulplanchon/Dropbox/Dev copie/3D/res/images/room.png", 9);
	} 
	
	private void generate_buffers() {
		this.add_face(2, 0);
		this.add_face(1, 0);
		this.add_face(0, 0);
		
		this.add_face(0, (float) this.size.x);
		this.add_face(1, (float) this.size.y);
		this.add_face(2, (float) this.size.z);
		
		float[] positions = this.to_arrayf(this.position);
		int[] indices = this.to_arrayi(this.indices);
		float[] textures = this.to_arrayf(this.texture);
			
		this.mesh = new Mesh(positions, indices, textures);
	}
	
	private void add_face(int axis, float axis_translate) {
		float x = 0, y = 0;
		switch (axis) {
			case 0:
				x = (float) this.size.z;
				y = (float) this.size.y;
				break;
			case 1:
				x = (float) this.size.x;
				y = (float) this.size.z;
				break;
			case 2:
				x = (float) this.size.x;
				y = (float) this.size.y;
				break;
		}
	
		
		this.add_line(axis, axis_translate, x, 0, 1, 6, 3, 7);
		this.add_line(axis, axis_translate, x, 1, y - 1, 2, 0, 1);
		this.add_line(axis, axis_translate, x, y - 1, y, 5, 4, 8);
	}
	
	private float[] to_arrayf(List<Float> tmp) {
		float[] test = new float[tmp.size()];
		for (int i = 0; i < tmp.size(); i++) {
			test[i] = tmp.get(i);
		}
		
		return test;
	}
	
	private int[] to_arrayi(List<Integer> tmp) {
		int[] test = new int[tmp.size()];
		for (int i = 0; i < tmp.size(); i++) {
			test[i] = tmp.get(i);
		}
		
		return test;
	}
	
	// axis -> l'axe invariant,
	// axis x = 0
	//      y = 1
	//      z = 2
	// axis_translate -> si on veut translate l'axis invariant
	// val -> valeur d'ecartement
	// hauteur -> 0 bas, 1 milieu, 2 haut
	// a, b, c -> texture de A | B B B | C
	private void add_line(int axis, float axis_translate, float val_x, float val_y_bas, float val_y_haut, float a, float b, float c) {
		// vertex stuff
		float[] tmp = {0, 1, 1, val_x - 1, val_x - 1, val_x};
		for (int i = 0; i < 12; i++) {
			float tmp_val_y = i < 6 ? val_y_bas : val_y_haut;
			switch (axis) {
				case 0:
					this.position.add(axis_translate);
					this.position.add(tmp_val_y);
					this.position.add(tmp[i % 6]);
					break;
				case 1:
					this.position.add(tmp[i % 6]);
					this.position.add(axis_translate);
					this.position.add(tmp_val_y);
					break;
				case 2:
					this.position.add(tmp[i % 6]);
					this.position.add(tmp_val_y);
					this.position.add(axis_translate);
					break;
			}
		}
		
		// indices stuff
		for (int i = 0; i < 3; i++) {
			int start = max_indices + 6 + i * 2;
			this.indices.add(start);
			this.indices.add(start - 6);
			this.indices.add(start - 6 + 1);
			
			this.indices.add(start + 1);
			this.indices.add(start);
			this.indices.add(start - 6 + 1);
		}
		
		this.max_indices += 12;
		
		float delta_val_y = val_y_haut - val_y_bas;
		
		// textures stuff
		float[] textures = {
			0, 0, a,
			1, 0, a,
			
			        0, 0, b, 
			val_x - 2, 0, b,
			
			0, 0, c,
			1, 0, c,
			
			0, delta_val_y, a,
			1, delta_val_y, a,
			
			        0, delta_val_y, b,
			val_x - 2, delta_val_y, b,
			
			0, delta_val_y, c,
			1, delta_val_y, c
		};
		
		for (int i = 0; i < textures.length; i++) {
			this.texture.add(textures[i]);
		}
	}

	public void render(Shader shader) {
		shader.setInt("tiles", 0);
		this.texture_test.bind();
		shader.setMatrix("worldMatrix", new Matrix4f()); 
		GL30.glBindVertexArray(this.mesh.VAO);
		GL30.glDrawElements(GL30.GL_TRIANGLES, this.mesh.vertex_count, GL30.GL_UNSIGNED_INT, 0);
	}
}
