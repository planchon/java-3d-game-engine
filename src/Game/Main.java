package Game;

import core.Engine;
import org.joml.Vector3f;
import phys.OBB;

public class Main {
	public static void main(String[] args) {
		Game game = new Game();
		game.init();
		game.run();
		game.stop();
	}
}
