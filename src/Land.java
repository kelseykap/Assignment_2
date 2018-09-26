public class Land {
	
    int dx;
    int dy;
    float[][] fullVals;
    float[][] shadeVals;

	static float shadefraction = 0.1f;
    
	Land(int dx, int dy) {
        this.dx = dx; this.dy = dy;
        fullVals = new float[dx][dy];
        shadeVals = new float[dx][dy];
	}

	int getDimX() {
		return dx;
	}
	
	int getDimY() {
        return dy;
	}
	
	void resetShade() {
        for (int i = 0; i < dx; i++) {
            for (int j = 0; j< dy; j++) {
                setFull(i, j, getFull(i, j));
            }
        }
	}
	
	float getFull(int x, int y) {
        return fullVals[x][y];
	}
	
	void setFull(int x, int y, float val) {
        fullVals[x][y] = val;
        shadeVals[x][y] = val;
	}
	
	float getShade(int x, int y) {
		return shadeVals[x][y];
	}
	
	void setShade(int x, int y, float val){
        shadeVals[x][y] = val;
	}
	
	void shadow(Tree tree){
        int ext = Math.round(tree.getExt());
        int x = tree.getX();
        int y = tree.getY();
        for (int i = -ext; i < ext+1; i++) {
            for (int j = -ext; j < ext+1; j++) {
                try{
                    setShade(x+i, y+j, getShade(x+i, y+j)*shadefraction);
                } catch (ArrayIndexOutOfBoundsException e) {};
            }
        }
	}
}
