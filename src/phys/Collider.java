package phys;

import org.joml.Vector3f;

public class Collider {
	public Vector3f pos;
	
	public boolean intersect(Collider c) {
		if (this instanceof SphereCollider) {
			return ((SphereCollider) this).intersect(c);
		}
		
		if (this instanceof AABB) {
			return ((AABB) this).intersect(c);
		}
		
		return false;
	}
}
