package phys;

import org.joml.Vector3f;

import Renderer.Mesh;
import Renderer.Shader;

public class AABB extends Collider {
	public Vector3f size;
	
	public AABB(Vector3f pos, Vector3f size) {
		this.pos = pos;
		this.size = size.mul(0.5f);
	}
	
	public AABB fromMinMax(Vector3f min, Vector3f max) {
		return new AABB(new Vector3f(min).add(max).mul(0.5f), new Vector3f(max).sub(min).mul(0.5f));
	}
	
	public Vector3f getMin() {
		Vector3f p1 = new Vector3f(this.pos).add(this.size);
		Vector3f p2 = new Vector3f(this.pos).sub(this.size);
		
		return new Vector3f(
					Math.min(p1.x, p2.x),
					Math.min(p1.y, p2.y),
					Math.min(p1.z, p2.z)
				);				
	}
	
	public Vector3f getMax() {
		Vector3f p1 = new Vector3f(this.pos).add(this.size);
		Vector3f p2 = new Vector3f(this.pos).sub(this.size);
		
		return new Vector3f(
					Math.max(p1.x, p2.x),
					Math.max(p1.y, p2.y),
					Math.max(p1.z, p2.z)
				);
	}
	
	public boolean inside(Vector3f p) {
		Vector3f min = this.getMin();
		Vector3f max = this.getMax();
		
		if (p.x < min.x || p.y < min.y || p.z < min.z) {
			return false;
		}
		
		if (p.x > max.x || p.y > max.y || p.z > max.z) {
			return false;
		}
		
		return true;
	}
	
	public Vector3f closest(Vector3f p) {
		Vector3f res = new Vector3f();
		Vector3f min = this.getMin();
		Vector3f max = this.getMax();
		
		res.x = (res.x < min.x) ? min.x : res.x;
		res.y = (res.y < min.y) ? min.y : res.y;
		res.z = (res.z < min.z) ? min.z : res.z;
		
		res.x = (res.x > max.x) ? max.x : res.x;
		res.y = (res.y > max.y) ? max.y : res.y;
		res.z = (res.z > max.z) ? max.z : res.z;
		
		return res;
	}
	
	public boolean intersect(SphereCollider s) {
		Vector3f closest = this.closest(s.pos);
		float distance = s.pos.distance(closest);
		return distance < s.rad;
	}
	
	public boolean intersect(Collider c) {
		if (c instanceof AABB) {
			return this.intersect((AABB) c);
		}
		
		if (c instanceof SphereCollider) {
			return this.intersect((SphereCollider) c);
		}
		
		return false;
	}
	
	public boolean intersect(AABB a) {
		Vector3f aMin = this.getMin();
		Vector3f aMax = this.getMax();
		Vector3f bMin = a.getMin();
		Vector3f bMax = a.getMax();
		
		return (aMin.x <= bMax.x && aMax.x >= bMin.x) && 
			   (aMin.y <= bMax.y && aMax.y >= bMin.y) && 
			   (aMin.z <= bMax.z && aMax.z >= bMin.z);
				
	}
}
