#version 330

uniform sampler2DArray tiles;
uniform vec3 aColor;
uniform bool lines;

in vec3 texPos;

out vec4 FragColor;

void main() {
	//FragColor = vec4(1, 0, 0, 1.0f);
	if (lines) {
		FragColor = vec4(aColor, 1.0f);
	} else {
		FragColor = texture(tiles, texPos);
	}
}
