#version 330

layout (location = 0) in vec3 aPos;

uniform mat4 projectionMatrix;
uniform mat4 worldMatrix;
uniform mat4 viewMatrix;

void main() {
    mat4 mat = projectionMatrix * viewMatrix * worldMatrix;
    gl_Position = mat * vec4(aPos, 1.0);
}
