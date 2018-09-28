import java.util.concurrent.ForkJoinPool;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.Timer;

public class TreeGrow {
	
    static long startTime = 0;

    static Tree[] arr;
    static Land map;
    final static int sequential_cutoff = 750000;
    
    static int count;
    
	private static void tick(){
		startTime = System.currentTimeMillis();
	}
	
	private static float tock(){
		return (System.currentTimeMillis() - startTime) / 1000.0f; 
	}
	
    static final ForkJoinPool fjPool = new ForkJoinPool();
    
	public static void main(String[] args) {
		
		if(args.length != 1)
		{
			System.out.println("Incorrect number of command line arguments. Should have form: java src/TreeGrow attachments/<intputfilename>");
			System.exit(0);
		}
        
        SunData sundata = new SunData();
		sundata.readData(args[0]);
		System.out.println("Data loaded");
        arr = sundata.trees;
        map = sundata.sunmap;

		View v = new View(sundata.sunmap.getDimX(), sundata.sunmap.getDimY(), arr);
		
        // garbage collection
        //get active thread count
        
        count = 0;
        int runCount = 0; // for testing specific amount of runs
        
        while (true) {
            
            if (v.reset == true) {
                //count = 0;
                //v.yearLabel.setText("Year " + Integer.toString(count));
                //v.set(0);
                v.reset();
                fjPool.invoke(new Reset(0, arr.length, arr, 100000));
                v.reset = false;
                
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                };
            }

            if (v.run == true) {
                //System.out.println("\nRun " + count);
                tick();
                v.yearIncrease();
                //v.incr();
                //v.reset();
                for (int i = 0; i < 20 ; i+=2) {
                    fjPool.invoke(new Grow(0, arr.length, arr, map, sequential_cutoff, i));
                }
                
                //System.out.printf("%.6f", arr[0].getExt());
                
                /**float totalShade = 0;
                for (int k = 0; k < map.dx; k++) {
                    for (int j = 0; j < map.dy; j++) {
                        totalShade = totalShade + map.shadeVals[k][j];
                    }
                }
                System.out.println(totalShade);*/
                
                map.resetShade();
                //count++;
                //v.yearLabel.setText("Year " + Integer.toString(count));
                //sundata.writeData("attachments/ParallelOutput" + Integer.toString(count) + ".txt");
                
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                };
                
                System.out.println(tock());
                runCount++;
                if (runCount > 100) { System.exit(0); }

            }
            else {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                };
            }
        }
	}
}
