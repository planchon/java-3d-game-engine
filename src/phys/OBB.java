package phys;

import org.joml.*;

public class OBB {
	public Vector3f pos, size;
	public Matrix3f rot;
	
	OBB(Vector3f pos, Vector3f size, Matrix3f rot) {
		this.pos = pos;
		this.size = size;
		this.rot = rot;
	}
	
	OBB(Vector3f pos, Vector3f size) {
		this.pos = pos;
		this.size = size;
		this.rot = new Matrix3f();
	}
	
	public boolean inside(Vector3f p) {
		Vector3f dir = p.sub(pos);
		
		for (int i = 0; i < 3; i++) {
			Vector3f axis = new Vector3f(rot.get(i, 0), rot.get(i, 1), rot.get(i, 2));
			float distance = axis.dot(dir);
			
			if (distance > size.get(i)) {
				return false;
			}
			
			if (distance < -size.get(i)) {
				return false;
			}
		}
		
		return true;
	}
	
	public Vector3f closest(Vector3f p) {
		Vector3f dir = p.sub(pos);
		Vector3f res = pos;
		
		for (int i = 0; i < 3; i++) {
			Vector3f axis = new Vector3f(rot.get(i, 0), rot.get(i, 1), rot.get(i, 2));
			float distance = axis.dot(dir);
			
			if (distance > size.get(i)) {
				distance = size.get(i);
			}
			
			if (distance < -size.get(i)) {
				distance = -size.get(i);
			}
			
			res = res.add(axis.mul(distance));
		}
		
		return res;
	}
	
	public boolean intersect(SphereCollider s) {
		Vector3f closest = this.closest(s.pos);
		float distance = s.pos.distance(closest);
		return distance < s.rad;
	}
}
