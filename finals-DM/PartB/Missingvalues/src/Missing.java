import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Missing {
	
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
			if(me.getValue()>maxVal)
				maxValName=me.getKey();
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
		Missing mc=new Missing();
		mc.loadFile("/Users/vishalprabhachandar/Documents/Programming/DataminingLab/Java-Programs/B2/src/input.csv");
		mc.missingString(5, "NA");
		mc.missingInteger(1, "NA");
		mc.generateFile();
		
	}

}