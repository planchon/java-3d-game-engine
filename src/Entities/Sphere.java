package Entities;

import org.joml.Vector3f;

import Renderer.Shader;
import World.SphereObject;
import phys.SphereCollider;

public class Sphere extends GameEntity {
	public float radius;
	
	SphereObject so;
	
	public Sphere(Vector3f pos, float radius) {
		super(new SphereCollider(pos, radius));
		
		this.pos = pos;
		this.radius = radius;
		this.collided = false;
		this.so = new SphereObject(pos, radius);
	}
	
	public void tick(int time) {
		this.so.pos = this.pos;
		this.collider.pos = this.pos;
	}
	
	public void render(Shader shader) {
		shader.setBool("lines", true);
		if (this.collided) {
			shader.setColor("aColor", new Vector3f(1, 0, 0));
		} else {
			shader.setColor("aColor", new Vector3f(0, 1, 0));
		}
		
		this.so.render(shader);
		shader.setBool("lines", false);
	}
}
