import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Pose;

// PilotRobot.java
// 
// Based on the SimpleRobot class, this provides access to the
// sensors, and constructs a MovePilot to control the robot.
//
// Terry Payne
// 1st October 2018
//


public class PilotRobot {
	private EV3UltrasonicSensor usSensor;
	private EV3GyroSensor gSensor;
	private SampleProvider distSP, gyroSP;	
	private float[] distSample, angleSample; 
	private MovePilot pilot;
	double lastAngle = 0;
	OdometryPoseProvider opp;
	Pose myPose;
	Paths searchTree = new Paths();
	Cell map[][] = null;
	Cell stateCell = new Cell(1,1);
	Probability probModel = new Probability(this);
	public boolean finished = false;
	
	public PilotRobot() {
		Brick myEV3 = BrickFinder.getDefault();

		//leftBump = new EV3TouchSensor(myEV3.getPort("S1"));
		//rightBump = new EV3TouchSensor(myEV3.getPort("S4"));
		usSensor = new EV3UltrasonicSensor(myEV3.getPort("S3"));
		gSensor = new EV3GyroSensor(myEV3.getPort("S2"));

		//leftSP = leftBump.getTouchMode();
		//rightSP = rightBump.getTouchMode();
		distSP = usSensor.getDistanceMode();
		gyroSP = gSensor.getAngleMode();
		
		//leftSample = new float[leftSP.sampleSize()];		// Size is 1
		//rightSample = new float[rightSP.sampleSize()];		// Size is 1
		distSample = new float[distSP.sampleSize()];		// Size is 1
		angleSample = new float[gyroSP.sampleSize()];	// Size is 1

		// Set up the wheels by specifying the diameter of the
		// left (and right) wheels in centimeters, i.e. 4.05 cm.
		// The offset number is the distance between the centre
		// of wheel to the centre of robot (4.9 cm)
		// NOTE: this may require some trial and error to get right!!!
		Wheel leftWheel = WheeledChassis.modelWheel(Motor.B, 4.225).offset(-5.55); //motor 4.225 offset 5.3
		Wheel rightWheel = WheeledChassis.modelWheel(Motor.D, 4.225).offset(5.55); // 4.225 and 5.3
		
		Chassis myChassis = new WheeledChassis( new Wheel[]{leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);

	    pilot = new MovePilot(myChassis);
    	opp = new OdometryPoseProvider(pilot);
		setPose();
		// Reset the value of the gyroscope to zero
    	initializeMap();
		getPilot().setLinearAcceleration(10);
		getPilot().setLinearAcceleration(getPilot().getLinearAcceleration()/3 * 2);
		gSensor.reset();
	}
	
	public void closeRobot() {
		//leftBump.close();
		//rightBump.close();
		usSensor.close();
		gSensor.close();
		
	}
	
	public boolean isTooCloseUS() {
		if(getDistance() < 0.08) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void turnHead(int angle) {
		Motor.C.rotate(angle);
	}
	public float getDistance() {
    	distSP.fetchSample(distSample, 0);
    	return distSample[0];
	} 
	
	public int getSomethingElse() {
		return distSP.sampleSize();
	}

	public float getAngle() {
    	gyroSP.fetchSample(angleSample, 0);
    	return angleSample[0];
	}
	
	public int getDirectionHeading() {
		int side;
		if(getAngle()>360)
			side = Math.round(((getAngle()%360)/90));
		else
			side = Math.round(getAngle()/90);
		if(side == 4 || side == -1) {
			side = 0;
		}
		return side;
	}
	
	public MovePilot getPilot() {
		return pilot;
	}
	
	 public void adjustPosition(double turnIntention) {
		double adjusted = 0;
		double angle  = getAngle();
		double realAngle = angle - lastAngle;
    	adjusted = (double) (turnIntention - realAngle);
        pilot.rotate(adjusted);
        lastAngle = angle + adjusted;
    }

    public void turnMotors(double angle){
    	pilot.setAngularAcceleration(70);
		pilot.rotate(angle);
		adjustPosition(angle);
		updatesGridProb();
    }
    public void updatesGridProb() {
		int head = getDirectionHeading();
		probModel.probabilisticModel(getDistance(), head);
//		turnHead(90);
//		if(head == 3) {
//			probModel.probabilisticModel(getDistance(), 0);	
//		}
//		else {
//			probModel.probabilisticModel(getDistance(), head+1);
//		}
//		turnHead(-180);
//		if(head == 0) {
//			probModel.probabilisticModel(getDistance(), 3);	
//		}
//		else {
//			probModel.probabilisticModel(getDistance(), head-1);
//		}		
//		turnHead(90);
    	
    }
    public void run(float distance) {
		float angleBefore = getAngle();
    	getPilot().travel(distance);
		float diff = angleBefore - getAngle();
		pilot.rotate(diff);
		updatesGridProb();
    }
    public void setPose() {
    	myPose = opp.getPose();
    }
    
    public Pose getPose() {
    	return myPose;
    }
    
    private void initializeMap() {
    	this.map = new Cell[8][7];
    	for(int i = 1;i<8;i++) {
    		for(int j = 1;j<7;j++) {
    			this.map[i][j] = new Cell(1,1);
    			this.map[i][j].x = j;
    			this.map[i][j].y = i;
        	}
    	}
    	stateCell = this.map[1][1];
    }
    
    public void assiningObstacle() {
		int side = getDirectionHeading();
		switch (side) {
		case 0: // Facing the initial position
			if(getDistance() < 0.08 && (stateCell.y) != 7) {
				map[stateCell.y + 1][stateCell.x].obstacle = true;
			}
			break;
		case 1: // Turned to the right from the initial position
			if(getDistance() < 0.08 &&(stateCell.x) != 6) {
				map[stateCell.y][stateCell.x + 1].obstacle = true;
			}
			break;
			
		case 2: // Turned 180 from the initial position
			if(getDistance() < 0.08 && (stateCell.y) != 1 ) {
				map[stateCell.y - 1][stateCell.x].obstacle = true;
			}
			break;
			
		case 3: // Turned to the left from the initial position
			if(getDistance() < 0.08 && (stateCell.x) != 1 ) {
				map[stateCell.y][stateCell.x - 1].obstacle = true;
			}
			break;
		default:
			break;
		}
    }
}