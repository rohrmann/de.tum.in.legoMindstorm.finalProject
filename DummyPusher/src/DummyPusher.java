import misc.RobotType;
import Actor.Actor;


public class DummyPusher extends Actor {
	
	public static void main(String[] args){
		DummyPusher pusher = new DummyPusher();
		
		pusher.start();
	}

	@Override
	public void epilog() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RobotType getType() {
		return RobotType.PUSHER;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prolog() {
		// TODO Auto-generated method stub
		
	}

}
