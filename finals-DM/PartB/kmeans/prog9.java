import java.util.*; 
import java.io.*; 
class record
{
	double attr1=0,attr2=0; 
}
class prog9
{
	public static void main(String args[]) throws FileNotFoundException, IOException
	{
		BufferedReader csv = new BufferedReader(new FileReader(new File("input.csv")));
		String data = csv.readLine();
		ArrayList<record> al = new ArrayList<>();
		while(data != null)
		{
			String[] dataarray = data.split(",");
			record rc = new record();
			rc.attr1 = Double.parseDouble(dataarray[0]); 
			rc.attr2 = Double.parseDouble(dataarray[1]); 
			al.add(rc);
			data = csv.readLine();
		}
		ArrayList<record> centroid = new ArrayList<>(); 
		centroid.add(al.get(0));
		centroid.add(al.get(3)); 
		centroid.add(al.get(6));
		ArrayList<record> c1 = new ArrayList<>(); 
		ArrayList<record> c2 = new ArrayList<>(); 
		ArrayList<record> c3 = new ArrayList<>(); 
		for(int i=0;i<100;i++)
		{
           c1.clear();c2.clear();c3.clear();
           for(record r:al)
           {
                ArrayList<Double> t = new ArrayList<>();
				double p = dist(r.attr1,r.attr2,(centroid.get(0)).attr1,(centroid.get(0)).attr2 );
				double q = dist(r.attr1,r.attr2,(centroid.get(1)).attr1,(centroid.get(1)).attr2 );
				double s = dist(r.attr1,r.attr2,(centroid.get(2)).attr1,(centroid.get(2)).attr2 );
                t.add(p);
                t.add(q);
                t.add(s);
                double res = Collections.min(t);
                if(res == p){c1.add(r);}
                if(res == q){c2.add(r);}
                if(res == s){c3.add(r);}
           }
           centroid.clear();
           centroid.add(calc(c1));
           centroid.add(calc(c2));
           centroid.add(calc(c3));
		}
		System.out.println("------0th cluster--------");
		for(record r:c1)
			System.out.println(r.attr1 +" "+r.attr2);
		System.out.println("------1st cluster--------");
		for(record r:c2)
			System.out.println(r.attr1 +" "+r.attr2);
		System.out.println("------2nd cluster--------");
		for(record r:c3)
			System.out.println(r.attr1 +" "+r.attr2);
		System.out.println("-----centroid------");
		for(int i=0;i<3;i++)
			System.out.println((centroid.get(i)).attr1+","+(centroid.get(i)).attr2);
	} 

	public static record calc(ArrayList<record> bl) {
	     record a = new record();
	     double x=0,y=0;
	     for(record b:bl){
	           x=x+b.attr1;
	           y=y+b.attr2;
	     }
	     a.attr1=x/bl.size();
	     a.attr2=y/bl.size();
	     return a;
	}
	public static double dist(double p,double q,double r,double s) {
	double res = Math.pow((p-r),2)+Math.pow((q-s),2);
	     return Math.sqrt(res);
	}
}