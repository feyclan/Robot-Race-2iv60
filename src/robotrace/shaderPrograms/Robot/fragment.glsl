#version 120

varying vec3 N;
varying vec3 P;

uniform sampler2D robotTexture;

vec4 shading(vec3 P, vec3 N, gl_LightSourceParameters light, gl_MaterialParameters mat){
    vec4 result = vec4(0,0,0,1);
    result += mat.ambient + light.ambient;
    
    vec3 L = normalize((light.position.xyz/light.position.w) - P); // vector towards light source
    float D = max(0.0, dot(L, N)); //diffuse contribution

    result += light.diffuse*mat.diffuse * D;

    vec3 E = normalize((dot(2*N, L)*N) - L); //light reflected
    vec4 eyePosition = gl_ModelViewMatrixInverse * vec4(0,0,0,1);
    vec3 C = eyePosition.xyz/eyePosition.w; //position of camera in viewspace
    vec3 V = normalize(C - P); // direction towards viewer
    float S = max(0.0, pow(dot(E, V), mat.shininess)); //specular contribution

    result += light.specular*mat.specular*S;
    
    return result;

}

void main()
{
	gl_LightSourceParameters light = gl_LightSource[0];
	gl_MaterialParameters mat = gl_FrontMaterial;
	gl_FragColor = texture2D(robotTexture, gl_TexCoord[0].st) + shading(P, N, light, mat);
}
