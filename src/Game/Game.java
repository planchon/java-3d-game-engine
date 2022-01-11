package Game;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;

import java.util.*;

import Components.*;
import Entities.GameEntity;
import Systems.*;
import core.Engine;
import core.Entity;
import org.joml.Vector3f;

import Renderer.Camera;
import Renderer.Shader;
import Window.Window;
import World.WorldObject;

public class Game {
	public final static int TICK_COUNT  = 60;
	public final static long ONE_SECOND = 1000000000;
	public final static double NS_PER_TICK = ONE_SECOND / TICK_COUNT;
	public final static int WIDTH = 1300;
	public final static int HEIGHT = 700;
	public final static float Z_NEAR = 0.1f;
	public final static float Z_FAR  = 1000.f;
	public final static float FOV = (float) Math.toRadians(60.0f);
	public final static float MOUSE_SENSIBILITY = 0.3f;
	public final static float KEYBOARD_SPEED = 0.1f;
	
	public List<GameEntity> entities = new ArrayList<GameEntity>();
	public List<WorldObject> world_structure = new ArrayList<WorldObject>();
	public Camera cam;
	
	public long last_tick_clock;
	public long last_milli_clock;
	public int tick_count;
	public int frame_count;
	public int global_tick_count = 0;
	
	public Window win;
	
	private MouseInputHandler mouseInputHandler;
	private KeyboardInputHandler keyboardInputHandler;
	
	Shader testShader;
	Shader worldShader;

	public Engine engine;
	
	public void stop() {
		this.win.stop();
	}
	
	public void tick() {
		glfwPollEvents();
		this.tick_count++;
		engine.update(1/ 60.0);
	}

	public void render() {
		this.win.clear_screen();
		engine.render(1/ 60.0);
		this.frame_count++;
		this.win.render();
	}
	
	public void reset_stats() {
		this.tick_count = 0;
		this.frame_count = 0;
		this.last_milli_clock = System.nanoTime();
	}
	
	public void run() {
		last_tick_clock = System.nanoTime();
		last_milli_clock = System.nanoTime();
		double unprocessed = 0;
		boolean camera_was_pressed = false;
		
		while (this.win.can_continue_running()) {			
			long now_clock = System.nanoTime();
			unprocessed += (now_clock - last_tick_clock) / NS_PER_TICK;
			last_tick_clock = now_clock;
			
			if(this.win.isKeyPressed(258) && !camera_was_pressed) {
				System.out.println(this.cam);
				camera_was_pressed = true;
			}
		
			while (unprocessed >= 1) {
				tick();
				unprocessed -= 1;
			}
			
			try {
				Thread.sleep(2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			render();
			
			if (System.nanoTime() - last_milli_clock > ONE_SECOND) {
				System.out.println("[stats] tick " + this.tick_count + ", frames " + this.frame_count);
				camera_was_pressed = false;
				this.reset_stats();
			}
		}
	}

	public void init_ecs_engine_systems() {
		RenderSystem renderSystem = new RenderSystem();
		InputSystem inputSystem = new InputSystem();
		CameraSystem cameraSystem = new CameraSystem();
		RandomRotate randomRotate = new RandomRotate();

		engine.addRenderSystem(renderSystem);

		engine.addSystem(inputSystem);
		engine.addSystem(cameraSystem);
		engine.addSystem(randomRotate);
	}
	
	public void init() {
		this.win = new Window("3D renderer test", WIDTH, HEIGHT);
		this.win.init();

		engine = new Engine();
		this.worldShader = new Shader("/Users/paulplanchon/Dropbox/Dev copie/3D/res/shaders/rooms/vertex.glsl", "/Users/paulplanchon/Dropbox/Dev copie/3D/res/shaders/rooms/fragment.glsl");

		this.init_ecs_engine_systems();

		MeshComponent cubeMesh = new MeshComponent();
		cubeMesh.asCube();
		ShaderComponent shader = new ShaderComponent(this.worldShader);

		Random random = new Random();

		for (int i = 0; i < 100; i++) {
			Entity cube = engine.createEntity();
			cube.addComponent(cubeMesh);
			cube.addComponent(new PositionComponent(new Vector3f(random.nextInt(40), random.nextInt(3), random.nextInt(40))));
			cube.addComponent(shader);
			cube.addComponent(new RandomRotation(new Vector3f(random.nextFloat(), random.nextFloat(), random.nextFloat())));
			engine.addEntity(cube);
		}

		Entity playerEntity = engine.createEntity();
		playerEntity.addComponent(new CameraComponent(FOV, WIDTH, HEIGHT, Z_NEAR, Z_FAR));
		playerEntity.addComponent(new InputComponent(this.win));
		playerEntity.addComponent(new PositionComponent(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0)));
		engine.addEntity(playerEntity);
	}
}
