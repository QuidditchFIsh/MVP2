import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;

public class algorithm extends Functions
{
	@SuppressWarnings("static-access")
	public synchronized static double[] sirs(int[][] grid,double p1, double p2, double p3,int iterations,boolean graphic,BufferedImage bi,grpahics g,int output,BufferedWriter bw) throws IOException
	{
		int counter1=0;
		int n = grid.length;
		Random rand = new Random();
		int randi,randj;
		int counter=0;
		double[] avgOrder=new double[(int)(iterations/(n*n*10))];
		double temp =0;
		for(int i=0;i<iterations;i++)
		{
			randi=rand.nextInt(n);
			randj=rand.nextInt(n);

			if(grid[randi][randj]==0)
			{
				if(checkNeighbors(grid,randi,randj))
				{
					if(p1 > rand.nextDouble())
					{
						grid[randi][randj] = (grid[randi][randj] + 1) % 3;
					}
				}
			}
			else if(grid[randi][randj]==1)
			{
				if(p2 > rand.nextDouble())
				{
					grid[randi][randj] = (grid[randi][randj] + 1) % 3;
				}
			}
			else if(grid[randi][randj]==2)
			{
				if(p3 > rand.nextDouble())
				{
					grid[randi][randj] = (grid[randi][randj] + 1) % 3;
				}
			}
			if(graphic)
			{
				if(i % g.slider.getValue() == 0)
				{
					
					grpahics.update(grid, bi);
				}
			}
			if(!graphic)
				if(i/(n*n)>10)
				{
					if(i % (n*n*10) == 0)
					{
						temp = orderParam(grid);
						avgOrder[counter]=temp;
						counter++;

					}
					if(output == 1 && i %(n*n)==0)
					{
						writeData(bw,orderParam(grid));
					}


				}
		}
		return avgOrder;
	}
	

}
