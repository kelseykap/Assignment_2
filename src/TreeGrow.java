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
    static final ForkJoinPool fjPool = new ForkJoinPool();
    
    /**
     Method to start a timer
     */
	private static void tick(){
		startTime = System.currentTimeMillis();
	}
	
    /**
     Method to stop a timer and return the time passed
     */
	private static float tock(){
		return (System.currentTimeMillis() - startTime) / 1000.0f; 
	}
    
    /**
     Main method for the application
     */
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
        
        while (true) {
            
            if (v.reset == true) {
                v.set(0);
                fjPool.invoke(new Reset(0, arr.length, arr, 100000));
                v.reset = false;
                
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                };
            }

            if (v.run == true) {
                //tick();
                v.incr();
                for (int i = 0; i < 20 ; i+=2) {
                    fjPool.invoke(new Grow(0, arr.length, arr, map, sequential_cutoff, i));
                }
                
                map.resetShade();
                
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                };
                
                //System.out.println(tock());

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
