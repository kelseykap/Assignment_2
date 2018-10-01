public class Land {
	
    int dx;
    int dy;
    float[][] fullVals;
    float[][] shadeVals;
	static float shadefraction = 0.1f;
    static float growfactor = 1000.0f;
    
    /**
     Constructor class for the Land class
     
     @param dx takes in an int to set the x dimension of the land map
     @param dy takes in an int to set the y dimension of the land map
     */
	Land(int dx, int dy) {
        this.dx = dx; this.dy = dy;
        fullVals = new float[dx][dy];
        shadeVals = new float[dx][dy];
	}

    /**
     Method to get the x dimension of the land map
     
     @return returns the x dimension
     */
	int getDimX() {
		return dx;
	}
	
    /**
     Method to get the y dimension of the land map
     
     @return returns the y dimension
     */
	int getDimY() {
        return dy;
	}
	
    /**
     Method to reset the land map values to the original values, after they have been altered by the 'shade' of Tree objects
     */
	void resetShade() {
        for (int i = 0; i < dx; i++) {
            for (int j = 0; j< dy; j++) {
                setFull(i, j, getFull(i, j));
            }
        }
	}
	
    /**
     Method to get the original sun exposure value from a specific place in the array of sun exposure values
     
     @param x specifies the x coordinate
     @param y specfies the y coordinate
     @return returns the specified sun exposure value
     */
	float getFull(int x, int y) {
        return fullVals[x][y];
	}
	
    /**
     Method to set the sun exposure values for a specific place in the array of sun exposure values
     
     @param x specifies the x coordinate
     @param y specfies the y coordinate
     @param val takes in a float as the sun exposure value
     */
	void setFull(int x, int y, float val) {
        fullVals[x][y] = val;
        shadeVals[x][y] = val;
	}
	
    /**
     Method to get the reduced sun exposure value
     
     @param x specifies the x coordinate
     @param y specfies the y coordinate
     @return returns the specified sun exposure value
     */
	float getShade(int x, int y) {
        return shadeVals[x][y];
    }
	
    /**
     Method to set the reduced sun exposure value
     
     @param x specifies the x coordinate
     @param y specfies the y coordinate
     @param val sets the specified sun exposure value
     */
	void setShade(int x, int y, float val){
        shadeVals[x][y] = val;
    }
    
    /**
    Method to calculate the reduction of sun exposure due to the shade from a Tree object
    
    @param tree takes in a Tree object
    */
    void shadow(Tree tree){
        int ext = Math.round(tree.getExt());
        int x = tree.getX();
        int y = tree.getY();
        synchronized(this) {
            tree.sungrow(this);
            for (int i = -ext; i < ext+1; i++) {
                for (int j = -ext; j < ext+1; j++) {
                    try{
                        setShade(x+i, y+j, getShade(x+i, y+j)*shadefraction);
                    } catch (ArrayIndexOutOfBoundsException e) {};
                }
            }
        }
	}

}
