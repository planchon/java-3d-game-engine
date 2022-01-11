#version 330

const int MAX_POINT_LIGHT = 5;

struct PointLight {
	vec3 position;
	vec3 color;
	float power;
	float falloff;
};

uniform PointLight pointLight[MAX_POINT_LIGHT];

uniform sampler2DArray tiles;
uniform vec3 aColor;
uniform bool lines;

//in vec3 texPos;

out vec4 FragColor;

void main() {
	// for debugging
	if (lines) {
		FragColor = vec4(aColor, 1.0f);
	} else {
		//FragColor = texture(tiles, texPos);
		FragColor = vec4(0.5, 0.5, 0.6, 1);
	}
}
