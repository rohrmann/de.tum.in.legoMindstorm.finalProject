import misc.RobotType;
import Actor.Actor;


public class DummyPuller extends Actor {
	
	public static void main(String[] args){
		DummyPuller puller = new DummyPuller();
		
		puller.start();
	}

	@Override
	public void epilog() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RobotType getType() {
		return RobotType.PULLER;
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
