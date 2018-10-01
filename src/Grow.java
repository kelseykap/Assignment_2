import java.io.*;
import java.util.concurrent.RecursiveAction;

public class Grow extends RecursiveAction  {
    
    int lo;
    int hi;
    Tree[] arr;
    Land land;
    int seq_cutoff;
    int i;
    
    /**
     Constructor method for the class
     
     @param lo
     @param hi
     @param arr
     @param land
     @param seq_cutoff
     @param i
     */
    Grow(int lo, int hi, Tree[] arr, Land land, int seq_cutoff, int i)
    {
        this.lo=lo; this.hi=hi; this.arr=arr; this.land=land; this.seq_cutoff=seq_cutoff; this.i=i;
    }
    
    protected void compute()
    {
        if( (hi-lo) < seq_cutoff) {
            for (int j = lo; j < hi; j++) {
                if (arr[j].inrange(20-i-2, 20-i)) {
                    land.shadow(arr[j]);
                }
            }
        }
        else
        {
            Grow left = new Grow(lo, (hi+lo)/2, arr, land, seq_cutoff, i);
            Grow right = new Grow((hi+lo)/2, hi, arr, land, seq_cutoff, i);
            
            left.fork();
            right.compute();
            left.join();
        }
    }
}
