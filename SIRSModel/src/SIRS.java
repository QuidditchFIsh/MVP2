/*
 *Author: Nye Baker
 *A simple simulation to modle the spread of infections. Not threaded.
 */
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class SIRS
{
	public static void main(String[] args) throws IOException
	{
		//User enters all of the parameters.
		
		//Run mode describes which graphs to be outputted 0 = data run, 1 = live data run, 2 = grpahics run.
		Scanner input = new Scanner(System.in);
		System.out.println("Mode 0: Data Run\nMode 1: Live Data Run \nMode 2: Graphics Run\nMode 3: Immune Mode");
		System.out.println("Enter the Mode which you wish to Run in:");
		int runMode=input.nextInt();
		System.out.println("Enter the size of the System:");
		int n = input.nextInt();
		int[][] SIRS_grid = new int[n][n];
		System.out.println("Enter the probabilities (p1,p2,p3)");
		double p1 = input.nextDouble();
		double p2 = input.nextDouble();
		double p3 = input.nextDouble();
		boolean graphics = false;
		int itertations =5000*n*n;
		double probstep =0;
		double immuneStep =0;

		Random rand = new Random();

		BufferedWriter bw;

		for(int j=0;j<n;j++)
		{
			for(int k=0;k<n;k++)
			{
				SIRS_grid[j][k] = rand.nextInt(3);
			}
		}

		if(runMode == 2)
		{
			bw = new BufferedWriter(new FileWriter("Dump"));
			//Dump is just a general file where the data from the grpahics run goes as its useless
		}
		else
		{
			System.out.println("Enter the name of the file which the output is being written to:");
			bw = new BufferedWriter(new FileWriter(input.next()));
		}
		if(runMode == 0)
		{
			System.out.println("Enter the number of sweeps to take data for");
			itertations =input.nextInt()*n*n;

			System.out.println("Enter the seperation of probability");
			probstep = input.nextDouble();

			graphics = false;

		}
		else if(runMode ==1)
		{
			graphics = false;
			System.out.println("Enter the number of sweeps to take data for");
			itertations =input.nextInt()*n*n;
		}
		else if(runMode ==2)
		{
			graphics = true;
			BufferedImage bi = new BufferedImage(n, n, BufferedImage.TYPE_INT_RGB);
			grpahics g = new grpahics(SIRS_grid,bi,graphics);
			algorithm.sirs(SIRS_grid, p1, p2, p3, itertations, graphics,bi,g,1,bw);
		}
		else if(runMode == 3)
		{
			graphics = false;
			System.out.println("Enter the number of sweeps to take data for");
			itertations =input.nextInt()*n*n;
			System.out.println("Enter the immune Step");
			immuneStep = input.nextDouble();
			System.out.println("Enter the probability Step");
			probstep = input.nextDouble();
		}


		input.close();


		BufferedImage bi = new BufferedImage(n, n, BufferedImage.TYPE_INT_RGB);
		grpahics g = new grpahics(SIRS_grid,bi,graphics);
		//g is an object where which handles all the graphical and GUI elements of the program
		if(runMode ==0)
		{
			double[][] result = new double[(int)((1.2/probstep)*((1.2/probstep)))][3];
			//Here 1.2 is used as to make sure that the results array is large enough. The zeros in 
			//The array can be removed when the data is plotted

			double [][] varienceArray = new double[(int)((1/probstep)*((1/probstep)+3) + 1)][1];
			for(double[] coloum :result)
				Arrays.fill(coloum, 0);
			int counter=0;


			for(double prob1=0;prob1<1;prob1=prob1+probstep)
			{
				for(double prob3 =0;prob3<1;prob3 = prob3+probstep)
				{
					varienceArray[counter]=algorithm.sirs(SIRS_grid, prob1, p2, prob3, itertations, graphics,bi,g,0,bw);
					//Here i may be able to reduce the amount of memory accesses somehow as the varience array is never really used apart from a place holder.
					result[counter][2] = Math.sqrt(Functions.standardDeviation(varienceArray[counter], varienceArray[counter].length));
					result[counter][1] = prob3;
					result[counter][0] = prob1;
					counter++;
					//To keep track of where the simulation is in its run
					System.out.println(prob1 + " " + prob3);
					for(int j=0;j<n;j++)
					{
						for(int k=0;k<n;k++)
						{
							SIRS_grid[j][k] = rand.nextInt(3);
						}
					}
				}
			}
			Functions.processData(bw,result,probstep);
		}

		else if(runMode ==1)
		{
			algorithm.sirs(SIRS_grid, p1, p2, p3, itertations, graphics,bi,g,1,bw);
		}
		else if(runMode ==3)
		{
			//Immune run so introduce another state that the cell can be in here 4.
			int counter =0;
			double[][] result = new double[(int)((1.2/immuneStep)*((1.2/probstep)))][3];
			for(double[] coloum :result)
				Arrays.fill(coloum, 0);
			int randi=0,randj=0;
			for(double immune =0;immune < 1;immune= immune + immuneStep)
			{
				for(double i=0;i<1;i = i + probstep)
				{


					for(int j=0;j<n;j++)
					{
						for(int k=0;k<n;k++)
						{
							SIRS_grid[j][k] = rand.nextInt(3);
						}
					}


					for(int k=0;k<Math.floor(immune * (n*n));k++)
					{
						randi = rand.nextInt(n);
						randj = rand.nextInt(n);
						SIRS_grid[randi][randj] = 4;
						//Initalise the fraction of the cells to 4 randomly.
					}
					 
					result[counter][2] =Functions.average(algorithm.sirs(SIRS_grid, i, 0.5, 0.5, itertations, graphics,bi,g,0,bw));
					result[counter][0] = immune;
					result[counter][1] = i;
					counter++;
					System.out.println(i + " " + immune);
				}

			}
			Functions.processData(bw,result,immuneStep);
		}


		bw.close();
		System.exit(0);
	}


}

