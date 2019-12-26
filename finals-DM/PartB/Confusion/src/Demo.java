import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Demo {

	public static ArrayList<DataPoint> getInput(String filename, String delimiter){
		ArrayList<DataPoint> data = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			String line;
			boolean rv, pv;
			while((line=br.readLine())!=null) {
				String[] currentLine = line.split(delimiter);
				if(currentLine[0].equals("T")) {
					rv = true;
				}
				else {
					rv = false;
				}
				
				if(currentLine[1].equals("T")) {
					pv = true;
				}
				else {
					pv = false;
				}
				
				DataPoint d = new DataPoint(rv, pv);
				data.add(d);
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	
	
	public static void main(String[] args) {
		String filename, delimiter;
		filename = "/Users/hemantj/Documents/scratch/DM-Lab/Part-B/Confusion/src/data.csv";
		delimiter = ",";
		ArrayList<DataPoint> data = getInput(filename, delimiter);
		double truePositive=0, falsePositive=0, trueNegetive=0 ,falseNegetive=0;
		double w1 = 2, w2 = 1, w3 = 1, w4 = 2;
		for(DataPoint d : data) {
			if(d.realValue && d.predictedValue) {
				truePositive++;
			}
			if(!d.realValue && d.predictedValue) {
				falsePositive++;
			}
			if(!d.realValue && !d.predictedValue) {
				trueNegetive++;
			}
			if(d.realValue && !d.predictedValue) {
				falseNegetive++;
			}
		}
		
		System.out.println(truePositive + " " + falseNegetive + " " + falsePositive + " " + trueNegetive);
		
		double sensitivity = truePositive/(truePositive + falseNegetive);
		double specificity = trueNegetive/(trueNegetive + falsePositive);
		double recall = truePositive/(truePositive + falseNegetive);
		double precision = truePositive/(truePositive+falsePositive);
		double weightedAccuracy = (w1*truePositive + w4*trueNegetive)/(w1*truePositive + w2*falseNegetive + w3*falsePositive + w4*trueNegetive);
		
		System.out.println("Sensitivity: " + sensitivity);
		System.out.println("Specificity: " + specificity);
		System.out.println("Precision: " + precision);
		System.out.println("Recall: " + recall);
		System.out.println("Weighted Accuracy: " + weightedAccuracy);
	}

}
