import java.io.*; 
import java.util.*;
class fitems {
	static boolean check(String x1,String x2) {
		x2 = x2.replace("", ".*");
		if(x1.matches(x2))
		      return true;
		else
		      return false;
	}
	public static void main(String[] args) throws IOException,FileNotFoundException
	{
	      BufferedReader csv = new BufferedReader(new FileReader(new File("/Users/vishalprabhachandar/Documents/Programming/DataminingLab/Java-Programs/B3/src/input.csv")));
	      String data = csv.readLine();
	      HashSet<String> hs = new HashSet<>();
	      ArrayList<String> al = new ArrayList<>();
	      ArrayList<String> bl = new ArrayList<>();
	      ArrayList<String> cl = new ArrayList<>();
	      double support = 0.4,confidence=0.5;
	      while(data != null)
	      {
	            String dataarray[] = data.split(",");
	            String temp1="";
	            for(String x:dataarray)
	            {
	            	  hs.add(x);
	                  temp1=temp1+x;
	            }
	            bl.add(temp1);
	            data = csv.readLine();
	      }
	      String d[] = hs.toArray(new String[hs.size()]);
	      int n = d.length;
	      // generate all possible subset
	      for(int i=0;i < (1<<n); i++)
	      {
	            String temp="";
	            for(int j=0;j<n;j++)
	                  if(( i & (1<<j))>0)
	                        temp = temp+d[j];
	            al.add(temp);
	      }
	      // generate frequent itemset
	      for(int i=1;i<=4;i++)
	      {
	     System.out.println("\nFrequent "+i+"-itemset");
	     for(String y:al)
	    	 if(i == y.length())
	            {
	                  double count = 0;
	                  for(String x:bl) 
	                	  if(check(x,y))
	                              count++;
	                  if(count/bl.size() >= support)
	                  {
	                	  if(i == 4)
	                		 cl.add(y); 
	                	  System.out.println(y + " ->"+count/bl.size());
	                  }
	              }
	
		}
		System.out.println("\n----------Strong rules------------\n"); 
		//generate rules
		for(String p:cl)
		{
			System.out.println("\n-----For string "+p+"-----------");
		    char[] c = p.toCharArray();
		    n = c.length;
		    for(int i=0;i < (1<<n); i++)  //generate all subset
		    {
		            String temp3="",temp4="";
		            for(int j=0;j<n;j++)
		            {
		                  if(( i & (1<<j))>0)
		                        temp3 = temp3+c[j];
		                  else
		                        temp4 = temp4+c[j];
		            }
		            if(temp3.length() !=0 && temp3.length() != 4)
		                  {
				                  double count1=0,count2=0;
				                  for(String x:bl)
				                  {
										if(check(x,p))
											count1++; 
										if(check(x,temp3))
											count2++;
								  }
										if(count2 > 0 && (count1/count2) >= confidence) System.out.println(temp3+"->"+temp4+"confidence: "+count1/count2);
		                  }
		     }
		}
	}
}
