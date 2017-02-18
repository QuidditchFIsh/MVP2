import java.io.BufferedWriter;
import java.io.IOException;

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
	public static void processData(BufferedWriter bw,double[][] results) throws IOException
	{
		for(int i=0;i<results.length;i++)
		{
			for(int j=0;j<results[0].length;j++)
			{
				bw.write(String.valueOf(results[i][j] + " "));
			}
			bw.newLine();
		}
		//Print out everything to the file using buffered writer!
	}
}
