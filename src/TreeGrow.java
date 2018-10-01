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
    final static int sequential_cutoff = 25000;
    
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
        int runCount = 0;
        
        while (true) {
            
            if (v.reset == true) {
                v.reset();
                fjPool.invoke(new Reset(0, arr.length, arr, 100000));
                v.reset = false;
                
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                };
            }

            if (v.run == true) {
                tick();
                v.yearIncrease();
                for (int i = 0; i < 20 ; i+=2) {
                    fjPool.invoke(new Grow(0, arr.length, arr, map, sequential_cutoff, i));
                }
                
                map.resetShade();
                
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
