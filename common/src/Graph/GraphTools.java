package Graph;

import java.util.Enumeration;

public class GraphTools {

	public static int getSize(Graph graph) {
		Enumeration enumeration = graph.getHashtable().keys();
		int size = 0;
		while (enumeration.hasMoreElements()) {
			enumeration.nextElement();
			size++;
		}
		return size;
	}
}