import java.io.*;


public class ReadGraph {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FileReader datenstrom;
			datenstrom = new FileReader(
			"C:/Users/Public/Documents/test.txt");
			BufferedReader eingabe = new BufferedReader(datenstrom);
			String a = eingabe.readLine();
			int maxLength = 0;
			int maxHeigth = 0;
		 while(a != null){
			 maxHeigth++;
			 if(a.length()>maxLength){
				 maxLength = a.length();
			 }
			 a = eingabe.readLine();
		 }
		 System.out.println("maxLength: " + maxLength);
		 System.out.println("maxHeigth: " + maxHeigth);
	}
}
