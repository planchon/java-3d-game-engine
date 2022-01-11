#version 330

layout (location = 0) in vec3 aPos;
layout (location = 1) in vec3 aTexPos;

uniform mat4 projectionMatrix;
uniform mat4 worldMatrix;
uniform mat4 viewMatrix;

out vec3 texPos;

void main() {
	gl_Position = projectionMatrix * viewMatrix * worldMatrix * vec4(aPos, 1.0);
	texPos = aTexPos;
}
