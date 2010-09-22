package Memory;

import Graph.Pair;

public class Update {
		
		private static Pair[] boxPositions;
		private static Pair pusher;
		private static Pair puller;
		
		public static Pair[] getBoxPositions(){
			return boxPositions;
		}
		
		public static void setBoxPositions(Pair[] boxPositions1){
			boxPositions = boxPositions1;
		}
		
		public static void setNewBoxPositions(Pair oldBox, Pair newBox){
			for(int i=0; i<boxPositions.length; i++){
				if (boxPositions[i] == oldBox){
					boxPositions[i] = newBox;
				}
			}
		}
		
		public static void setPusher(Pair a){
			pusher = a;
		}
		
		public static Pair getPusher(){
			return pusher;
		}
		
		public static void setPuller(Pair a){
			puller = a;
		}
		
		public static Pair getPuller(){
			return puller;
		}
	
}
