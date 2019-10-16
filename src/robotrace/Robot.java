package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL2ES3.GL_QUADS;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.*;

/**
* Represents a Robot, to be implemented according to the Assignments.
*/
class Robot {
    
    /** The position of the robot. */
    public Vector position = new Vector(0, 0, 0);
    
    /** The direction in which the robot is running. */
    public Vector direction = new Vector(1, 0, 0);

    /** The material from which this robot is built. */
    private final Material material;
    
    

    /**
     * Constructs the robot with initial parameters.
     */
    public Robot(Material material
            
    ) {
        this.material = material;

        
    }

    /**
     * Draws this robot (as a {@code stickfigure} if specified).
     */
    public void draw(GL2 gl, GLU glu, GLUT glut, float tAnim) {
        gl.glPushMatrix();        
        gl.glTranslated(0,0,4.5);
        glut.glutSolidCube(1); //head
        gl.glColor3d(0,0,0);
        gl.glTranslated(0,0,-1.5);
        gl.glScaled(1,1,2);
        glut.glutSolidCube(1); //torso
        gl.glColor3d(0,0.3,0);
        gl.glTranslated(0.75, 0, 0);
        gl.glScaled(0.5,1,1);
        glut.glutSolidCube(1); //first arm
        gl.glTranslated(-3, 0, 0);
        glut.glutSolidCube(1); // second arm
        gl.glTranslated(1, 0, -1);
        glut.glutSolidCube(1); //first leg
        gl.glTranslated(1,0,0);
        glut.glutSolidCube(1); //second leg
        gl.glPopMatrix();
    }
    
    public void niks(GL2 gl, GLU glu, GLUT glut){
        gl.glColor3d(0.5,0.5,0.5);
        gl.glTranslated(0,0,2.5);
        glut.glutSolidCube(1); // head
        gl.glColor3d(0.1,0.1,0.1);
        gl.glTranslated(0,0,-1.5);
        gl.glScaled(1.5,1,2);
        glut.glutSolidCube(1); //torso
        gl.glColor3d(0.6, 0.4,0.5);
        gl.glScaled(0.5/1.5, 1, 0.9);
        gl.glTranslated(0.75+1.25,0,0);
        glut.glutSolidCube(1);
        gl.glTranslated(-4,0,0);
        glut.glutSolidCube(1);//arms
        gl.glTranslated(2.75,0,-1.025);
        gl.glScaled(1.3,1,1);
        glut.glutSolidCube(1);
        gl.glTranslated(-1.25, 0,0);
        glut.glutSolidCube(1);//legs
        //WAARDES LATEN KLOPPEN
    }
}
