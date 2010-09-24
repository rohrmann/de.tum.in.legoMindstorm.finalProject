import misc.GraphExample;
import misc.RobotType;
import Graph.Pair;


public class testPusher {

	/**
	 * @param args
	 */
	public static void main (String[] aArg)
	throws Exception
	{
		Pusher pusher = new Pusher();
		pusher.getNavigator().setGraph(GraphExample.getGraph(), RobotType.PUSHER);
		//pusher.getNavigator().moveToAstar(new Pair(0,5));
		pusher.getNavigator().turnSouth();
		pusher.prolog();
		pusher.getNavigator().moveStraightForward(1);
		pusher.epilog();
	}

}
