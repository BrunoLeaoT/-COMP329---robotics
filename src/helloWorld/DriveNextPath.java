package helloWorld;

import java.awt.Robot;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import helloWorld.Cell;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class DriveNextPath implements Behavior{
	public boolean suppressed;
	private PilotRobot me;
	Cell map[][];
	// Constructor - store a reference to the robot
	public DriveNextPath(PilotRobot robot){
    	 me = robot;
    	 map = me.map;
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
		
		Cell nextCell = nextPath();
		
		if(!(nextCell == null)) {
			// Go forward
			goToNextPath(nextCell);
		}
		// While we can run, yield the thread to let other threads run.
		// It is important that no action function blocks any otherf action.
		while (!suppressed) {
			Thread.yield();
		}
		
	    // Ensure that the motors have stopped.
		me.getPilot().stop();
		me.setPose();
	}
	
	public Cell nextPath() { 
		for(int i =0; i < 4; i++) {
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

			default:
				System.out.println("No where to go");
				if(!me.didEntireCircuit) {
					me.didEntireCircuit = true;
					return me.searchTree.first;
				}
				else {
					return null;
				}
			}

		}
		return null;
	}

	public void goToNextPath(Cell goalCell) {
		List<Cell> path = (ArrayList<Cell>) AStar(me.map, goalCell, me.stateCell);
		for(int i =0; i < path.size();i++) {
			Cell nextCell = path.get(i);
			System.out.println(nextCell.y + ";" + nextCell.x);
		}
		Delay.msDelay(1000);
		for(int i =0; i < path.size();i++) {
			Cell nextCell = path.get(i);
			boolean stop = false;
			if(nextCell.x != me.stateCell.x) {
				stop = TravelToCell("x", nextCell, goalCell);
			}
			if(nextCell.y != me.stateCell.y) {
				stop = TravelToCell("y", nextCell, goalCell);
			}
			if(stop)
				break;
			
		}
		
		assignVisited(me.stateCell);
		Cell nextCell = nextPath();
		if(nextCell != null)
			goToNextPath(nextCell);
	}
	
	public boolean TravelToCell(String axis, Cell nextCell, Cell goalCell) {
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
			float angleBefore = me.getAngle();
			boolean returned = checkForObstacles(nextCell, goalCell);
			if(returned)
				return true;
			me.run(24);
			AdjustMyAngle(angleBefore);
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
			float angleBefore = me.getAngle();
			boolean returned = checkForObstacles(nextCell, goalCell);
			if(returned)
				return true;
			me.run(24);
			AdjustMyAngle(angleBefore);
			me.stateCell.y = nextCell.y;
		}
		return false;
	}
	
	public boolean checkForObstacles(Cell nextCell, Cell goalCell) {
		if(me.getDistance() < 0.08 && me.stateCell.y != 0 && me.stateCell.y != 6 && me.stateCell.x != 0 && me.stateCell.x != 5) {
			me.assiningObstacle();
			if(nextCell.x == goalCell.x && nextCell.y == goalCell.y ) {
				assignVisited(nextCell);
				goalCell = nextPath();
			}
			goToNextPath(goalCell);
			return true;
		}
		return false;
	}
	
	public void AdjustMyAngle(float angle) {
		float diff = angle - me.getAngle();
		me.turnMotors(diff);
	}
	
	public void AdjustMyPosition(int desired, int now) {
		Delay.msDelay(1000);
		while(now != desired) {
			System.out.println("Desired: " + desired);
			System.out.println(" Now: " + now);
			me.turnMotors(90);
			now = me.getDirectionHeading();

		}
	}
	
	public void assignVisited(Cell cell) {
		for(int i =0; i < 4; i++) {
			switch (i) {
			case 0:
				if(cell.x == me.searchTree.first.x && cell.y == me.searchTree.first.y ) {
					me.searchTree.first.visited = true;
					System.out.println("Assigned One");
				}
				break;
			case 1:
				if(cell.x == me.searchTree.second.x && cell.y == me.searchTree.second.y ) {
					me.searchTree.second.visited = true;
					System.out.println("Assigned Two");
				}
				break;
			case 2:
				if(cell.x == me.searchTree.third.x && cell.y == me.searchTree.third.y ) {
					me.searchTree.third.visited = true;
					System.out.println("Assigned Three");
				}
				break;
			case 3:
				if(cell.x == me.searchTree.fourth.x && cell.y == me.searchTree.fourth.y ) {
					me.searchTree.fourth.visited = true;
					System.out.println("Assigned Four");
				}
				break;

			default:
				break;
			}

		}
	}
	
	public List<Cell> AStar(Cell sucessors[][], Cell goal, Cell state) {
		ArrayList<ArrayList<Cell>> frontier = new ArrayList<>();
		frontier.add(new ArrayList<Cell>());
		frontier.get(0).add(state);
		while(frontier.size() > 0) {
			int index = cost(frontier, goal);
			ArrayList<Cell> lowerCost = frontier.get(index);
			frontier.remove(index);
			if(lowerCost.get(lowerCost.size()-1).x == goal.x && lowerCost.get(lowerCost.size()-1).y == goal.y) {
				return lowerCost;
			}
			else {
				AddingSucessors(frontier, lowerCost, me.map, goal);
			}
		}
		return null;
	}
	
	public int cost(ArrayList<ArrayList<Cell>> frontier, Cell goal) {
		int min = 0;
		int auxIndex = 0;
		for(int i = 0;i < frontier.size(); i++ ) {
			Cell lastCellOfPath = frontier.get(i).get(frontier.get(i).size() - 1);
			int g = lastCellOfPath.x + lastCellOfPath.y;
			int h = (goal.x -  lastCellOfPath.x) +  (goal.y -lastCellOfPath.y);
			if( g+h < min){
				min = g+h;
				auxIndex = i;
			}
		}
		return auxIndex;
	}
	public void AddingSucessors(ArrayList<ArrayList<Cell>> frontier, ArrayList<Cell> lowerCost, Cell sucessors[][], Cell goal) {
		Cell lastCell = lowerCost.get(lowerCost.size() - 1);
		if((lastCell.x + 1) <= 5) {
			ArrayList<Cell> aux = (ArrayList<Cell>) lowerCost.clone();
			aux.add(sucessors[lastCell.y][lastCell.x + 1]);
			if(!me.map[(aux.get(aux.size()-1).y)][(aux.get(aux.size()-1).x)].obstacle || checkIfIsGoal(goal, (aux.get(aux.size()-1))) )
			{
				frontier.add(aux); 
			}


		}
		if((lastCell.x -1) >= 0 ) {
			ArrayList<Cell> aux =(ArrayList<Cell>) lowerCost.clone();
			aux.add(sucessors[lastCell.y][lastCell.x - 1]);
			if(!me.map[(aux.get(aux.size()-1).y)][(aux.get(aux.size()-1).x)].obstacle || checkIfIsGoal(goal, (aux.get(aux.size()-1))) )
				frontier.add(aux);  

		}
		if((lastCell.y + 1) <= 6) {
			ArrayList<Cell> aux = (ArrayList<Cell>) lowerCost.clone();
			aux.add(sucessors[lastCell.y + 1 ][lastCell.x]);
			if(!me.map[(aux.get(aux.size()-1).y)][(aux.get(aux.size()-1).x)].obstacle || checkIfIsGoal(goal, (aux.get(aux.size()-1))) )
				frontier.add(aux);


		}
		if((lastCell.y -1) >= 0) {
			ArrayList<Cell> aux = (ArrayList<Cell>) lowerCost.clone();
			aux.add(sucessors[lastCell.y - 1][lastCell.x]);
			if(!me.map[(aux.get(aux.size()-1).y)][(aux.get(aux.size()-1).x)].obstacle || checkIfIsGoal(goal, (aux.get(aux.size()-1))) )
			{
				frontier.add(aux); 
			}

		}
		
	}
	
	public boolean checkIfIsGoal(Cell goal, Cell test) {
		if(goal.x == test.x && goal.y == test.y) {
			return true;
		}
		return false;
	}
	
	public ArrayList<ArrayList<Cell>> removeElement(ArrayList<ArrayList<Cell>> frontier, List<Cell> lowerCost) {
        for(int i = 0; i < frontier.size(); i++){
            if(frontier.get(i) == lowerCost){
                // shifting elements
                for(int j = i; j < frontier.size() - 1; j++){
                	frontier.set(j, frontier.get(j+1));
                }
                break;
            }
        }
        return frontier;
	}
}