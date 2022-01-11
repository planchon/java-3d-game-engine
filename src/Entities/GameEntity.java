package Entities;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import Renderer.Shader;
import phys.Collider;

public class GameEntity {
	public int id;

	// position
	public Vector3f pos = new Vector3f();
	public Vector3f rotation = new Vector3f();
	public Vector3f scale    = new Vector3f(1, 1, 1);
	
	public Matrix4f worldMatrix = new Matrix4f();

	public Collider collider;
	public boolean collided;
	
	public GameEntity(Collider col) {
		this.collider = col;
	}
	
	public void render(Shader shader) {}
	
	public void tick(int time) {}
	
	public void updateWorldMatrix() {
		this.worldMatrix = new Matrix4f();
		this.worldMatrix = this.worldMatrix.scale(this.scale);
		this.worldMatrix = this.worldMatrix.rotateX(this.rotation.x);
		this.worldMatrix = this.worldMatrix.rotateY(this.rotation.y);
		this.worldMatrix = this.worldMatrix.rotateZ(this.rotation.z);
		this.worldMatrix = this.worldMatrix.translate(this.pos);
	}
	
	public void setPosition(float x, float y, float z) {
		this.pos.set(x, y, z);
		this.updateWorldMatrix();
	}
	
	public void setRotation(float x, float y, float z) {
		this.rotation = new Vector3f(x, y, z);
		this.updateWorldMatrix();
	}
	
	public void setScale(float x, float y, float z) {
		this.scale.set(x, y, z);
		this.updateWorldMatrix();
	}
}
