public class Tree{
	
	private int xpos;
    private int ypos;
	private float ext;
	static float growfactor = 1000.0f;
	
    /**
     Constructor method for the Tree class
     
     @param x takes in the x coordinate of the center of the Tree object
     @param y takes in the y coordinate of the center of the Tree object
     @param e takes in a value for the extent of the Tree object
     */
	Tree(int x, int y, float e){
		xpos=x; ypos=y; ext=e;
	}
	
    /**
     Method to get the x coordinate
     
     @return returns the x position as an int value
     */
	int getX() {
		return xpos;
	}
	
    /**
     Method to get the t coordinate
     
     @return returns the y position as an int value
     */
	int getY() {
		return ypos;
	}
	
    /**
     Method to get the extent of the Tree object
     
     @return the extent as a float value
     */
	float getExt() {
		return ext;
	}
	
    /**
     Method to set the extent of the Tree object
     
     @param e takes in a new float value for the extent     
     */
	void setExt(float e) {
		ext = e;
	}

    /**
     Method to check if the Tree has an extent within the specified bounds
     
     @param minr takes in the lower bound as a float
     @param maxr takes in the upper bound as a float
     @return returns a boolean value that specifies if the extent is within the range
     */
    boolean inrange(float minr, float maxr) {
        return (ext >= minr && ext < maxr);
    }
    
    /**
     Method to calculate the average sun exposure a Tree object receives by accessing the shade values of stored in the Land object
     
     @param land takes in an object of type Land
     @return returns the average sun exposure to a Tree object as a float
     */
	float sunexposure(Land land){
        float sum = 0;
        int numCells = 0;
        int extent = Math.round(ext);
        synchronized(this) {
            for (int i = -extent; i < extent+1; i++) {
                for (int j = -extent; j < extent+1; j++) {
                    try{
                        sum = sum + land.getShade(xpos+i, ypos+j);
                        numCells++;
                    }  catch (ArrayIndexOutOfBoundsException e) {};
                }
            }
        }
        return sum/numCells;
	}
	
    /**
     Method to obtain the average sun exposure to a Tree object and set its new extent
     
     @param land takes in a Land object
     */
	void sungrow(Land land) {
        float avg;
        int extent = Math.round(ext);
        avg = sunexposure(land);
        ext = ext + (avg/growfactor);
    }
}
