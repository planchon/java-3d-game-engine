package Renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
	float fov, zNear, zFar;
	int width, height;
	Vector3f position = new Vector3f();
	Vector3f rotation = new Vector3f();
	public Matrix4f perspective = new Matrix4f();
	public final Matrix4f view = new Matrix4f();
	
	public Camera(float fov, int width, int height, float zNear, float zFar) {
		this.fov    = fov;
		this.zFar   = zFar;
		this.zNear  = zNear;
		this.width  = width;
		this.height = height;
		
		this.perspective = this.perspective.setPerspective(this.fov, (float) this.width / (float) this.height, this.zNear, this.zFar);
	}
	
	public Matrix4f getViewMatrix() {
		view.identity();
		view.rotate((float) Math.toRadians(this.rotation.x), new Vector3f(1, 0, 0))
			.rotate((float) Math.toRadians(this.rotation.y), new Vector3f(0, 1, 0));
			
		view.translate(-this.position.x, -this.position.y, -this.position.z);
		return view;
	}
	
	public void setPosition(float x, float y, float z) {
		this.position.set(x, y, z);
	}
	
	public void movePosition(Vector3f vec, float speed) {
		this.movePosition(vec.x * speed, vec.y * speed, vec.z * speed);
	}
	
	public void movePosition(float dx, float dy, float dz) {
		if (dz != 0) {
			this.position.x += (float) Math.sin(Math.toRadians(this.rotation.y)) * -1.0f * dz;
			this.position.z += (float) Math.cos(Math.toRadians(this.rotation.y)) * dz;
		}
		if (dx != 0) {
			this.position.x += (float) Math.sin(Math.toRadians(this.rotation.y - 90)) * -1.0f * dx;
			this.position.z += (float) Math.cos(Math.toRadians(this.rotation.y - 90)) * dx;
		}
		position.y += dy;
	}
	
	public void setRotation(float x, float y, float z) {
		this.rotation.set(x, y, z);
	}
	
	public void moveRotation(float x, float y, float z) {
		this.rotation.x += x;
		this.rotation.y += y;
		this.rotation.z += z;
	}
	
	public String toString() {
		return "[camera] " + this.position + ", " + this.rotation;
	}
}
