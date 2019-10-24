// simple vertex shader

#version 120
void main()
{
    gl_Position    = gl_ModelViewProjectionMatrix * gl_Vertex;      // model view transform
    gl_FrontColor = gl_Color;
}