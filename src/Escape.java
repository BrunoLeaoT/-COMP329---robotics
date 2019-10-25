import lejos.hardware.Button;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Escape implements Behavior{
	public boolean suppressed;
	PilotRobot pilot;
	// Constructor - the robot, and gets access to the pilot class
	// that is managed by the robot (this saves us calling
	// me.getPilot.somemethod() all of the while)

	// When called, this should stop action()
	public Escape(PilotRobot me) {
		pilot = me;
	}
	public void suppress(){
		while(!Button.ESCAPE.isDown()) {
			suppressed = true;
		}
	}
	
	// When called, determine if this behaviour should start
	public boolean takeControl(){
		if(Button.ESCAPE.isDown())
			return true;
		return false;

	}

	// This is our action function.  All calls to the motors should be
	// non blocking, so that they can be stopped if suppress is true.
	// If a call is made to move a specific distance or rotate a specific
	// angle etc, then it should return immediately, and monitored until
	// it has completed.  The code below illustrates this, but waiting
	// until the robot stops moving.  An OdometryPoseProvider could also
	// be used for this.	
	public void action() {
		// Allow this method to run
		suppressed = false;
		
		// Reverse for 20cm, and have the thread yield until this is
		// complete (i.e. the robot stops) or if suppressed is true.
		
		pilot.closeRobot();
	    while(!suppressed) {
	        Thread.yield();  // wait till turn is complete or suppressed is called
	    }

	    // Rotate for 45 degrees, and have the thread yield until this is
		// complete (i.e. the robot stops) or if suppressed is true.  Note
	    // that we can check suppressed to see if it is even worth doing.
	    // There are more elegant ways of doing this!!!
	    
	}

}