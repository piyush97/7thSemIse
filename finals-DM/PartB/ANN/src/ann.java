import java.util.*; 
import java.io.*; 
class temp{
	double x,y,z;
}
class ann
{
	public static void main(String args[]) throws FileNotFoundException, IOException
	{
		BufferedReader csv = new BufferedReader(new FileReader(new File("/Users/hemantj/Documents/scratch/DM-Lab/Part-B/ANN/src/input.csv")));
		String data = csv.readLine();
		Random rand = new Random();
		ArrayList<temp> al = new ArrayList<>();
		double[] weight = new double[3];
		double rate = 0.02;
		while(data != null)
		{
		     String[] dataarray = data.split(",");
		     temp v = new temp();
		     v.x = Double.parseDouble(dataarray[0]);
		     v.y = Double.parseDouble(dataarray[1]);
		     v.z = Double.parseDouble(dataarray[2]);
		     al.add(v);
		     data = csv.readLine();
		}
		weight[0] = rand.nextInt(32767*2)-32767;
		weight[1] = rand.nextInt(32767*2)-32767;
		for(int i =0;i<10000;i++)
		{
		     for(int j=0;j<al.size()-1;j++)
		     {
		           double sum = weight[0]+weight[1]*(al.get(j)).x+weight[2]*(al.get(j)).y; 
		           double error = (al.get(j)).z-sum;
		           weight[0]=weight[0]+error*rate;
		           weight[1]=weight[1]+(rate*error*(al.get(j)).x);
		           weight[2]=weight[2]+(rate*error*(al.get(j)).y); 
		     }
		}
		System.out.println("enter x,y test data"); 
		Scanner in = new Scanner(System.in);
		double p = in.nextDouble();
		double q = in.nextDouble();
		double sum1 = weight[0]+weight[1]*p+weight[2]*q; 
		System.out.println("The predicted value is: " + sum1);
		System.out.println("final weight[0] :"+weight[0]+"\nfinal weight[1] :"+weight[1]+"\n final weight[2] :"+weight[2]);
	}
}