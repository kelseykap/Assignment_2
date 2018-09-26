package treeGrow;

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
	static int frameX;
	static int frameY;
	static ForestPanel fp;

    private static JButton resetButton;
    private static JButton pauseButton;
    private static JButton playButton;
    private static JButton endButton;
    
    static JLabel yearLabel = new JLabel("Year 0");
    
    static Tree[] arr;
    static Land map;
    final static int sequential_cutoff = 10000;
    static int interval;
    
    static boolean run;
    static int count;
    
	// start timer
	private static void tick(){
		startTime = System.currentTimeMillis();
	}
	
	// stop timer, return time elapsed in seconds
	private static float tock(){
		return (System.currentTimeMillis() - startTime) / 1000.0f; 
	}
	
	public static void setupGUI(int frameX, int frameY, Tree [] trees) {
		Dimension fsize = new Dimension(800, 800);
		// Frame init and dimensions
    	JFrame frame = new JFrame("Photosynthesis"); 
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setPreferredSize(fsize);
    	frame.setSize(800, 800);
    	
      	JPanel g = new JPanel();
        g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS)); 
      	g.setPreferredSize(fsize);
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.LINE_AXIS));
        titlePanel.add(yearLabel);
        g.add(titlePanel);
        
		fp = new ForestPanel(trees);
		fp.setPreferredSize(new Dimension(frameX,frameY));
		JScrollPane scrollFrame = new JScrollPane(fp);
		fp.setAutoscrolls(true);
		scrollFrame.setPreferredSize(fsize);
	    g.add(scrollFrame);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        JButton resetButton = new JButton("Reset");
        buttonPanel.add(resetButton);
        JButton playButton = new JButton("Play");
        buttonPanel.add(playButton);
        JButton pauseButton = new JButton("Pause");
        buttonPanel.add(pauseButton);
        JButton endButton = new JButton("End");
        buttonPanel.add(endButton);
        g.add(buttonPanel);
        
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ep) {
                count = 0;
                yearLabel.setText("Year " + Integer.toString(count));
                for (Tree t: arr) {
                        t.setExt((float)0.4);
                }
            }
        });
        
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ep) {
                run = false;
            }
        });
        
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ep) {
                run = true;
                //yearLabel.setText(Integer.toString(count));
            }
        });
        
        endButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ep) {
                System.exit(0);
            }
        });
        
      	frame.setLocationRelativeTo(null);  // Center window on screen.
      	frame.add(g); //add contents to window
        frame.setContentPane(g);     
        frame.setVisible(true);
        Thread fpt = new Thread(fp);
        fpt.start();
	}
    
    static final ForkJoinPool fjPool = new ForkJoinPool();
    static void simulate() { fjPool.invoke(new Grow(0, arr.length, arr, map, sequential_cutoff, interval)); }
    
	public static void main(String[] args) {
		SunData sundata = new SunData();
		
		// check that number of command line arguments is correct
		if(args.length != 1)
		{
			System.out.println("Incorrect number of command line arguments. Should have form: java treeGrow.java intputfilename");
			System.exit(0);
		}
				
		// read in forest and landscape information from file supplied as argument
		sundata.readData(args[0]);
		System.out.println("Data loaded");
        arr = sundata.trees;
        map = sundata.sunmap;
		
		frameX = sundata.sunmap.getDimX();
		frameY = sundata.sunmap.getDimY();
		setupGUI(frameX, frameY, arr);
		
		// create and start simulation loop here as separate thread
        // garbage collection
        //get active thread count
        
        count = 0;
        run = false;
        
        //tick();
        while (true) {
            if (run == true) {
                System.out.println("\nRun " + count);
                for (int i = 0; i < 20 ; i+=2) {
                    interval = i;
                    simulate();
                }
                fp.forest = arr;
                System.out.printf("%.6f", arr[0].getExt());
                map.resetShade();
                count++;
                yearLabel.setText("Year " + Integer.toString(count));
            }
            else {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                };
            }
        }
        //System.out.println(tock());
        
        /**
        tick();
        for (int s = 0; s < 100; s++) {
            System.out.println("\nRun " + s);
            for (int i = 0; i < 20 ; i+=2) {
                for (Tree t : arr)
                {
                    if (t.inrange(20-i-2, 20-i))
                    {
                        t.sungrow(map);
                        (map).shadow(t);
                    }
                    fp.forest = arr;
                }
            }
            map.resetShade();
        }
        System.out.println(tock());
        */
	}
}
