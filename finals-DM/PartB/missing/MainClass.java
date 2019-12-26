import java.io.*;
import java.util.*;

public class MainClass {
	
	Scanner in=new Scanner(System.in);
	ArrayList<String[]> data;

	private void loadFile(String fileName) {
		try {
			data=new ArrayList<String[]>();

			String ipLine;
			BufferedReader br=new BufferedReader(new FileReader(new File(fileName)));
			ipLine=br.readLine();
			while(ipLine!=null){
				
				String[] rowData=ipLine.split(",");
				ipLine=br.readLine();
				data.add(rowData);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("No such file found!");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void missingString(int col, String defValue) {
		HashMap<String, Integer> hm=new HashMap<String,Integer>();
		for(String[] row: data) {
			if(!row[col].equalsIgnoreCase(defValue)) {
				hm.put(row[col], hm.getOrDefault(row[col],0)+1);
			}
		}
		
		String maxValName = null;
		int maxVal=0;
		for(Map.Entry<String, Integer> me:hm.entrySet()) {
			if(me.getValue()>maxVal){
				maxVal=me.getValue();
				maxValName=me.getKey();
			}
		}
		
		for(String[] row: data) {
			if(row[col].equalsIgnoreCase(defValue)) {
				row[col]=maxValName;
			}
		}
		
		
	}
	
	public void missingInteger(int col, String defValue) {
		float avg=0;
		int count=0;
		for(String[] row: data) {
			if(!row[col].equalsIgnoreCase(defValue)) {
				avg+=Float.parseFloat(row[col]);
			}
			count++;
		}
		avg=avg/count;
		for(String[] row: data) {
			if(row[col].equalsIgnoreCase(defValue)) {
				row[col]=Integer.toString((int)avg);
			}
		}
		
	}
	public void generateFile() {
		try {
			FileWriter fw=new FileWriter(new File("output.csv"));
			for(String[] rowData:data) {
				String newData=Arrays.toString(rowData);
				newData=newData.substring(1,newData.length()-1);
				fw.write(newData);
				fw.write('\n');
			}
			
			fw.close();
			System.out.println("\nFile generated successfully!");
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		MainClass mc=new MainClass();
		mc.loadFile("input.csv");
		mc.missingString(5, "NA");
		mc.missingInteger(1, "NA");
		mc.generateFile();
		
	}

}