import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;

public class SunData{
	
	Land sunmap;
	Tree [] trees;
	
    /**
     Method to read in data from a specified file
     
     @param fileName takes in a String
     */
	void readData(String fileName){ 
		try{ 
			Scanner sc = new Scanner(new File(fileName));
			
			int dimx = sc.nextInt(); 
			int dimy = sc.nextInt();
			sunmap = new Land(dimx,dimy);
			for(int x = 0; x < dimx; x++)
				for(int y = 0; y < dimy; y++) {
					sunmap.setFull(x,y,sc.nextFloat());
				}
			sunmap.resetShade();
			
			int numt = sc.nextInt();
			trees = new Tree[numt];
			for(int t=0; t < numt; t++)
			{
				int xloc = sc.nextInt();
				int yloc = sc.nextInt();
				float ext = (float) sc.nextInt();
				trees[t] = new Tree(xloc, yloc, ext);
			}
			sc.close(); 
		} 
		catch (IOException e){ 
			System.out.println("Unable to open input file "+fileName);
			e.printStackTrace();
		}
		catch (java.util.InputMismatchException e){ 
			System.out.println("Malformed input file "+fileName);
			e.printStackTrace();
		}
	}
	
    /**
     Method to write data to a specified file
     
     @param fileName takes in a String
     */
	void writeData(String fileName){
		 try{ 
			 FileWriter fileWriter = new FileWriter(fileName);
			 PrintWriter printWriter = new PrintWriter(fileWriter);
			 printWriter.printf("%d\n", trees.length);
			 for(int t=0; t < trees.length; t++)
				 printWriter.printf("%d %d %f\n", trees[t].getX(), trees[t].getY(), trees[t].getExt());
			 printWriter.close();
		 }
		 catch (IOException e){
			 System.out.println("Unable to open output file "+fileName);
				e.printStackTrace();
		 }
	}
	
}
