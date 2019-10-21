package robotrace;

/**
* Materials that can be used for the robots.
*/
public enum Material {

    /** 
     * Gold material properties.
     * Modify the default values to make it look like gold.
     */
    GOLD (
            
        new float[] {0.59f, 0.58f, 0.35f, 1}, //diffuse
        new float[] {0.71f, 0.71f, 0.71f, 1}, //specular
        10                         //shininess, high

    ),

    /**
     * Silver material properties.
     * Modify the default values to make it look like silver.
     */
    SILVER (
            
        new float[] {0.07f, 0.07f, 0.07f, 1},
        new float[] {0.26f, 0.26f, 0.26f, 1},
        11

    ),

    /** 
     * Orange material properties.
     * Modify the default values to make it look like orange.
     */
    ORANGE (
            
        new float[] {0.87f, 0.65f, 0.02f, 1},
        new float[] {0.29f, 0.29f, 0.29f, 1},
        4

    ),

    /**
     * Wood material properties.
     * Modify the default values to make it look like Wood.
     */
    WOOD (

        new float[] {0.24f, 0.16f, 0.09f, 1},
        new float[] {0, 0, 0, 1},
        0   //low shininess

    );

    /** The diffuse RGBA reflectance of the material. */
    float[] diffuse;

    /** The specular RGBA reflectance of the material. */
    float[] specular;
    
    /** The specular exponent of the material. */
    float shininess;

    /**
     * Constructs a new material with diffuse and specular properties.
     */
    private Material(float[] diffuse, float[] specular, float shininess) {
        this.diffuse = diffuse;
        this.specular = specular;
        this.shininess = shininess;
    }
}
