import java.util.concurrent.ForkJoinPool;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.Timer;

public class View {
	
	static int frameX;
	static int frameY;
	static ForestPanel fp;

    private static JButton resetButton;
    private static JButton pauseButton;
    private static JButton playButton;
    private static JButton endButton;
    
    static JLabel yearLabel = new JLabel("Year 0");
    
    static Tree[] arr;
    
    static boolean run;
    static boolean reset;
    
	public View(int frameX, int frameY, Tree [] trees) {
		
        run = false;
        
        Dimension fsize = new Dimension(800, 800);
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
                reset = true;
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
            }
        });
        
        endButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ep) {
                System.exit(0);
            }
        });
        
      	frame.setLocationRelativeTo(null);
      	frame.add(g);
        frame.setContentPane(g);     
        frame.setVisible(true);
        Thread fpt = new Thread(fp);
        fpt.start();
	}
}
