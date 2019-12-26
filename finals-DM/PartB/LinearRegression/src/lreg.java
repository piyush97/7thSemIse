//y=Mx+b;
import java.util.*;

class lreg
{
	public static void main(String args[]) {
		double M=-1000;
		double B=-1000;
		//y=5x+2
		double mean_x=0,mean_y=0,cov_xy=0,var_x=0;
		double[] x = { 1, 2, 3, 4, 5, 6, 7, 8 };
		double[] y = { 7,12,17,22,27,32,37,42};
		for(int i=0;i<x.length;i++)
		{
			mean_x = mean_x+x[i];
			mean_y = mean_y+y[i];
		}
		mean_x = mean_x/x.length;
		mean_y = mean_y/x.length;
		for(int i=0;i<x.length;i++)
		{
			var_x += Math.pow(x[i] - mean_x, 2);
			cov_xy += (x[i] - mean_x) * (y[i] - mean_y);
		}
		var_x = var_x/x.length;
		cov_xy = cov_xy/x.length;
		M = cov_xy/var_x;
		B = mean_y-M*mean_x;
		System.out.println("enter the value for x");
		Scanner in = new Scanner(System.in);
		double r = in.nextInt();
		double res = r*M+B;
		System.out.println("Predicted output for "+r+" : "+res);
		in.close();
	} 
}