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
		boolean graphics = Boolean.parseBoolean(args[4]);
		int itertations =20000*n*n;
		Scanner input = new Scanner(System.in);
		if(!graphics)
		{
			System.out.println("Enter the number of sweeps to take data for");
			itertations =input.nextInt()*n*n;
	
		}
		System.out.println("Enter the name of the file which the output is being written to:");
		BufferedWriter bw = new BufferedWriter(new FileWriter(input.next()));
		System.out.println("Enter the seperation of probability");
		double probstep = input.nextDouble();
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
		if(graphics)
		{
		BufferedImage bi = new BufferedImage(n, n, BufferedImage.TYPE_INT_RGB);
		grpahics g = new grpahics(SIRS_grid,bi);
		algorithm.sirs(SIRS_grid, p1, p2, p3, n, itertations, graphics,bi,g);
		}
		else
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

					result[counter][2]=algorithm.sirs(SIRS_grid, prob1, p2, prob3, n, itertations, graphics,bi,g);
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
			System.out.println("foo");
			Functions.processData(bw,result);
			bw.close();
			System.out.println("poo");
		}	
		System.exit(0);

	}
}
