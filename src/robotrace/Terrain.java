package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

/**
 * Represents the terrain, to be implemented according to the Assignments.
 */
class Terrain {

    
    
    public Terrain() {
        
    }

    /**
     * Draws the terrain.
     */
    public void draw(GL2 gl, GLU glu, GLUT glut) {
        gl.glColor3d(0.2,0.20,0.2);
        for(int y = -20; y<=20; y++){
            for(int x = -20; x<=20; x++){
                gl.glBegin(GL2.GL_TRIANGLE_STRIP);
                    //gl.glTexCoord2d(0, 0);
                    gl.glVertex3d(x, y, 0); //x,y,z
                    //gl.glTexCoord2d(0, 1);
                    gl.glVertex3d(x, y + 1, 0);
                    //gl.glTexCoord2d(1, 0);
                    gl.glVertex3d(x + 1, y, 0);
                    //gl.glTexCoord2d(1, 1); nog niet nodig; geen texture
                    gl.glVertex3d(x + 1, y + 1, 0);
                gl.glEnd(); //draws one square
            }
        }
    }
    
}
