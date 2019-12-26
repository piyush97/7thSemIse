import java.io.*;
class Records {
	int index,attr1;
	String attr2;
}

public class sample {
	static Records[] rc=new Records[10];
	public static void main(String args[]) throws FileNotFoundException,IOException {
		String path = "/Users/hemantj/Documents/scratch/DM-Lab/Part-B/Sampling/src/data.csv";
		BufferedReader CSV=new BufferedReader(new FileReader(new File(path)));
		String data=CSV.readLine();
		int i=0,min = Integer.MAX_VALUE,max = Integer.MIN_VALUE; 
		System.out.println("Dataset:");
		while(data!=null) {
			rc[i]=new Records();
			String[] dataArray=data.split(","); 
			rc[i].index=Integer.parseInt(dataArray[0]);
			rc[i].attr1=Integer.parseInt(dataArray[1]); 
			rc[i].attr2=dataArray[2];
			if(rc[i].attr1 > max)
			max=rc[i].attr1; if(rc[i].attr1 < min)
			min=rc[i].attr1;
			System.out.println(rc[i].index+" "+rc[i].attr1+" "+rc[i].attr2);
			                  data=CSV.readLine();
			i++;
		}
        //finding aggregate for numeric attribute
        int avg =0;
        for(int j=0;j<i;j++)
		avg+= rc[j].attr1; 
        avg= avg/i;
		System.out.println("max value :"+max+"\tmin value:"+min); 
		System.out.println("Average value is: "+avg);
		//performing discretization for numeric attribute
		int mean = (min + max) / 2;
		int mid1 = (min + mean) / 2;
		int mid2 = (mean + max) / 2;
		int sampling[] = new int[4];
		for(int j=0;j<i;j++) {
			System.out.print(rc[j].index+" "+rc[j].attr1+" "+rc[j].attr2); 
			if(rc[j].attr1 >= min && rc[j].attr1 < mid1) {
				System.out.println(" ["+min+"-"+mid1+")");
				sampling[0]=rc[j].attr1; 
			}
			else if(rc[j].attr1 >= mid1 && rc[j].attr1 < mean) {
				System.out.println(" ["+mid1+"-"+mean+")");
				sampling[1]=rc[j].attr1; 
			}
			else if(rc[j].attr1 >= mean && rc[j].attr1 < mid2) {
				System.out.println(" ["+mean+"-"+mid2+")");
				sampling[2]=rc[j].attr1; 
			}
			else if(rc[j].attr1 >= mid2 && rc[j].attr1 <= max) {
				System.out.println(" ["+mid2+"-"+max+")");
				sampling[3]=rc[j].attr1; 
			}
		}
		System.out.println("----sampling-------");
		System.out.println(" ["+min+"-"+mid1+") -"+sampling[0]);
		System.out.println(" ["+mid1+"-"+mean+") -"+sampling[1]); 
		System.out.println(" ["+mean+"-"+mid2+") -"+sampling[2]);
		System.out.println(" ["+mid2+"-"+max+") -"+sampling[3]);
		CSV.close();
	}
}