import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*Author:Nye Baker
 * This controller class has the functionality of threading added into it.
 * Modes 
 * 1: Varience Data
 * 2: correlation function data
 * 3: immune data
 * 4: Graphics run
 */
public class Controller 
{
	public static void main(String[] args)
	{
		//Enter all the parameters.
		Random rand = new Random();
		Scanner input = new Scanner(System.in);
		System.out.println("Modes\n1: Varience Data\n2: Correlation Function Data\n3: Immune Data\n4: Graphics Run");
		System.out.println("Enter the Mode Which you Wish to run in:");
		int runMode = input.nextInt();
		System.out.println("Enter the size of the system");
		int n = input.nextInt();
		int[][] SIRS_grid = new int[n][n];

		for(int j=0;j<n;j++)
		{
			for(int k=0;k<n;k++)
			{
				SIRS_grid[j][k] = rand.nextInt(3);
			}
		}

		System.out.println("Enter the Number of sweeps:");
		int iterations = input.nextInt() * n *n;

		BufferedImage bi = new BufferedImage(n, n, BufferedImage.TYPE_INT_RGB);
		int counter =0;
		if(runMode ==1)
		{
			grpahics g = new grpahics(SIRS_grid,bi,false);
			System.out.println("Enter the seperation of Probability:");
			double probStep = input.nextDouble();

			try {
				System.out.println("Enter The Data File Name:");
				BufferedWriter bw = new BufferedWriter(new FileWriter(input.next()));
				ExecutorService executor = Executors.newFixedThreadPool(10);
				//here will need to loop over all the prbailities...
				for(double i=0;i<1;i= i + probStep)
				{
					for(double j=0;j<1;j = j+ probStep)
					{
						//now add each of the algorithms to the threadpool
						executor.submit(new process(SIRS_grid, i, 0.5, j, iterations, bw, bi, g, counter, runMode,probStep));
						counter++;

					}
				}
				//shutdown the executor thread so no more threads are added to the pool
				executor.shutdown();

				//Now wait for all the tasks to finish

				executor.awaitTermination(1, TimeUnit.DAYS);
				System.out.println("All Tasks Finished");

				Functions.processData(bw,process.getResult(),probStep);

				bw.close();


			} 
			catch (IOException e) {e.printStackTrace();} catch (InterruptedException e) {e.printStackTrace();}
		}
		else if(runMode ==2)
		{
			grpahics g = new grpahics(SIRS_grid,bi,false);
			System.out.println("Enter The Data File Name:");
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(input.next()));
				
				System.out.println("Enter the probabilities (p1,p2,p3)");
				double p1 = input.nextDouble();
				double p2 = input.nextDouble();
				double p3 = input.nextDouble();
				
				algorithm.sirs(SIRS_grid, p1, p2, p3, iterations, false,bi,g,1,bw);
			} 
			catch (IOException e) {e.printStackTrace();}
			

		}
		else if(runMode ==3)
		{
			System.out.println("Enter the seperation of immunity:");
			double immuneStep = input.nextDouble();
			grpahics g = new grpahics(SIRS_grid,bi,false);
			System.out.println("Enter The Data File Name:");
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(input.next()));
				ExecutorService executor = Executors.newFixedThreadPool(2);  
				
				for(double i=0;i<1;i= i + immuneStep)
				{
					for(double j=0;j<1;j= j + immuneStep)
					{
						//i is immune fraction, j is probability.
						executor.submit(new process(SIRS_grid, 0.5, 0.5, j, iterations, bw, bi, g, counter, runMode,immuneStep,i));
						counter++;
					}
				}
				
				executor.shutdown();

				//Now wait for all the tasks to finish

				executor.awaitTermination(1, TimeUnit.DAYS);
				System.out.println("All Tasks Finished");

				Functions.processData(bw,process.getResult(),immuneStep);

				bw.close();
			} 
			catch (IOException e) {e.printStackTrace();} catch (InterruptedException e) {e.printStackTrace();}
		}
		else if(runMode ==4)
		{
			grpahics g = new grpahics(SIRS_grid,bi,true);
			try 
			{
				BufferedWriter bw = new BufferedWriter(new FileWriter("Dump"));
				System.out.println("Enter the probabilities (p1,p2,p3)");
				double p1 = input.nextDouble();
				double p2 = input.nextDouble();
				double p3 = input.nextDouble();
				algorithm.sirs(SIRS_grid, p1, p2, p3, iterations, true,bi,g,1,bw);
			} 
			catch (IOException e) {e.printStackTrace();}
		}

		System.exit(0);
		input.close();

	}

	/*
	 * Modes 
	 * 1: Varience Data
	 * 2: correlation function data
	 * 3: immune data
	 * 4: Graphics run
	 */

}
class process implements Runnable
{
	private volatile int counter,runMode,iterations;
	private volatile int[][] SIRS_grid;
	private volatile static double[][] result;
	private volatile double p1;
	private volatile double p2;
	private volatile double p3;
	private volatile BufferedWriter bw;
	private volatile BufferedImage bi;
	private volatile grpahics g;
	private volatile double[] varienceArray;
	private volatile Random rand = new Random();
	private volatile double immune;
	private volatile int randi=0,randj=0,n;


	//used two contructors as one is for the immune fraction and the other for the varience runs.
	public process(int[][] SIRS_grid, double p1,double p2,double p3, int iterations, BufferedWriter bw, BufferedImage bi, grpahics g,int counter,int runMode,double probStep)
	{
		//PERHAPS REDUCE THE NUMBER OF PARAMETERS PASSED IN
		this.SIRS_grid=SIRS_grid;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.iterations=iterations;
		this.bw = bw;
		this.bi=bi;
		this.g=g;
		this.counter = counter;
		this.runMode = runMode;
		result = new double[(int)((1.2/probStep)*(1/probStep))][3];
		this.n = SIRS_grid.length;

	}
	public process(int[][] SIRS_grid, double p1,double p2,double p3, int iterations, BufferedWriter bw, BufferedImage bi, grpahics g,int counter,int runMode,double probStep,double immune)
	{
		//PERHAPS REDUCE THE NUMBER OF PARAMETERS PASSED IN
		this.SIRS_grid=SIRS_grid;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.iterations=iterations;
		this.bw = bw;
		this.bi=bi;
		this.g=g;
		this.counter = counter;
		this.runMode = runMode;
		result = new double[(int)((1.2/probStep)*(1/probStep))][3];
		this.n = SIRS_grid.length;
		this.immune = immune; 

	}

	public void run() 
	{
		//Run just runs the program with the inputted runMode from the controlle class. 
		if(runMode==1)
		{
			try 
			{
				for(int k=0;k<SIRS_grid.length;k++)
				{
					for(int l=0;l<SIRS_grid.length;l++)
					{
						SIRS_grid[k][l] = rand.nextInt(3);
					}
				}
				varienceArray=algorithm.sirs(SIRS_grid, p1, p2, p3, iterations, false,bi,g,0,bw);
				result[counter][2] = Math.sqrt(Functions.standardDeviation(varienceArray, varienceArray.length));

			}
			catch (IOException e) {e.printStackTrace();}
		}

		if(runMode==3)
		{
			try 
			{
				//initalise the grid
				for(int k=0;k<SIRS_grid.length;k++)
				{
					for(int l=0;l<SIRS_grid.length;l++)
					{
						SIRS_grid[k][l] = rand.nextInt(3);
					}
				}
				for(int i=0;i<Math.floor(immune * n * n);i++)
				{
					randi = rand.nextInt(n);
					randj = rand.nextInt(n);
					SIRS_grid[randi][randj]=4;
				}
				result[counter][2] = Functions.average(algorithm.sirs(SIRS_grid, p1, p2, p3, iterations, false,bi,g,0,bw));

			}
			catch (IOException e) {e.printStackTrace();}
		}

	}
	public static double[][] getResult()
	{
		return result;
	}

}

