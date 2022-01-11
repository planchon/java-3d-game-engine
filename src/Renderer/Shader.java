package Renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

import Utils.FileUtil;

public class Shader {
	public String vertex, fragment;
	public FileUtil vertexFile, fragmentFile;
	public int vertexShader, fragmentShader, programShader;
	
	public Shader(String vertex, String fragment) {
		this.vertex = vertex;
		this.fragment = fragment;
		
		this.vertexFile = new FileUtil(this.vertex);
		this.fragmentFile = new FileUtil(this.fragment);
		
		this.vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		GL20.glShaderSource(this.vertexShader, this.vertexFile.get_content());
		GL20.glCompileShader(this.vertexShader);
		
		if (GL20.glGetShaderi(this.vertexShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println("[shader] error in compilation of (vertex) " + this.vertex);
			System.out.println(GL20.glGetShaderInfoLog(this.vertexShader));
			System.exit(-1);
		}
		
		System.out.println("[shader] vertex " + this.vertex + " compiled");
		
		this.fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		GL20.glShaderSource(this.fragmentShader, this.fragmentFile.get_content());
		GL20.glCompileShader(this.fragmentShader);
		
		if (GL20.glGetShaderi(this.fragmentShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println("[shader] error in compilation of (fragment) " + this.fragment);
			System.out.println(GL20.glGetShaderInfoLog(this.fragmentShader));
			System.exit(-1);
		}
		
		System.out.println("[shader] vertex " + this.fragment + " compiled");
		
		this.programShader = GL20.glCreateProgram();
		GL20.glAttachShader(programShader, fragmentShader);
		GL20.glAttachShader(programShader, vertexShader);
		GL20.glLinkProgram(programShader);
		
		if (GL20.glGetProgrami(programShader, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
			System.out.println("[shader] error in compilation of (program)");
			System.out.println(GL20.glGetProgramInfoLog(this.programShader));
			System.exit(-1);
		}
	}
	
	protected void finalize() {
		GL20.glDeleteProgram(programShader);
		GL20.glDeleteShader(fragmentShader);
		GL20.glDeleteShader(vertexShader);
	}
	
	public void use() {
		GL20.glUseProgram(programShader);
	}
	
	public void unuse() {
		GL30.glUseProgram(0);
	}
	
	public void setMatrix(String name, Matrix4f mat) {
		try (MemoryStack stack = MemoryStack.stackPush()) {
            GL20.glUniformMatrix4fv(GL20.glGetUniformLocation(programShader, name), false, mat.get(stack.mallocFloat(16)));
        }
	}
	
	public void setColor(String name, Vector3f col) {
		try (MemoryStack stack = MemoryStack.stackPush()) {
            GL20.glUniform3fv(GL20.glGetUniformLocation(programShader, name), col.get(stack.mallocFloat(3)));
        } catch (Exception e) {
			System.out.println("cannont set color uniform " + e);
		}
	}
	
	public void setBool(String name, boolean value) {
		int tmp = 0;
		if (value) tmp = 1;
		GL20.glUniform1i(GL20.glGetUniformLocation(programShader, name), tmp);
	}
	
	public void setInt(String name, int value) {
		GL20.glUniform1i(GL20.glGetUniformLocation(programShader, name), value);
	}
	
	public void setFloat(String name, float value) {
		int uniform_location = GL20.glGetUniformLocation(programShader, name);
		GL20.glUniform1f(uniform_location, value);
	}
}
