import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;

public class Functions 
{
	public static boolean checkNeighbors(int[][] grid,int i,int j)
	{
		int n = grid.length;
		if(grid[Math.floorMod(i+1, n)][j]==1 || grid[Math.floorMod(i-1, n)][j]==1 || grid[i][Math.floorMod(j-1, n)]==1 || grid[i][Math.floorMod(j+1, n)]==1)
		{
			return true;
		}
		return false;
	}
	
	public static double orderParam(int[][] grid)
	{
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
		//System.out.println(noInfect/(n*n));
		return noInfect/(n*n);
	}
	public static void processData(BufferedWriter bw,double[][] results,double probSpace) throws IOException
	{
		int counter=0;
		for(int i=0;i<results.length;i++)
		{
			
			for(int j=0;j<results[0].length;j++)
			{
				bw.write(String.valueOf(results[i][j] + " "));
			}
			bw.newLine();
			counter++;
			if(counter == (int) 1/(probSpace))
			{
				counter=0;
				bw.newLine();
			}
		}
		//Print out everything to the file using buffered writer!
	}
	
	public static void writeData(BufferedWriter bw,double result) throws IOException
	{
		bw.write(String.valueOf(result));
		bw.newLine();
	}
	public static double bootStrap(double[] sample)
	{
		//Hoepfully this is the correct bootstrap algorithm 
		//Will go through it when i get home!!!!
		Random rand = new Random();
		int m =   sample.length;
		double[] bootSample = new double[m];
		double[] sampleVar = new double[m];
		//not sure if k here is meant to me less than m or not?????
		for(int j=0;j<m;j++)
		{
			for(int i=0;i<sample.length;i++)
			{
				bootSample[i]= sample[rand.nextInt(m)];
			}
			sampleVar[j] = standardDeviation(bootSample,bootSample.length);
			//need to add in the temp and other terms to this calculation!!!!!
		}
		return Math.sqrt(standardDeviation(sampleVar,sampleVar.length));



	}
	public synchronized static double standardDeviation(double[] mag,double sweeps)
	{
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
		int n = avgData.length;
		double sum=0;
		for(int i=0;i<n;i++)
		{
			sum +=avgData[i];
		}
		return sum/((double) n );
	}
}
