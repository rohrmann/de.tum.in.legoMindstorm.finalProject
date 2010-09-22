package Text;

public class TestTextparse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String a = "pull (-333,5) (78686,-92)";
		
		if (a.startsWith("puller")){
			String b = (String) a.subSequence(8, a.length()-1);
			String[] c = b.split(",");
			int x = Integer.parseInt(c[0]);
			int y = Integer.parseInt(c[1]);
			System.out.println(x);
			System.out.println(y);
		}else if (a.startsWith("pusher")){
			String b = (String) a.subSequence(8, a.length()-1);
			String[] c = b.split(",");
			int x = Integer.parseInt(c[0]);
			int y = Integer.parseInt(c[1]);
			System.out.println(x);
			System.out.println(y);
		}else if (a.startsWith("push")){
			String b = (String) a.subSequence(6, a.length()-1);
			String[] c = b.split(" ");
			String dest = c[0].substring(0, c[0].length()-1);
			String[] destArray = dest.split(",");
			int x = Integer.parseInt(destArray[0]);
			int y = Integer.parseInt(destArray[1]);
			
			
			String destbox = c[1].substring(1, c[1].length());
			String[] destboxArray = destbox.split(",");
			int xdest = Integer.parseInt(destboxArray[0]);
			int ydest = Integer.parseInt(destboxArray[1]);
			System.out.println(x);
			System.out.println(y);
			System.out.println(xdest);
			System.out.println(ydest);
		}else if (a.startsWith("pull")){
			String b = (String) a.subSequence(6, a.length()-1);
			String[] c = b.split(" ");
			String dest = c[0].substring(0, c[0].length()-1);
			String[] destArray = dest.split(",");
			int x = Integer.parseInt(destArray[0]);
			int y = Integer.parseInt(destArray[1]);
			
			
			String destbox = c[1].substring(1, c[1].length());
			String[] destboxArray = destbox.split(",");
			int xdest = Integer.parseInt(destboxArray[0]);
			int ydest = Integer.parseInt(destboxArray[1]);
			System.out.println(x);
			System.out.println(y);
			System.out.println(xdest);
			System.out.println(ydest);
		}
		else {
			
		}
	}

}
