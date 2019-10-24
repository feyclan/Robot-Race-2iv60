#version 120
varying vec3 P;
varying vec3 N;



}
void main()
{
    gl_LightSourceParameters light = gl_LightSource[0];
    gl_MaterialParameters mat = gl_FrontMaterial;
    N = gl_NormalMatrix * gl_Normal; //normal in viewspace
    vec4 X = gl_ModelViewMatrix * gl_Vertex; //vertex position in viewspace
    P = X.xyz / X.w;

    gl_Position    = gl_ModelViewProjectionMatrix * gl_Vertex; // model view transform
    gl_FrontColor = gl_Color;
}