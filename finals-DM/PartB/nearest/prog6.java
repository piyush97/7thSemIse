import java.io.*;
import java.util.*;
class comp{
	double a;
	String b;
	int x,y,z;
	public comp(double p,String q,int r,int s,int t){
		a=p;b=q;x=r;y=s;z=t;
	}
}
class prog6
{
	public static void main(String args[]) throws FileNotFoundException, IOException
	{
		BufferedReader csv = new BufferedReader(new FileReader(new File("input.csv")));
		String data = csv.readLine();
		int a[] = new int[3];
		int b[] = new int[3];
		Scanner in = new Scanner(System.in);
		System.out.println("Enter x,y,z to classify in Class A,B,C");
		a[0] = in.nextInt();
		a[1] = in.nextInt();
		a[2] = in.nextInt();
		System.out.println("enter number of nearest neighbours");
		int k = in.nextInt();
		int i;
		ArrayList<comp> al = new ArrayList<>();
		while(data != null)
		{
			String[] dataarray = data.split(",");
			int sum=0;
			for(i=0;i<3;i++)
				b[i] = Integer.parseInt(dataarray[i]);
			sum = (a[0]-b[0])*(a[0]-b[0]) + (a[1]-b[1])*(a[1]-b[1]) + (a[2]-b[2])*(a[2]-b[2]);
			double res = Math.sqrt(sum);
			comp temp = new comp(res,dataarray[3],b[0],b[1],b[2]);
			al.add(temp);
			data = csv.readLine();
		}
		Collections.sort(al, new Comparator<comp>() {
		@Override public int compare(comp p1, comp p2) {
		if(p1.a == p2.a)
			return 0;
		else if(p1.a > p2.a)
			return 1;
		else
			return -1;
		} });
		int x[] = new int[3];
		System.out.println(k+" -nearest neighbour are:");
		for(comp temp:al)
		{
			if(k==0)
				break;
			System.out.println(temp.x+" "+temp.y+" "+temp.z+" "+temp.b);
			k--;
			String c = temp.b;
			if(c.equals("A"))
				x[0]++;
			else if(c.equals("B"))
				x[1]++;
			else
				x[2]++;
		}
		char res;
		int r = Math.max(x[0],Math.max(x[1],x[2]));
		if(x[0] == r)
			res = 'A';
		else if(x[1] == r)
			res = 'B';
		else
			res = 'C';
		System.out.println("classifer for "+a[0]+" "+a[1]+" "+a[2]+" is :"+res);
		csv.close();
		in.close();
	}
}