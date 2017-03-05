import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class SIRS
{
	public SIRS(int n,double p1,double p2,double p3) throws IOException
	{
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the size of the System:");
		//int n = input.nextInt();
		int[][] SIRS_grid = new int[n][n];
		System.out.println("Enter the probabilities (p1,p2,p3)");
		//double p1 = input.nextDouble();
		//double p2 = input.nextDouble();
		//double p3 = input.nextDouble();
		boolean graphics = false;
		int itertations =5000*n*n;
		double probstep =0;
		int noRuns =1;
		int runMode =0;
		Random rand = new Random();
		//Run mode describes which graphs to be outputted 0 = data run, 1 = live data run, 2 = grpahics run.

		System.out.println("Mode 0: Data Run\nMode 1: Live Data Run \nMode 2: Graphics Run");
		System.out.println("Enter the Mode which you wish to Run in:");
		runMode=input.nextInt();
		BufferedWriter bw;

		for(int j=0;j<n;j++)
		{
			for(int k=0;k<n;k++)
			{
				SIRS_grid[j][k] = rand.nextInt(3);
			}
		}
		
		System.out.println("Enter the name of the file which the output is being written to:");
		bw = new BufferedWriter(new FileWriter(input.next()));
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


		input.close();
		//pass in all the vairbales for the system


		BufferedImage bi = new BufferedImage(n, n, BufferedImage.TYPE_INT_RGB);
		grpahics g = new grpahics(SIRS_grid,bi,graphics);
		if(runMode ==0)
		{
			double[][] result = new double[(int)((1/probstep)*((1/probstep)+3))][3];
			for(int i=0;i<noRuns;i++)
			{

				double [][] varienceArray = new double[(int)((1/probstep)*((1/probstep)+3) + 1)][noRuns];
				for(double[] coloum :result)
					Arrays.fill(coloum, 0);
				int counter=0;

		
				for(double prob1=0;prob1<1;prob1=prob1+probstep)
				{
					for(double prob3 =0;prob3<1;prob3 = prob3+probstep)
					{
						varienceArray[counter]=algorithm.sirs(SIRS_grid, prob1, p2, prob3, itertations, graphics,bi,g,0,bw);
						result[counter][2] = Math.sqrt(Functions.standardDeviation(varienceArray[counter], varienceArray[counter].length));
						result[counter][1] = prob3;
						result[counter][0] = prob1;
						counter++;
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
		}

		if(runMode ==1)
		{
			algorithm.sirs(SIRS_grid, p1, p2, p3, itertations, graphics,bi,g,1,bw);
		}


		bw.close();
		System.exit(0);
	}


}

