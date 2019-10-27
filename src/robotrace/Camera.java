package robotrace;

/**
 * Implementation of a camera with a position and orientation. 
 */
class Camera {

    /** The position of the camera. */
    public Vector eye = new Vector(3f, 6f, 5f);

    /** The point to which the camera is looking. */
    public Vector center = Vector.O;

    /** The up vector. */
    public Vector up = Vector.Z;

    /**
     * Updates the camera viewpoint and direction based on the
     * selected camera mode.
     */
    public void update(GlobalState gs, Robot focus) {

        switch (gs.camMode) {
            
            // First person mode    
            case 1:
                setFirstPersonMode(gs, focus);
                break;
                
            // Default mode    
            default:
                setDefaultMode(gs);
        }
    }

    /**
     * Computes eye, center, and up, based on the camera's default mode.
     */
    private void setDefaultMode(GlobalState gs) {
        center = gs.cnt;
        up = Vector.Z;
        
        eye = new Vector(0,0,0);
        eye.x = Math.cos(gs.theta) * Math.sin(gs.phi) * gs.vDist;
        eye.y = Math.sin(gs.theta) * Math.sin(gs.phi) * gs.vDist;
        eye.z = Math.cos(gs.phi) * gs.vDist;
        //eye.scale(gs.vDist); FOR SOME REASON THIS DOES NOT WORK?
        
    }

    /**
     * Computes eye, center, and up, based on the first person mode.
     * The camera should view from the perspective of the robot.
     */
    private void setFirstPersonMode(GlobalState gs, Robot focus) {
        up = Vector.Z;
        center = focus.position;
        center = center.scale(gs.vDist); //scaled to be further away from the eye position
        center = rotation(center); //camera is always at looking at the right of the track, so it needs to be rotated 90 degrees so the 1st person camera actually looks forward
        
        eye = focus.position;
        eye.z = 3;
        //eye.scale(0.25);
        
    }
    
    public Vector rotation(Vector center){
        Vector a = new Vector(0,0,0);
        a.x = -center.y; //x' = x cos(a) - y sin(a), since we're rotating 90 degrees, xcos(a) becomes 0 and sin(a) is 1
        a.y = center.x; //y' = x sin(a) + y cos(a), same reasoning as above
        a.z = 3;
        return a;
    } 
    
}
