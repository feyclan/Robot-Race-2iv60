// simple vertex shader
#version 120

void main()
{
    vec4 height = gl_Vertex;
    height.z = (0.6 * cos(0.3 * height.x + 0.2 * height.y)) + (0.4 * cos(height.x - 0.5 * height.y));
    gl_Position  = gl_ModelViewProjectionMatrix * height;      // model view transform
    gl_FrontColor = gl_Color;
    if(height.z<0){
        gl_FrontColor = vec4(0,0,1,1);
    } else if(height.z >= 0.5){
        gl_FrontColor = vec4(0.1,1,0.1,1);
    } else if(height.z >= 0 && height.z <0.5){
        gl_FrontColor = vec4(0.9, 0.8, 0.4,1);
    }
}