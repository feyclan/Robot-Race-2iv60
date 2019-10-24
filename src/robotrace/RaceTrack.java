package robotrace;

import static com.jogamp.opengl.GL.GL_LINE_LOOP;//**********************************************8
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import static com.jogamp.opengl.GL2.*;

/**
 * Implementation of a race track that is made from Bezier segments.
 */
abstract class RaceTrack {
    
    /** The width of one lane. The total width of the track is 4 * laneWidth. */
    private final static float laneWidth = 1.22f;
    
    
    
    /**
     * Constructor for the default track.
     */
    public RaceTrack() {
    }


    
    /**
     * Draws this track, based on the control points.
     */
    public void draw(GL2 gl, GLU glu, GLUT glut) {
        double stepAmount = 100;
        //first collect the sets of points. 
        //5 arrays will store the points used to create 4 tracks between them
        Vector[] pointsA = new Vector[(int)stepAmount]; //closest set to the origin
        Vector[] pointsB = new Vector[(int)stepAmount];
        Vector[] pointsC = new Vector[(int)stepAmount]; //middle set, exactly on P(t)
        Vector[] pointsD = new Vector[(int)stepAmount];
        Vector[] pointsE = new Vector[(int)stepAmount]; //furthest set from the origin 
        
        //fill in the points
        for (double i = 0.0; i < stepAmount; i++){
            double t = 1.0/stepAmount * i;
            Vector point = getPoint(t);
            Vector toOrigin = getTangent(t).cross(Vector.Z).normalized(); // vector pointing from point to origin (calculated as normal to tangent)
            
            pointsA[(int)i] = point.subtract(toOrigin.scale(2*laneWidth));
            pointsB[(int)i] = point.subtract(toOrigin.scale(laneWidth));
            pointsC[(int)i] = point;
            pointsD[(int)i] = point.add(toOrigin.scale(laneWidth));
            pointsE[(int)i] = point.add(toOrigin.scale(2*laneWidth));
        }
        
        //each lane gets its own color
        Vector[] laneColors = new Vector[4];
        laneColors[0] = new Vector(0,0,1); //blue
        laneColors[1] = new Vector(0,1,0); //green
        laneColors[2] = new Vector(1,1,0); //yellow
        laneColors[3] = new Vector(1,0,0); //red
        
        //draw quads between the points to form the lanes   
        for (int i = 0; i < (stepAmount-1); i++){
            gl.glBegin(GL_QUAD_STRIP);
            gl.glColor3d(laneColors[0].x, laneColors[0].y, laneColors[0].z);
            gl.glVertex3d(pointsA[i].x, pointsA[i].y, pointsA[i].z);
            gl.glVertex3d(pointsA[i+1].x, pointsA[i+1].y, pointsA[i+1].z); //inner edge
            gl.glVertex3d(pointsB[i].x, pointsB[i].y, pointsB[i].z);
            gl.glVertex3d(pointsB[i+1].x, pointsB[i+1].y, pointsB[i+1].z); //lane 1
            
            gl.glColor3d(laneColors[1].x, laneColors[1].y, laneColors[1].z);
            gl.glVertex3d(pointsC[i].x, pointsC[i].y, pointsC[i].z);
            gl.glVertex3d(pointsC[i+1].x, pointsC[i+1].y, pointsC[i+1].z); //lane 2   
            
            gl.glColor3d(laneColors[2].x, laneColors[2].y, laneColors[2].z);
            gl.glVertex3d(pointsD[i].x, pointsD[i].y, pointsD[i].z);
            gl.glVertex3d(pointsD[i+1].x, pointsD[i+1].y, pointsD[i+1].z); //lane 3
            
            gl.glColor3d(laneColors[3].x, laneColors[3].y, laneColors[3].z);
            gl.glVertex3d(pointsE[i].x, pointsE[i].y, pointsE[i].z);
            gl.glVertex3d(pointsE[i+1].x, pointsE[i+1].y, pointsE[i+1].z); //lane 4
            gl.glEnd();
            
        }   
        //the final step of drawing requires connecting points [stepAmount] to points[0] (not possible with loop)
        gl.glBegin(GL_QUAD_STRIP);
        gl.glVertex3d(pointsA[(int)stepAmount-1].x, pointsA[(int)stepAmount-1].y, pointsA[(int)stepAmount-1].z);
        gl.glVertex3d(pointsA[0].x, pointsA[0].y, pointsA[0].z); //inner edge
        gl.glVertex3d(pointsB[(int)stepAmount-1].x, pointsB[(int)stepAmount-1].y, pointsB[(int)stepAmount-1].z);
        gl.glVertex3d(pointsB[0].x, pointsB[0].y, pointsB[0].z); //lane 1
        gl.glVertex3d(pointsC[(int)stepAmount-1].x, pointsC[(int)stepAmount-1].y, pointsC[(int)stepAmount-1].z);
        gl.glVertex3d(pointsC[0].x, pointsC[0].y, pointsC[0].z); //lane 2            
        gl.glVertex3d(pointsD[(int)stepAmount-1].x, pointsD[(int)stepAmount-1].y, pointsD[(int)stepAmount-1].z);
        gl.glVertex3d(pointsD[0].x, pointsD[0].y, pointsD[0].z); //lane 3
        gl.glVertex3d(pointsE[(int)stepAmount-1].x, pointsE[(int)stepAmount-1].y, pointsE[(int)stepAmount-1].z);
        gl.glVertex3d(pointsE[0].x, pointsE[0].y, pointsE[0].z); //lane 4
        gl.glEnd();
        
        
        
        /*
        gl.glPushMatrix();
             
        gl.glBegin(GL_LINE_LOOP);
        gl.glColor3f(0, 0, 0);
        gl.glLineWidth(2.5f);
        
        double stepSize = 1.0/stepAmount;    
        for (double i = 0.0; i <= 1.0; i += stepSize){
            Vector point = getPoint(i);
            gl.glVertex3d(point.x,point.y,point.z);
            System.out.println(i);
        }            
        gl.glEnd();
        
        
        gl.glPopMatrix(); 
        */
        
        
    }
    
    /**
     * Returns the center of a lane at 0 <= t < 1.
     * Use this method to find the position of a robot on the track.
     */
    public Vector getLanePoint(int lane, double t){

        return Vector.O;

    }
    
    /**
     * Returns the tangent of a lane at 0 <= t < 1.
     * Use this method to find the orientation of a robot on the track.
     */
    public Vector getLaneTangent(int lane, double t){
        
        return Vector.O;

    }
    
    
    
    // Returns a point on the test track at 0 <= t < 1.
    protected abstract Vector getPoint(double t);

    // Returns a tangent on the test track at 0 <= t < 1.
    protected abstract Vector getTangent(double t);
}
