import java.util.*; 
import java.io.*; 

class temp{
    double p;
    String q,r;
}

class naive {
    public static double probability(ArrayList<temp> al,String x) {
        double sum = 0;
        for(temp z:al)
            if((z.q).equals(x))
                sum++;
        return sum / al.size();
    }

    public static double mean(ArrayList<temp> al) {
        double sum = 0;
        for (temp z:al) {
            sum += z.p;
        }
        return sum / al.size();
    }

    public static double sd(ArrayList<temp> al,double mean) {
        double sum = 0;
        int x = al.size();
        for (temp z:al) {
            sum += Math.pow((z.p-mean),2);
        }
        return Math.sqrt(sum/(x*(x-1)));
    }

    public static void main(String args[]) throws FileNotFoundException, IOException {
        BufferedReader csv = new BufferedReader(new FileReader(new File("/Users/hemantj/Documents/scratch/DM-Lab/Part-B/NaiiveBayes/src/input.csv")));
        String data = csv.readLine();
        ArrayList<temp> y = new ArrayList<>();
        ArrayList<temp> n = new ArrayList<>();
        int i=0;
        while(data != null) {
            String[] dataarray = data.split(","); 
            temp res = new temp();
            res.p = Double.parseDouble(dataarray[0]); 
            res.q = dataarray[1];
            res.r = dataarray[2];
            if(dataarray[2].equals("Y"))
                y.add(res);
            else
                n.add(res);
            data = csv.readLine();
        }
        System.out.println("Enter temperature in def F and degree of coldness(L,M,H) to find its class");
        Scanner in = new Scanner(System.in);
        double x1 = in.nextDouble();
        String x2 = in.next();
        //initial probability
        int t = y.size() + n.size();
        double p_y = (double)y.size()/(double)t;
        double p_n = (double)n.size()/(double)t;
        //for numeric attributes
        double p1,p2,p3,p4,exp;
        double mean1 = mean(y);
        double mean2 = mean(n);
        double sd1 = sd(y,mean1);
        double sd2 = sd(n,mean2);
        exp = Math.pow((x1 - mean1), 2)/(2* Math.pow(sd1,2));
        p3 = Math.exp(-1*exp)/(Math.sqrt(2*3.14)*sd1);
        exp = Math.pow((x1 - mean2), 2)/(2 *Math.pow(sd2,2));
        p4 = Math.exp(-1*exp)/(Math.sqrt(2*3.14)*sd2);
        //for non-numeric atrributes
        p1 = probability(y,x2);
        p2 = probability(n,x2);
        double res1,res2;
        res1 = p1*p3*p_y;
        res2 = p2*p4*p_n;

        //results
        System.out.println("--Class Y--");
        System.out.println("Mean :"+mean1+"\tStandard deviation:  "+sd1);
        System.out.println("size :S given class"+i+":"+probability(y,"S"));
        System.out.println("size :M given class"+i+":"+probability(y,"M"));
        System.out.println("size :L given class"+i+":"+probability(y,"L")+"\n");

        System.out.println("--Class N--");
        System.out.println("Mean :"+mean2+ "\tStandard deviation: "+sd2);
        System.out.println("size :S given class"+i+":"+probability(n,"S"));
        System.out.println("size :M given class"+i+":"+probability(n,"M"));
        System.out.println("size :L given class"+i+":"+probability(n,"L")+"\n");
        System.out.println(res1+"\n"+res2);
        if(res1>res2)
            System.out.println("Class for weight "+x1+" and shirt size"+x2+" is : Y");
        else
            System.out.println("Class for weight "+x1+" and shirt size "+x2+" is : N");
    }
}