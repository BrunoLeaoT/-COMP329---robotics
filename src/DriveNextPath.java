import java.awt.Robot;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class DriveNextPath implements Behavior{
	public boolean suppressed;
	private PilotRobot me;
	Cell map[][];
	Cell goalCell;
	List<Cell> path;
	boolean stop;
	public aStar aStar;
	// Constructor - store a reference to the robot
	public DriveNextPath(PilotRobot robot){
    	 me = robot;
    	 map = me.map;
    	 aStar = new aStar(me);
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
		
		goToNextPath();
		// While we can run, yield the thread to let other threads run.
		// It is important that no action function blocks any otherf action.
		//while (!suppressed) {
		//	Thread.yield();
		//}
		
	    // Ensure that the motors have stopped.
		me.getPilot().stop();
		me.setPose();
	}
	
	public Cell nextPath() {
		for(int i =0; i < 5; i++) {
			switch (i) {
			case 0:
				if(!me.searchTree.first.visited) {
					return me.searchTree.first;
				}
				break;
			case 1:
				if(!me.searchTree.second.visited) {
					return me.searchTree.second;
				}
				break;
			case 2:
				if(!me.searchTree.third.visited) {
					return me.searchTree.third;
				}
				break;
			case 3:
				if(!me.searchTree.fourth.visited) {
					return me.searchTree.fourth;
				}
				break;
			case 4:
				if(!me.searchTree.five.visited) {
					return me.searchTree.five;
				}
				break;
			default:
				return null;
			}

		}
		return null;
	}

	public void goToNextPath() {
		goalCell = nextPath();
		while(goalCell != null) {
			path = (ArrayList<Cell>) aStar.AStar(me.map, me.stateCell, goalCell);
	
//			for(int i =0; i < path.size();i++) {
//				Cell nextCell = path.get(i);
//				System.out.println(nextCell.y + "," + nextCell.x);
//			}
//			
			// supressed = false;
			for(int i =0; i < path.size();i++) {
				Cell nextCell = path.get(i);
				if(nextCell.x != me.stateCell.x) {
					TravelToCell("x", nextCell, goalCell);
				}
				if(nextCell.y != me.stateCell.y) {
					TravelToCell("y", nextCell, goalCell);
				}
				if(stop) {
					stop = false;
					break;
				}
			}
			assignVisited(me.stateCell);
			goalCell = nextPath();
		}
	}
	
	public void TravelToCell(String axis, Cell nextCell, Cell goalCell) {
		int side = me.getDirectionHeading();
		
		if(axis.equals("x")) {
			if(nextCell.x > me.stateCell.x) {
				if(side != 1)
					AdjustMyPosition(1, side);
			}
			else { 
				if(side != 3)
					AdjustMyPosition(3, side);
			}
			boolean returned = checkForObstacles(nextCell, goalCell);
			if(returned)
				return;
			me.run((float) 25.5);
			//while (!suppressed) {
			//	Thread.yield();
			//}
			me.stateCell.x = nextCell.x;
		}
		else {
			if(nextCell.y > me.stateCell.y) {
				if(side != 0)
					AdjustMyPosition(0, side);
			}
			else {
				if(side != 2)
					AdjustMyPosition(2, side);
			}

			boolean returned = checkForObstacles(nextCell, goalCell);
			if(returned)
				return;
			me.run((float) 25.5);
			//while (!suppressed) {
			//	Thread.yield();
			//}
			me.stateCell.y = nextCell.y;
		}
	}
	
	public boolean checkForObstacles(Cell nextCell, Cell goalCell) {
		if(me.getDistance() < 0.08) {
			me.assiningObstacle();
			if(nextCell.x == goalCell.x && nextCell.y == goalCell.y ) {
				assignVisited(goalCell);
			}
			stop = true;
			return true;
		}
		return false;
	}
	
	public void AdjustMyPosition(int desired, int now) {
		Delay.msDelay(500);
		while(now != desired) {
			me.turnMotors(90);
			now = me.getDirectionHeading();
			//while (!suppressed) {
			//	Thread.yield();
			//}
		}
	}
	
	public void assignVisited(Cell cell) {
		for(int i =0; i < 5; i++) {
			switch (i) {
			case 0:
				if(cell.x == me.searchTree.first.x && cell.y == me.searchTree.first.y ) {
					me.searchTree.first.visited = true;
				}
				break;
			case 1:
				if(cell.x == me.searchTree.second.x && cell.y == me.searchTree.second.y ) {
					me.searchTree.second.visited = true;
				}
				break;
			case 2:
				if(cell.x == me.searchTree.third.x && cell.y == me.searchTree.third.y ) {
					me.searchTree.third.visited = true;
				}
				break;
			case 3:
				if(cell.x == me.searchTree.fourth.x && cell.y == me.searchTree.fourth.y ) {
					me.searchTree.fourth.visited = true;
				}
				break;
			case 4:
				if(cell.x == me.searchTree.five.x && cell.y == me.searchTree.five.y ) {
					me.searchTree.five.visited = true;
				}
				break;
			default:
				break;
			}

		}
	}
	

}
