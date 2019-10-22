package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL2ES3.GL_QUADS;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.*;
import java.nio.FloatBuffer;
import java.util.Arrays;

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
    public void draw(GL2 gl, GLU glu, GLUT glut, float tAnim, float xPos, GlobalState gs) {
        position.x = xPos; //weet niet of dit moet neem aan van wel?
        gl.glPushMatrix();    
        //gl.glMaterialfv(GL_FRONT_AND_BACK,GL_DIFFUSE, FloatBuffer.wrap(material.diffuse));

        gl.glColor3d(1,1,0.4);
        gl.glTranslated(xPos,0,4.5);
        glut.glutSolidCube(1); //head
        drawTorso(gl, glu, glut);
        drawRightArm(gl, glu, glut, gs, tAnim);
        gl.glPopMatrix();
        //********
        gl.glPushMatrix();
        drawLeftArm(gl, glu, glut, xPos, tAnim); //own matrix as well
        gl.glPopMatrix();
        //********
        gl.glPushMatrix();
        drawLeftLeg(gl, glu, glut, xPos, tAnim);
        //drawRightLeg(gl, glu, glut);
        gl.glPopMatrix();
        //********
        gl.glPushMatrix();
        drawRightLeg(gl, glu, glut, xPos, tAnim);
        gl.glPopMatrix();
    }
    
    public void drawTorso(GL2 gl, GLU glu, GLUT glut){
        gl.glColor3d(0,0,0);
        gl.glTranslated(0,0,-1.5);
        gl.glScaled(1,1,2);
        glut.glutSolidCube(1); //torso
    }
    
    public void drawRightArm(GL2 gl, GLU glu, GLUT glut, GlobalState gs, float tAnim){
        gl.glColor3d(0,0.3,0);
        gl.glTranslated(0.75, 0, 0.25);
        gl.glScaled(0.5,1,0.5);
        gl.glRotated(-90.0 * Math.sin(tAnim), 1, 0, 0);
        glut.glutSolidCube(1); //first arm
        drawRightArm2(gl, glu,glut, gs, tAnim);
    }
    
    public void drawRightArm2(GL2 gl, GLU glu, GLUT glut, GlobalState gs, float tAnim){
        gl.glTranslated(0,0,-1);
        gl.glTranslated(0,0,0.5);
        //gl.glRotated(gs.sliderB * -90.0, 1, 0, 0);  
        gl.glRotated(rotAngle(tAnim), 1, 0, 0);
        gl.glTranslated(0,0,-0.5); //rotation around the upper arm, hence translation
        glut.glutSolidCube(1);
        
   
    }
    
    public double rotAngle(float tAnim){
        double t = Math.sin(tAnim);
        if(t<0){
            return 0;
        } else{
            return t*-90;
        }
    }
    
    public void drawLeftArm(GL2 gl, GLU glu, GLUT glut, float xPos, float tAnim){
        /**gl.glTranslated(-3, 0, 1);
        glut.glutSolidCube(1); // second arm **/
        gl.glTranslated(-0.75+xPos,0,3.5);
        gl.glScaled(0.5,1,1);
        gl.glRotated(90.0 * Math.sin(tAnim), 1, 0, 0);
        glut.glutSolidCube(1);
        drawLeftArm2(gl,glu,glut, tAnim);
    }
    
    public void drawLeftArm2(GL2 gl, GLU glu, GLUT glut, float tAnim){
        gl.glTranslated(0,0,-1);
        gl.glTranslated(0,0,0.5);
        gl.glRotated(rotAngle2(tAnim), 1, 0, 0);
        gl.glTranslated(0,0,-0.5);
        glut.glutSolidCube(1);
             
    }
    
    public double rotAngle2(float tAnim){
        double t = Math.sin(tAnim);
        if(t>0){
            return 0;
        } else{
            return t*90;
        }
    }
    
    public void drawLeftLeg(GL2 gl, GLU glu, GLUT glut, float xPos, float tAnim){
        gl.glColor3d(1,0,0.5);
        gl.glTranslated(-0.25+xPos, 0, 1.5);
        gl.glScaled(0.5,1,1);
        gl.glTranslated(0,0.25,0.25); //optioneel, comment maken als zonder beter uitziet
        gl.glRotated(-90.0 * Math.sin(tAnim), 1, 0, 0);
        gl.glTranslated(0,-0.25,-0.25); //optioneel, comment maken als zonder beter uitziet
        glut.glutSolidCube(1); //first leg
        drawLeftLeg2(gl,glu,glut,tAnim);
    }
    
    public void drawLeftLeg2(GL2 gl, GLU glu, GLUT glut, float tAnim){
        gl.glTranslated(0,0,-1);
        glut.glutSolidCube(1);
    }
    
    public void drawRightLeg(GL2 gl, GLU glu, GLUT glut,float xPos, float tAnim){
        //gl.glColor3d(1,0,0.5);
        gl.glTranslated(0.25+xPos, 0, 1.5);
        gl.glScaled(0.5,1,1);
        gl.glTranslated(0,0.25,0.25); //optioneel, comment maken als zonder beter uitziet
        gl.glRotated(90.0 * Math.sin(tAnim), 1, 0, 0);
        gl.glTranslated(0,-0.25,-0.25); //optioneel, comment maken als zonder beter uitziet
        glut.glutSolidCube(1); 
        drawRightLeg2(gl,glu,glut);
    }
    
    public void drawRightLeg2(GL2 gl, GLU glu, GLUT glut){
        gl.glTranslated(0,0,-1);
        glut.glutSolidCube(1);
    }
}
