package helloWorld;

public class DriveNextPath {
	public boolean suppressed;
	private PilotRobot me;

	// Constructor - store a reference to the robot
	public DriveNextPath(PilotRobot robot){
    	 me = robot;
    }

	// When called, this should stop action()
	public void suppress(){
		suppressed = true;
	}

	// This returns true, so will always take control (if
	// no higher priority behaviour also takes control).
	// We could modify this to look for a "finish" variable
	// so that if the robot should stop, then we could simply
	// not take control.  If no behaviour takes control, the
	// Arbritrator will end.
	public boolean takeControl(){
		return true;	
	}

	// This is our action function.  This starts the motor running
	// (which returns immediately).  We then simply run until told
	// to suppress our action, in which case we stop.
	public void action() {
		// Allow this method to run
		suppressed = false;
		
		// Go forward
		me.getPilot().forward();
		// While we can run, yield the thread to let other threads run.
		// It is important that no action function blocks any otherf action.
		while (!suppressed) {
			Thread.yield();
		}
		
	    // Ensure that the motors have stopped.
		me.getPilot().stop();
		me.setPose();
	}
}
