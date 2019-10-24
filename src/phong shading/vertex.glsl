#version 120
uniform bool ambient, diffuse, specular;

varying vec3 N;
varying vec3 P;

vec4 shading(vec3 X, vec3 N, gl_LightSourceParameters light, gl_MaterialParameters mat) {
	vec4 result = vec4(0,0,0,1); //opaque black
	result += mat.ambient + light.ambient; //TODO: compute ambient contribution

	vec3 L = normalize((light.position.xyz/light.position.w) - X); // vector towards light source
	float D = max(0.0, dot(L, N));

	result += (light.diffuse + mat.diffuse) * D; //TODO: compute diffuse contribution

	vec3 E = (dot(2*N, L)*N) - L; //position of camera in View space
	vec4 eyePosition = gl_ModelViewMatrixInverse * vec4(0,0,0,1);
	vec3 C = eyePosition.xyz/eyePosition.w;
	vec3 V = normalize(X); // direction towards viewer
	float S = max(0.0, pow(dot(E, V), mat.shininess));

	result += (light.specular+mat.specular)*S; //TODO : compute specular contribution
	return result;
}


void main() {
	// pick up light LIGHT0 and material properties set in shadermaker
	gl_LightSourceParameters light = gl_LightSource[0];
	gl_MaterialParameters mat = gl_FrontMaterial;
	N = gl_NormalMatrix * gl_Normal; //TODO: transform normal vector to view space
	vec4 X = gl_ModelViewMatrix * gl_Vertex; //TODO: compute vertex position in 3D view coordinates
	P = X.xyz / X.w;
	//output of vertex sharttrder
	gl_TexCoord[0] = gl_MultiTexCoord0;
	gl_FrontColor = gl_Color;
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}