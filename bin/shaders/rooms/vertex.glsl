#version 330

layout (location = 0) in vec3 aPos;
//layout (location = 1) in vec3 aTexPos;
//layout (location = 2) in vec3 aNormal;

uniform mat4 projectionMatrix;
uniform mat4 worldMatrix;
uniform mat4 viewMatrix;

//out vec3 texPos;
//out vec3 vertexPos;
//out vec3 vertexNormal;

void main() {
	mat4 mat = projectionMatrix * viewMatrix * worldMatrix;
	gl_Position = mat * vec4(aPos, 1.0);
//	texPos = aTexPos;

//	vertexPos = aPos.xyz;
//	vertexNormal = normalize(mat * vec4(aNormal, 0.0)).xyz;
}
