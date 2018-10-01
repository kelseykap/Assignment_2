import java.io.*;
import java.util.concurrent.RecursiveAction;

public class Reset extends RecursiveAction  {
    
    int lo;
    int hi;
    Tree[] arr;
    int seq_cutoff;
    
    /**
     Constructor method for the class
     
     @param lo takes in the lower bound
     @param hi takes in the upper bound
     @param arr takes in the array of Tree objects
     @param seq_cutoff specifies the cut-off
     */
    Reset(int lo, int hi, Tree[] arr, int seq_cutoff)
    {
        this.lo=lo; this.hi=hi; this.arr = arr; this.seq_cutoff = seq_cutoff;
    }
    
    /**
     Method that recursuvely runs through the Tree array, and creates new threads
     */
    protected void compute()
    {
        if( (hi-lo) < seq_cutoff)
        {
            for (int j = lo; j < hi; j++) {
                arr[j].setExt((float)0.4);
            }
        }
        else
        {
            Reset left = new Reset(lo, (hi+lo)/2, arr, seq_cutoff);
            Reset right = new Reset((hi+lo)/2, hi, arr, seq_cutoff);
            
            left.fork();
            right.compute();
            left.join();
        }
    }
}
