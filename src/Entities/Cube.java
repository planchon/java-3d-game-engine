package Entities;

import org.joml.Vector3f;

import Renderer.Shader;
import World.CubeObject;
import World.WorldObject;
import phys.AABB;

public class Cube extends GameEntity {
	public CubeObject co;
	public Vector3f pos;
	
	public Cube(Vector3f pos) {
		super(new AABB(pos, new Vector3f(1, 1, 1)));
		this.pos = pos;
		this.collided = false;
		this.co = new CubeObject(pos, new Vector3f(1, 1, 1));
	}
	
	public void tick(int time) {
		this.collider.pos = this.pos;
		this.co.pos = this.pos;
	}
	
	public void render(Shader shader) {
		if (this.collided) {
			shader.setBool("lines", true);
			shader.setColor("aColor", new Vector3f(1, 0, 0));
		}
		
		this.co.render(shader);
		
		shader.setBool("lines", false);
	}
}
