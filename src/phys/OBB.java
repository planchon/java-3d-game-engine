package phys;

import org.joml.*;

import java.beans.PropertyEditorSupport;

class Interval {
	float min = 0;
	float max = 0;

	public Interval getInterval(OBB obb, Vector3f axis) {
		Vector3f[] vertex = new Vector3f[8];
		Vector3f center = new Vector3f(obb.pos);
		Vector3f e = new Vector3f(obb.size);

		float[] o = new float[9];
		o = obb.rot.get(o);

		Vector3f[] A = {
				new Vector3f(o[0], o[1], o[2]),
				new Vector3f(o[3], o[4], o[5]),
				new Vector3f(o[6], o[7], o[8]),
		};

		Vector3f a0e0 = (new Vector3f(A[0])).mul(e.x);
		Vector3f a1e1 = (new Vector3f(A[1])).mul(e.y);
		Vector3f a2e2 = (new Vector3f(A[2])).mul(e.z);

		vertex[0] = new Vector3f(center).add(a0e0).add(a1e1).add(a2e2);
		vertex[1] = new Vector3f(center).sub(a0e0).add(a1e1).add(a2e2);
		vertex[2] = new Vector3f(center).add(a0e0).sub(a1e1).add(a2e2);
		vertex[3] = new Vector3f(center).add(a0e0).add(a1e1).sub(a2e2);
		vertex[4] = new Vector3f(center).sub(a0e0).sub(a1e1).sub(a2e2);
		vertex[5] = new Vector3f(center).add(a0e0).sub(a1e1).sub(a2e2);
		vertex[6] = new Vector3f(center).sub(a0e0).add(a1e1).sub(a2e2);
		vertex[7] = new Vector3f(center).sub(a0e0).sub(a1e1).add(a2e2);

		Interval res = new Interval();

		res.min = new Vector3f(axis).dot(vertex[0]);
		res.max = res.min;

		for (int i = 1; i < 8; ++i) {
			float proj = new Vector3f(axis).dot(vertex[i]);
			res.min = (proj < res.min) ? proj : res.min;
			res.max = (proj > res.max) ? proj : res.max;
		}

		return res;
	}
}

public class OBB {
	public Vector3f pos, size;
	public Matrix3f rot;
	
	public OBB(Vector3f pos, Vector3f size, Matrix3f rot) {
		this.pos = pos;
		this.size = size;
		this.rot = rot;
	}
	
	public OBB(Vector3f pos, Vector3f size) {
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

	public boolean overlapOnAxis(OBB obb1, OBB obb2, Vector3f axis) {
		Interval a = new Interval().getInterval(obb1, axis);
		Interval b = new Interval().getInterval(obb2, axis);
		return ((b.min <= a.max) && (a.min <= b.max));
	}

	public boolean intersect(OBB other) {
		if (other == null) return false;
		float[] o1 = new float[9];
		o1 = this.rot.get(o1);
		float[] o2 = new float[9];
		o2 = other.rot.get(o2);

		Vector3f[] test = new Vector3f[15];

		test[0] = new Vector3f(o1[0], o1[1], o1[2]);
		test[1] = new Vector3f(o1[3], o1[4], o1[5]);
		test[2] = new Vector3f(o1[6], o1[7], o1[8]);

		test[3] = new Vector3f(o2[0], o2[1], o2[2]);
		test[4] = new Vector3f(o2[3], o2[4], o2[5]);
		test[5] = new Vector3f(o2[6], o2[7], o2[8]);

		for (int i = 0; i < 3; ++i) {
			test[6 + i * 3 + 0] = new Vector3f(test[i]).cross(test[0]);
			test[6 + i * 3 + 1] = new Vector3f(test[i]).cross(test[1]);
			test[6 + i * 3 + 2] = new Vector3f(test[i]).cross(test[2]);
		}

		for (int i = 0; i < 15; ++i) {
			if (!overlapOnAxis(this, other, test[i])) {
				return false;
			}
		}

		return true;
	}
}
