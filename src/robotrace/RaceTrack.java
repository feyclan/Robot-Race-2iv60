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
        double stepAmount = 50;
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
        
        //storing the points together allows a cleaner code by using a for-loop later
        Vector[][] points = new Vector[5][];
        points[0] = pointsA;
        points[1] = pointsB;
        points[2] = pointsC;
        points[3] = pointsD;
        points[4] = pointsE;
        
        //each lane gets its own color
        Vector[] laneColors = new Vector[4];
        laneColors[0] = new Vector(0,0,1); //blue
        laneColors[1] = new Vector(0,1,0); //green
        laneColors[2] = new Vector(1,1,0); //yellow
        laneColors[3] = new Vector(1,0,0); //red
        
        //draw quads between the points to form the lanes   
        //[i] = the point along P(i); [l] = the lane number
        for (int i = 0; i < (stepAmount-1); i++){
            for (int l = 0; l < 4; l++){
                gl.glBegin(GL_QUADS); //decided against using GL_QUAD_STRIPS, as this interpolates the color between vertices
                gl.glColor3d(laneColors[l].x, laneColors[l].y, laneColors[l].z);            //select Color
                
                gl.glVertex3d(points[l][i+1].x, points[l][i+1].y, points[l][i+1].z);        //top left
                gl.glVertex3d(points[l+1][i+1].x, points[l+1][i+1].y, points[l+1][i+1].z);  //top right
                gl.glVertex3d(points[l+1][i].x, points[l+1][i].y, points[l+1][i].z);        //bottom right
                gl.glVertex3d(points[l][i].x, points[l][i].y, points[l][i].z);              //bottom left
                gl.glEnd();            
            }          
        }   
        //the final strip requires connecting points[stepAmount-1] to points[0] (not possible with loop) 
        for (int l = 0; l < 4; l++){
            gl.glBegin(GL_QUADS);          
            gl.glColor3d(laneColors[l].x, laneColors[l].y, laneColors[l].z);
            
            gl.glVertex3d(points[l][0].x, points[l][0].y, points[l][0].z); 
            gl.glVertex3d(points[l+1][0].x, points[l+1][0].y, points[l+1][0].z);      
            gl.glVertex3d(points[l+1][(int)stepAmount-1].x, points[l+1][(int)stepAmount-1].y, points[l+1][(int)stepAmount-1].z);
            gl.glVertex3d(points[l][(int)stepAmount-1].x, points[l][(int)stepAmount-1].y, points[l][(int)stepAmount-1].z);
            gl.glEnd();         
        }
        
        //drawing lines between the lanes makes them more distinguishable
        //[pts] = set of points;; [p] = point in set
        for (int n = 0; n < 5; n++){
            Vector[] pts = points[n];
            gl.glBegin(GL_LINE_LOOP);
            gl.glColor3f(0, 0, 0);
            gl.glLineWidth(5.0f);
            gl.glVertex3d(pts[0].x, pts[0].y, pts[0].z); //starting point
            for (Vector p : pts){
                gl.glVertex3d(p.x, p.y, p.z); 
            }
            gl.glEnd();
        }
        
        //finally the sides should be drawn
        //inner side:
        gl.glColor3d(189.0/255.0, 140.0/255.0, 43.0/255.0);
        gl.glBegin(GL_QUAD_STRIP);
        for (Vector p : pointsA){
            Vector lowP = p.subtract(new Vector(0.0,0.0,2.0));
            gl.glVertex3d(lowP.x, lowP.y, lowP.z);
            gl.glVertex3d(p.x, p.y, p.z);
            
        }
        //final quad
        Vector P = pointsA[0];
        Vector lowp = P.subtract(new Vector(0.0,0.0,2.0));
        gl.glVertex3d(lowp.x, lowp.y, lowp.z);
        gl.glVertex3d(P.x, P.y, P.z);
        gl.glEnd();
        
        //outer side:
        gl.glBegin(GL_QUAD_STRIP);
        for (Vector p : pointsE){
            Vector lowP = p.subtract(new Vector(0.0,0.0,2.0));
            gl.glVertex3d(lowP.x, lowP.y, lowP.z);
            gl.glVertex3d(p.x, p.y, p.z);
            
        }
        //final quad
        Vector p = pointsE[0];
        Vector lowP = p.subtract(new Vector(0.0,0.0,2.0));
        gl.glVertex3d(lowP.x, lowP.y, lowP.z);
        gl.glVertex3d(p.x, p.y, p.z);
        gl.glEnd();
        
        
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
