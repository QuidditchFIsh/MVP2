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
		int n = Integer.parseInt(args[0]);
		int[][] SIRS_grid = new int[n][n];
		double p1 = Double.parseDouble(args[1]);
		double p2 = Double.parseDouble(args[2]);
		double p3 = Double.parseDouble(args[3]);
		int runType = Integer.parseInt(args[4]);
		//0 = graphics run, 1= run over all prbabilities,2= run over 1 set of probs
		boolean graphics = false;
		int itertations =20000*n*n;
		double probstep =0;
		int noRuns =1;
		Scanner input = new Scanner(System.in);
		if(runType == 1)
		{
			System.out.println("Enter the number of sweeps to take data for");
			itertations =input.nextInt()*n*n;

			System.out.println("Enter the seperation of probability");
			probstep = input.nextDouble();

		}
		if(runType == 2)
		{
			System.out.println("Enter the number of sweeps to take data for");
			itertations =input.nextInt()*n*n;
			
			System.out.println("Enter the number of runs to do");
			noRuns = input.nextInt();
			
		}
		System.out.println("Enter the name of the file which the output is being written to:");
		BufferedWriter bw = new BufferedWriter(new FileWriter(input.next()));

		input.close();
		//pass in all the vairbales for the system
		Random rand = new Random();

		//		for(int[] row : SIRS_grid)
		//			for(int coloum:row)
		//				coloum = rand.nextInt(3);

		for(int j=0;j<n;j++)
		{
			for(int k=0;k<n;k++)
			{
				SIRS_grid[j][k] = rand.nextInt(3);
			}
		}
		if(runType ==0)
		{
			BufferedImage bi = new BufferedImage(n, n, BufferedImage.TYPE_INT_RGB);
			grpahics g = new grpahics(SIRS_grid,bi);
			algorithm.sirs(SIRS_grid, p1, p2, p3, n, itertations, graphics,bi,g);
		}
		else if(runType ==1)
		{
			double[][] result = new double[200][3];
			//SOLVE THIS ISSUE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ^^
			for(double[] coloum :result)
				Arrays.fill(coloum, 0);
			int counter=0;
			BufferedImage bi = new BufferedImage(n, n, BufferedImage.TYPE_INT_RGB);
			grpahics g = new grpahics(SIRS_grid,bi);		
			for(double prob1=0;prob1<1;prob1=prob1+probstep)
			{
				for(double prob3 =0;prob3<1;prob3 = prob3+probstep)
				{
					
					result[counter][2]=algorithm.sirs(SIRS_grid, prob1, p2, prob3, itertations, graphics,bi,g);
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

			Functions.processData(bw,result);
			bw.close();
		}
		else if(runType ==2)
		{
			//Here i will take the data for only 1 run
			double[][][] result = new double[noRuns][itertations/(n*n)][2];
			for(int i=0;i<noRuns;i++)
			{
				
			}
			

		}
		System.exit(0);

	}
}
