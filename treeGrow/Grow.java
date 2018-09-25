package treeGrow;

import java.io.*;
import java.util.concurrent.RecursiveAction;

public class Grow extends RecursiveAction  {
    
    int lo;
    int hi;
    Tree[] arr;
    Land land;
    int seq_cutoff;
    int i;
    
    Grow(int lo, int hi, Tree[] arr, Land land, int seq_cutoff, int i)
    {
        this.lo=lo; this.hi=hi; this.arr = arr; this.land = land; this.seq_cutoff = seq_cutoff; this.i = i;
    }
    
    protected void compute()
    {
        if( (hi-lo) < seq_cutoff)
        {
            for (int j = lo; j < hi; j++) {
                Tree t = arr[j];
                if (t.inrange(20-i-2, 20-i))
                {
                    t.sungrow(land);
                    (land).shadow(t);
                }
            }
        }
        else
        {
            Grow left = new Grow(lo, (hi+lo)/2, arr, land, seq_cutoff, i);
            Grow right = new Grow((hi+lo)/2, hi, arr, land, seq_cutoff, i);
            
            left.fork();
            //left.compute();
            right.compute();
            left.join();
        }
    }
}
