import java.awt.Color;
import java.util.Random;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.*;


public class ForestPanel extends JPanel implements Runnable {
	Tree[] forest;
	List<Integer> rndorder;
	
	ForestPanel(Tree[] trees) {
		forest=trees;
	}
	
    /**
     Method to
     
     @param g
     */
	public void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		g.clearRect(0,0,width,height);
		    
		Random rnd = new Random(0);

		float minh = 0.0f;
		float maxh = 2.0f;
		for(int layer = 0; layer <= 10; layer++) {
			for(int t = 0; t < forest.length; t++){
				int rt = rndorder.get(t); 
				if(forest[rt].getExt() >= minh && forest[rt].getExt() < maxh) {
					g.setColor(new Color(rnd.nextInt(100), 150+rnd.nextInt(100), rnd.nextInt(100)));
					g.fillRect(forest[rt].getY() - (int) forest[rt].getExt(), forest[rt].getX() - (int) forest[rt].getExt(),
						   2*(int) forest[rt].getExt()+1,2*(int) forest[rt].getExt()+1);
				}
			}
			minh = maxh;
			maxh += 2.0f;
		}	
	}
	
    /**
     Method to
     */
	public void run() {
			
		rndorder = new ArrayList<Integer>();
		for(int l = 0; l < forest.length; l++)
			rndorder.add(l);
		java.util.Collections.shuffle(rndorder);
		
		while(true) {
			repaint(50);
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			};
		}
	}

}
