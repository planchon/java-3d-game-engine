package World;

import org.joml.Vector3f;

import Renderer.Shader;

public class WorldObject {
	Vector3f pos;
	
	public WorldObject(Vector3f position) {
		this.pos = position;
	}
	
	public void render(Shader shader) {
	}
}
