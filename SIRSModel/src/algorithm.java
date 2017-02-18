import java.awt.image.BufferedImage;
import java.util.Random;

public class algorithm extends Functions
{
	@SuppressWarnings("static-access")
	public static double sirs(int[][] grid,double p1, double p2, double p3,int n,int iterations,boolean graphic,BufferedImage bi,grpahics g)
	{
		Random rand = new Random();
		int randi,randj;
		int counter=0;
		double avgOrder=0;
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
				if(i % (g.getSliderValue()) == 0)
				{
					grpahics.update(grid, bi);
				}
			if(!graphic)
				if(i/(n*n)>10)
					if(i % (n*n*10) == 0)
					{
						avgOrder+=orderParam(grid);
						counter++;
						//System.out.println(avgOrder);
						//System.out.println(avgOrder);
					}
		}
		//System.out.println(avgOrder);
		return avgOrder/counter;
	}
}
