
package robotrace;

/**
 * Implementation of RaceTrack, creating a track from a parametric formula
 */
public class ParametricTrack extends RaceTrack {
    
    @Override
    protected Vector getPoint(double t) {
        //Formula: P(t) = (10 cos(2Pt); 14 sin(2Pt); 1)
        
        double x = 10 * Math.cos(2 * Math.PI * t);
        double y = 14 * Math.sin(2 * Math.PI * t);
        double z = 1;
        
        return new Vector(x,y,z);

    }

    @Override
    protected Vector getTangent(double t) {
        //Formula: P'(t) = (-20Psin(2Pt), 28Pcos(2Pt), 0)
        
        double x = -20 * Math.PI * Math.sin(2 * Math.PI * t);
        double y = 28 * Math.PI * Math.cos(2 * Math.PI * t);
        double z = 0;
        
        return new Vector(x,y,z);

    }
    
}
