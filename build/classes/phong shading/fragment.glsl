// simple fragment shader
uniform bool ambient, diffuse, specular;
// 'time' contains seconds since the program was linked.
uniform float time;

varying vec3 N;
varying vec3 P;

vec4 shading(vec3 X, vec3 N, gl_LightSourceParameters light, gl_MaterialParameters mat) {
	vec4 result = vec4(0,0,0,1); //opaque black
	result += mat.ambient + light.ambient;//TODO: compute ambient contribution

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


void main()
{
	gl_LightSourceParameters light = gl_LightSource[0];
	gl_MaterialParameters mat = gl_FrontMaterial;
	gl_FragColor = shading(P, N, light, mat);
}
