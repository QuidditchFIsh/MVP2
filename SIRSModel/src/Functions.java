import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;

/*
 * Author: Nye Baker
 * A class where all mathematical function/Calculations are held.Or any type of function which is not realted to any class in particular.
 */

public class Functions 
{
	public static boolean checkNeighbors(int[][] grid,int i,int j)
	{
		//Method to check weather any of the neighbors of a cell is infected.
		int n = grid.length;
		if(grid[Math.floorMod(i+1, n)][j]==1 || grid[Math.floorMod(i-1, n)][j]==1 || grid[i][Math.floorMod(j-1, n)]==1 || grid[i][Math.floorMod(j+1, n)]==1)
		{
			return true;
		}
		return false;
	}
	
	public static double orderParam(int[][] grid)
	{
		//Method to claculate the order paramter. Will loop over all the array and count the number of infected cells. 
		int n= grid.length;
		double noInfect=0;
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				if(grid[i][j]==1)
				{
					noInfect++;
				}
			}
		}
		return noInfect/(n*n);
	}
	public static void processData(BufferedWriter bw,double[][] results,double probSpace) throws IOException
	{
		//Method to write the out the data to a specified file. Also the x and y values for the graph are written to
		//the file here as it does not work when writing out them in the thread. 
		int counter=0;
		double prob1=0,prob2=0;
		for(int j=0;j<results.length;j++)
		{
			if(prob2 > 1)
			{
				prob2=0;
				prob1 += probSpace;
			}
			results[j][0] = prob1;
			results[j][1] = prob2;
			prob2 += probSpace;
			
			
		}
		
		for(int i=0;i<results.length;i++)
		{
			
			for(int j=0;j<results[0].length;j++)
			{
				bw.write(String.valueOf(results[i][j] + " "));
			}
			bw.newLine();
			counter++;
			if(counter == (int) 1/(probSpace) +1)
			{
				//New line for pm3d 
				counter=0;
				bw.newLine();
			}
		}
		//Print out everything to the file using buffered writer!
	}
	
	public static void writeData(BufferedWriter bw,double result) throws IOException
	{
		// simple write data for when doing 2d parameter graphs. 
		bw.write(String.valueOf(result));
		bw.newLine();
	}

	public synchronized static double standardDeviation(double[] mag,double sweeps)
	{
		//method to calculate the standard deviation of a set of variables which are passed in. 
		double mag1 =0,magSqd =0;

		for(int i=0;i<mag.length;i++)
		{
			mag1 += mag[i];
			magSqd+= mag[i] * mag[i];
		}
		magSqd /= sweeps;
		mag1 /= sweeps;
		return (magSqd - (mag1*mag1));
	}
	
	public static double average(double[] avgData)
	{
		//method to calcuate the average of a data set. 
		int n = avgData.length;
		double sum=0;
		for(int i=0;i<n;i++)
		{
			sum +=avgData[i];
		}
		return sum/((double) n );
	}
}
