import java.util.concurrent.ForkJoinPool;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.Timer;
import java.util.concurrent.atomic.AtomicLong;

public class View {
	
	int frameX;
	int frameY;
	ForestPanel fp;
    Tree[] arr;

    private JButton resetButton;
    private JButton pauseButton;
    private JButton playButton;
    private JButton endButton;
    
    boolean run;
    boolean reset;
    //AtomicLong year;
    int year;
    
    JLabel yearLabel = new JLabel("Year 0");
    
	public View(int frameX, int frameY, Tree [] trees) {
		
        //year = new AtomicLong(0);
        year = 0;
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
    
    /**public long get() {
        return year.get();
    }*/
    
    /**public void set(long val) {
        year.set(val);
    }*/
    
    /**public void incr() {
        System.out.println(year.toString());
        year.getAndIncrement();
    }*/

    
    public void reset() {
        year = 0;
        yearLabel.setText("Year " + Integer.toString(year));
        //yearLabel.setText("Year " + year.toString());
    }
    
    public void yearIncrease() {
        year++;
        //System.out.println(Integer.toString(year));
        yearLabel.setText("Year " + Integer.toString(year));
    }
    
}
