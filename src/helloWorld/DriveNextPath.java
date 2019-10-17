package helloWorld;

import java.awt.Robot;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import helloWorld.Cell;
import lejos.robotics.subsumption.Behavior;

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
				break;
			}

		}
		return null;
	}

	public void goToNextPath(Cell goalCell) {
		ArrayList<Cell> path = (ArrayList<Cell>) AStar(map, goalCell, me.stateCell);
		for(int i =0; i < path.size();i++) {
			Cell nextCell = path.get(i);

			if(nextCell.x != me.stateCell.x) {
				TravelToCell("x", nextCell);
			}
			if(nextCell.y != me.stateCell.y) {
				//Rotate to desired location
				TravelToCell("y", nextCell);
			}
			
			if(me.getDistance() < 0.08) {
				me.assiningObstacle();
				goToNextPath(goalCell);
				break;
			}
		}
	}
	
	public void TravelToCell(String axis, Cell nextCell) {
		int side = me.getDirectionHeading();
		if(axis.equals("x")) {
			if(nextCell.x > me.stateCell.x) {
				if(side != 1)
					AdjustMyPosition(1, side);
			else 
				if(side != 3)
					AdjustMyPosition(3, side);
			}
			me.getPilot().travel(25);
			me.stateCell.x = nextCell.x;
		}
		else {
			if(nextCell.y > me.stateCell.y) {
				if(side != 0)
					AdjustMyPosition(0, side);
			else 
				if(side != 2)
					AdjustMyPosition(2, side);
			}
			me.getPilot().travel(25);
			me.stateCell.y = nextCell.y;
		}
	}
	
	public void AdjustMyPosition(int desired, int now) {
		while(now != desired) {
			me.turnMotors(90);
			now = me.getDirectionHeading();
		}
	}
	
	public static List<Cell> AStar(Cell sucessors[][], Cell goal, Cell state) {
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
				AddingSucessors(frontier, lowerCost, sucessors);
			}
		}
		return null;
	}
	
	
	public static int cost(ArrayList<ArrayList<Cell>> frontier, Cell goal) {
		int min = 0;
		int auxIndex = 0;
		for(int i = 0;i < frontier.size(); i++ ) {
			Cell lastCellOfPath = frontier.get(i).get(frontier.get(i).size() - 1);
			if(!lastCellOfPath.obstacle) {
				int g = lastCellOfPath.x + lastCellOfPath.y;
				int h = (goal.x -  lastCellOfPath.x) +  (goal.y -lastCellOfPath.y);
				if( g+h < min){
					min = g+h;
					auxIndex = i;
				}
			}
		}
		return auxIndex;
	}
	
	public static void AddingSucessors(ArrayList<ArrayList<Cell>> frontier, ArrayList<Cell> lowerCost, Cell sucessors[][]) {
		Cell lastCell = lowerCost.get(lowerCost.size() - 1);
		if((lastCell.x + 1) <= 5) {
			ArrayList<Cell> aux = (ArrayList<Cell>) lowerCost.clone();
			aux.add(sucessors[lastCell.y][lastCell.x + 1]);
			frontier.add(aux); 

		}else if((lastCell.x -1) >= 0) {
			ArrayList<Cell> aux =(ArrayList<Cell>) lowerCost.clone();
			aux.add(sucessors[lastCell.y][lastCell.x - 1]);
			frontier.add(aux);
		}
		if((lastCell.y + 1) <= 6 ) {
			ArrayList<Cell> aux = (ArrayList<Cell>) lowerCost.clone();
			aux.add(sucessors[lastCell.y + 1 ][lastCell.x]);
			frontier.add(aux);

		}else if((lastCell.y -1) >= 0) {
			ArrayList<Cell> aux = (ArrayList<Cell>) lowerCost.clone();
			aux.add(sucessors[lastCell.y - 1][lastCell.x]);
			frontier.add(aux);
		}
		
	}
	
	public static ArrayList<ArrayList<Cell>> removeElement(ArrayList<ArrayList<Cell>> frontier, List<Cell> lowerCost) {
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
