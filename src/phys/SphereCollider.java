package phys;

import org.joml.Vector3f;

public class SphereCollider extends Collider {
	public float rad;
	
	public SphereCollider(Vector3f pos, float rad) {
		this.pos = pos;
		this.rad = rad;
	}
	
	SphereCollider(Vector3f pos) {
		this.pos = pos;
		this.rad = 1.f;
	}
	
	public boolean inside(Vector3f p) {
		return p.distance(pos) < rad;
	}
	
	public Vector3f point(Vector3f p) {
		return p.sub(pos).normalize().mul(rad).add(pos);
	}
	
	public boolean intersect(SphereCollider s) {
		float r = this.rad + s.rad;
		
		return pos.distance(s.pos) < r;
	}
	
	public boolean intersect(Collider c) {
		if (c instanceof SphereCollider) {
			return this.intersect((SphereCollider) c);
		}
		
		return false;
	}
}
