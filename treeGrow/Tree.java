package treeGrow;

// Trees define a canopy which covers a square area of the landscape
public class Tree{
	
private
	int xpos;	// x-coordinate of center of tree canopy
	int ypos;	// y-coorindate of center of tree canopy
	float ext;	// extent of canopy out in vertical and horizontal from center
	
	static float growfactor = 1000.0f; // divide average sun exposure by this amount to get growth in extent
	
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

	// return the average sunlight for the cells covered by the tree
	float sunexposure(Land land){
        float sum = 0;
        int extent = Math.round(ext);
        for (int i = -extent; i < extent+1; i++) {
            for (int j = -extent; j < extent+1; j++) {
                try{
                    sum = sum + land.getShade(xpos+i, ypos+j);
                } catch (ArrayIndexOutOfBoundsException e) {};
            }
        }
        //System.out.println("\nSum:");
        //System.out.printf("%.6f", sum);
        //System.out.println("\nNumber of cells");
        //System.out.printf("%.6f", (float)Math.pow( (2*(Math.round(ext)) + 1), 2 ));
        return sum/((float)Math.pow( (2*(Math.round(ext)) + 1), 2 ));
	}
	
	// is the tree extent within the provided range [minr, maxr)
	boolean inrange(float minr, float maxr) {
		return (ext >= minr && ext < maxr);
	}
	
	// grow a tree according to its sun exposure
	void sungrow(Land land) {
        float avg = sunexposure(land);
        ext = ext + (avg/growfactor);
        //System.out.println("\nAvg: ");
        //System.out.printf("%.8f", avg);
	}
}
