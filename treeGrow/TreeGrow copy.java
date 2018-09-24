package treeGrow;

import java.io.*;
import java.util.concurrent.RecursiveTask;

public class Parallel extends RecursiveTask<Double>  {
    
    int lo;
    int hi;
    Tree[] arr;
    int sequential_cutoff;
    float[][] sunArray;
    
    Parallel(int lo, int hi, Tree[] arr, float[][] sunArray, int sequential_cutoff)
    {
        this.lo=lo;
        this.hi=hi;
        this.arr = arr;
        this.sunArray = sunArray;
        this.sequential_cutoff = sequential_cutoff;
    }
    
    protected Double compute()
    {
        if( (hi-lo) < sequential_cutoff)
        {
            double sum = calculate(lo, hi);
            return sum;
        }
        else
        {
            Parallel left = new Parallel(lo, (hi+lo)/2, arr, sunArray, sequential_cutoff);
            Parallel right = new Parallel((hi+lo)/2, hi, arr, sunArray, sequential_cutoff);
            
            left.fork();
            double rightAns = right.compute();
            double leftAns  = left.join();
            return leftAns + rightAns;
        }
    }
    
    public double calculate(int l, int h)
    {
        double total = 0;
        double sum = 0;
        for(int i = l; i < h; i++)
        {
            int x = (arr[i]).getX();
            int y = (arr[i]).getY();
            int extent = (arr[i]).getExtent();
            
            for (int j = 0; j < extent; j++)
            {
                for (int k = 0; k < extent; k++)
                {
                    try{
                        total += sunArray[x+j][y+k];
                    } catch (ArrayIndexOutOfBoundsException e) {};
                }
            }
            (arr[i]).setExposure(total);
            sum = sum + total;
            total = 0;
        }
        return sum;
    }
}
