import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;
/*
 * Author : Nye Baker
 * Main Algorithm for this simulation. 
 */
public class algorithm extends Functions
{
	@SuppressWarnings("static-access")
	public synchronized static double[] sirs(int[][] grid,double p1, double p2, double p3,int iterations,boolean graphic,BufferedImage bi,grpahics g,int output,BufferedWriter bw) throws IOException
	{
		//Initalise paraters needed for the main Algorithm
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
			//Select a random cell to update. 
			if((grid[randi][randj]==4))
			{
				//If the cell is immune to the infection Do nothing. 
			}
			//Using Else if to make sure that we only perform one action on a cell every time.
			else if(grid[randi][randj]==0)
			{
				if(checkNeighbors(grid,randi,randj))
				{
					if(p1 > rand.nextDouble())
					{
						grid[randi][randj] = (grid[randi][randj] + 1) % 3;
						//Mod 3 update as eaiser way to update. May want to be careful when adding other cells into the scheme.
						//e.g. adding immune cells.
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
				// Slider controls the speed of updateing the graphics. 
				{
					grpahics.update(grid, bi);
				}
			}
			if(!graphic)
				if(i/(n*n)>10)
				{
					//only start taking data after the first 10 sweeps to avoid any noise from initalisation. 
					if(i % (n*n*10) == 0)
					{
						//Now take data every 10 sweeps to avoid any correlated data points. 
						temp = orderParam(grid);
						avgOrder[counter]=temp;
						counter++;

					}
					if(output == 1 && i %(n*n)==0)
					{
						//this IF statement is for when i want to output data for the order paramter graphs. 
						writeData(bw,orderParam(grid));
					}


				}
		}
		return avgOrder;
	}


}
