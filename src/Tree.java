public class Tree{
	
private
	int xpos;
    int ypos;
	float ext;
	
	static float growfactor = 1000.0f;
    
    Object[] rowLocks;
	
public	
	Tree(int x, int y, float e){
		xpos=x; ypos=y; ext=e;
	}
	
	int getX() {
		return xpos;
	}
	
	int getY() {
		return ypos;
	}
	
	float getExt() {
		return ext;
	}
	
	void setExt(float e) {
		ext = e;
	}

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
	
	boolean inrange(float minr, float maxr) {
		return (ext >= minr && ext < maxr);
	}
	
	void sungrow(Land land) {
        float avg;
        int extent = Math.round(ext);
        avg = sunexposure(land);
        ext = ext + (avg/growfactor);
    }
}
