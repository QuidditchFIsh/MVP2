
public class testing 
{
	public static void main(String[] args)
	{
		double[][] results = new double[120][3];
		for(double[] i:results)
			for(double j:i)
			{
				j=0;
			}
		double probSpace =0.1;
		double prob1=0,prob2=0;
		for(int j=0;j<results.length;j++)
		{
			if(prob2 > 1)
			{
				prob2=0;
				prob1 += probSpace;
			}
			results[j][0] = prob1;
			results[j][1] = prob2;
			prob2 += probSpace;
			
			
		}
		
		for(double[] i: results)
		{
			for(double j : i)
			{
				System.out.print(j+" ");
			}
			System.out.println();
		}
			
	}
}
