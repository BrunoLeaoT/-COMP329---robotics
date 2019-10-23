package helloWorld;

import java.util.ArrayList;
import java.util.List;

public class aStar {
	private PilotRobot me;
	List<Cell> path;
	static Cell map[][];
	boolean stop;
	// Constructor - store a reference to the robot
	public aStar(PilotRobot robot){
    	 me = robot;
    }
	public static List<Cell> AStar(Cell sucessors[][], Cell state, Cell goal ) {
		ArrayList<ArrayList<Cell>> frontier = new ArrayList<>();
		map = sucessors;
		frontier.add(new ArrayList<Cell>());
		frontier.get(0).add(state);
		while(frontier.size() > 0) {
			int index = cost(frontier, goal);
			ArrayList<Cell> lowerCost = frontier.get(index);
			frontier.remove(index);
			if(lowerCost.get(lowerCost.size()-1).x == goal.x && lowerCost.get(lowerCost.size()-1).y == goal.y) {
				System.out.println("Found the goal");
				return lowerCost;
			}
			else {
				AddingSucessors(frontier, lowerCost, map, goal);
			}
		}
		return null;
	}
	
	public static int cost(ArrayList<ArrayList<Cell>> frontier, Cell goal) {
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
	
	public static void AddingSucessors(ArrayList<ArrayList<Cell>> frontier, ArrayList<Cell> lowerCost, Cell sucessors[][], Cell goal) {
		Cell lastCell = lowerCost.get(lowerCost.size() - 1);
		if((lastCell.x + 1) <= 5) {
			ArrayList<Cell> aux = (ArrayList<Cell>) lowerCost.clone();
			aux.add(sucessors[lastCell.y][lastCell.x + 1]);
			if(!map[(aux.get(aux.size()-1).y)][(aux.get(aux.size()-1).x)].obstacle || checkIfIsGoal(goal, (aux.get(aux.size()-1))) )
				frontier.add(aux); 
		}
		if((lastCell.x -1) >= 0 ) {
			ArrayList<Cell> aux =(ArrayList<Cell>) lowerCost.clone();
			aux.add(sucessors[lastCell.y][lastCell.x - 1]);
			if(!map[(aux.get(aux.size()-1).y)][(aux.get(aux.size()-1).x)].obstacle || checkIfIsGoal(goal, (aux.get(aux.size()-1))) )
				frontier.add(aux);
				if(aux.get(aux.size()-1).y == 0 && aux.get(aux.size()-1).x == 0)
					System.out.println("Botei 0,0");
		}
		if((lastCell.y + 1) <= 6) {
			ArrayList<Cell> aux = (ArrayList<Cell>) lowerCost.clone();
			aux.add(sucessors[lastCell.y + 1 ][lastCell.x]);
			if(!map[(aux.get(aux.size()-1).y)][(aux.get(aux.size()-1).x)].obstacle || checkIfIsGoal(goal,(aux.get(aux.size()-1))) )
				frontier.add(aux);
		}
		if((lastCell.y -1) >= 0) {
			ArrayList<Cell> aux = (ArrayList<Cell>) lowerCost.clone();
			aux.add(sucessors[lastCell.y - 1][lastCell.x]);
			if(!map[(aux.get(aux.size()-1).y)][(aux.get(aux.size()-1).x)].obstacle || checkIfIsGoal(goal,(aux.get(aux.size()-1))) )
				frontier.add(aux);
				if(aux.get(aux.size()-1).y == 0 && aux.get(aux.size()-1).x == 0)
					System.out.println("Botei 0,0");
		}
		
	}
	
	public static boolean checkIfIsGoal(Cell test, Cell goal) {
		if(goal.x == test.x && goal.y == test.y) {
			return true;
		}
		return false;
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
