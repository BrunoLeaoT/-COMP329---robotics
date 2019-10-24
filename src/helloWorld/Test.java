package helloWorld;
import java.sql.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import helloWorld.Cell;

public class Test {
	static Cell map[][] = null;
	static Cell stateCell = new Cell(3,5);
	public static void main(String[] args) {
		map  = initializeMap(map);
		
//		Cell goal = new Cell(2,2); // Can't create with more than 6 on y or 5 on x 
//		Cell state = new Cell(1,1);
//		map[2][2].obstacle = true;
//		//map[0][2].obstacle = true;
//		List<Cell> path = AStar(map, goal, state);
//		for(int i=0;i<path.size();i++) {
//			Cell aux = path.get(i);
//			System.out.println("Cell: " + aux.y + ","+ aux.x);
//		}
//		System.out.println("-------------");
//		goal = new Cell(1,2); // Can't create with more than 6 on y or 5 on x 
//		state = new Cell(2,5);
//		map[1][3].obstacle = true;
//		path = AStar(map, goal, state);
//		for(int i=0;i<path.size();i++) {
//			Cell aux = path.get(i);
//			System.out.println("Cell: " + aux.y + ","+ aux.x);
//		}
		//adjustPosition(90);
		float distance = (float) 0.8;
		probabilisticModel(distance);
		distance = (float) 0.55;
		probabilisticModel(distance);
    	for(int i = 0;i<8;i++) {
    		System.out.println(map[i][1].probabilityOccupied +" - " + map[i][2].probabilityOccupied + " - " +map[i][3].probabilityOccupied +" - "+ map[i][4].probabilityOccupied +" - "+  map[i][5].probabilityOccupied +" - "+  map[i][6].probabilityOccupied);
    	}
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
		if((lastCell.x + 1) <= 6) {
			ArrayList<Cell> aux = (ArrayList<Cell>) lowerCost.clone();
			aux.add(sucessors[lastCell.y][lastCell.x + 1]);
			if(!map[(aux.get(aux.size()-1).y)][(aux.get(aux.size()-1).x)].obstacle || checkIfIsGoal(goal, (aux.get(aux.size()-1))) )
				frontier.add(aux); 
		}
		if((lastCell.x -1) >= 1 ) {
			ArrayList<Cell> aux =(ArrayList<Cell>) lowerCost.clone();
			aux.add(sucessors[lastCell.y][lastCell.x - 1]);
			if(!map[(aux.get(aux.size()-1).y)][(aux.get(aux.size()-1).x)].obstacle || checkIfIsGoal(goal, (aux.get(aux.size()-1))) )
				frontier.add(aux);
		}
		if((lastCell.y + 1) <= 7) {
			ArrayList<Cell> aux = (ArrayList<Cell>) lowerCost.clone();
			aux.add(sucessors[lastCell.y + 1 ][lastCell.x]);
			if(!map[(aux.get(aux.size()-1).y)][(aux.get(aux.size()-1).x)].obstacle || checkIfIsGoal(goal,(aux.get(aux.size()-1))) )
				frontier.add(aux);
		}
		if((lastCell.y -1) >= 1) {
			ArrayList<Cell> aux = (ArrayList<Cell>) lowerCost.clone();
			aux.add(sucessors[lastCell.y - 1][lastCell.x]);
			if(!map[(aux.get(aux.size()-1).y)][(aux.get(aux.size()-1).x)].obstacle || checkIfIsGoal(goal,(aux.get(aux.size()-1))) )
				frontier.add(aux);
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
    public static Cell[][] initializeMap(Cell map [][]) {
    	map = new Cell[8][7];
    	for(int i = 0;i<8;i++) {
    		for(int j = 0;j<7;j++) {
    			map[i][j] = new Cell(1,1);
        		map[i][j].x = j;
        		map[i][j].y = i;
        	}
    	}
    	return map;
    }
    
    public static void probabilisticModel(float distance) {
    	// Understand heading
    	int head = getDirectionHeading(630);
    	ArrayList<Cell> cellsToUpdate = new ArrayList<>();
    	int x,y;
    	System.out.println("Heading: " + head);
    	switch (head) {
		case 0: // Facing the initial position
			y = stateCell.y;
		  	if(!(distance - (0.25*(8-y)) > 0.25)) {
				for(int i=y; i < (8); i++) {
					int j = 1;
					cellsToUpdate.add(map[i][stateCell.x]);
					if(i - stateCell.y >=3) {
						if(stateCell.x + j <= 6) {
							cellsToUpdate.add(map[i][stateCell.x + j]);
						}
						if( stateCell.x -j >= 1) {
							cellsToUpdate.add(map[i][stateCell.x - j]);
						}
						j++;
					}
				}
				calcProbForAllCells(cellsToUpdate, distance, head);
		  	}
			break;
		case 1: // Turned to the right from the initial position
			x = stateCell.x;
	    	// Calculate if its a wall, // If yes, break. If not get which cells are being affected.
			if(!(distance - (0.25*(7-x)) > 0.25)) {
				for(int i=x; i < (7); i++) {
					int j = 1;
					cellsToUpdate.add(map[stateCell.y][i]);
					if(i - stateCell.x >=3) {
						if(stateCell.y + j <= 7) {
							cellsToUpdate.add(map[stateCell.y + j][i]);
						}
						if( stateCell.y - j >= 1) {
							cellsToUpdate.add(map[stateCell.y - j][i]);
						}
						j++;
					}
				}
				calcProbForAllCells(cellsToUpdate, distance, head);
			}
			break;
			
		case 2: // Turned 180 from the initial position
			y = stateCell.y;
			if(!((distance - (0.25*(y))) > 0.25)) {
				for(int i=y; i >= 0;i--) {
					int j =1;
					cellsToUpdate.add(map[i][stateCell.x]);
					if(stateCell.y - i >=3) {
						if(stateCell.x + j <= 6) {
							cellsToUpdate.add(map[i][stateCell.x + j]);
						}
						if( stateCell.x -j >= 1) {
							cellsToUpdate.add(map[i][stateCell.x - j]);
						}
						j++;
					}
				}
				calcProbForAllCells(cellsToUpdate, distance, head);
			}
			break;
			
		case 3: // Turned to the left from the initial position
			x = stateCell.x;
			if((((0.25*x) - distance) > 0.25)) {
				for(int i=x; i >= 0;i--) {
					int j = 1;
					cellsToUpdate.add(map[stateCell.y][i]);
					if(stateCell.x - i >=3) {
						if(stateCell.y + j <= 6) {
							cellsToUpdate.add(map[stateCell.y + j][i]);
						}
						if( stateCell.y - j >= 1) {
							cellsToUpdate.add(map[stateCell.y - j][i]);
						}
						j++;
					}
				}
			}
				for(int i = 0;i<cellsToUpdate.size();i++) {
					Cell aux = cellsToUpdate.get(i);
					System.out.println(aux.y + ":" + aux.x);
				}
			calcProbForAllCells(cellsToUpdate, distance, head);
			break;
		default:
			break;
		}	
    }
    
    public static void calcProbForAllCells(ArrayList<Cell> cells,float distance, int head) {
    	Cell state = cells.get(0);
    	for(int i = 1;i<cells.size();i++) {
			Cell aux = cells.get(i);
			
    		switch (head) {
			case 0:
				if(aux.y * 0.26 > (distance) && aux.y * 0.26 < (distance+0.26)) { // At the obstacle
					probEmptyAndOccupied(aux,distance, getAngle(state, aux), true);
				}
				else if(aux.y * 0.26 < distance) {
					probEmptyAndOccupied(aux,distance, getAngle(state, aux), false);
				}
				else {
					// CHange to callnig bayesRUle with 0.5 0.5
					bayesRule(aux, (float) 0.5,(float) 0.5, false, true);
				}
				break;
			case 1:
				if(aux.x * 0.26 > (distance) && aux.x * 0.26 < (distance+0.26)) { // At the obstacle
					probEmptyAndOccupied(aux,distance, getAngle(state, aux), true);
				}
				else if(aux.y * 0.26 < distance) { // Before obstacle
					probEmptyAndOccupied(aux,distance, getAngle(state, aux), false);
				}
				else {
					bayesRule(aux, (float) 0.5,(float) 0.5, false, true);
				}
				break;
			case 2:
				if((state.y-aux.y) * 0.26 > (distance) && (state.y-aux.y) * 0.26 < (distance+0.26)) { // After the obstacle
					probEmptyAndOccupied(aux,distance, getAngle(state, aux), true);
				}
				else if((state.y-aux.y) * 0.26 < distance) {
					probEmptyAndOccupied(aux,distance, getAngle(state, aux), false);
				}
				else {
					bayesRule(aux, (float) 0.5,(float) 0.5, false, true);
				}
				break;
			case 3:
				if((state.x-aux.x) * 0.26 > (distance) && (state.x-aux.x) * 0.26 < (distance+0.26)) { // At the obstacle
					System.out.println("At:");
					probEmptyAndOccupied(aux, distance, getAngle(state, aux), true);
				}
				else if((state.x-aux.x)* 0.26 < distance) { // Before obstacle
					System.out.println("Before: ");
					probEmptyAndOccupied(aux, distance, getAngle(state, aux), false);
				}
				else {
					bayesRule(aux, (float) 0.5,(float) 0.5, false, true);
				}
				break;
			default:
				break;
			}
    	}
    }
    
    public static float getAngle(Cell target, Cell now) {
        float angle = (float) Math.toDegrees(Math.atan2(target.y - now.y, target.x - now.x));

        if(angle < 0){
            angle = Math.abs(angle);
        }

        return angle;
    }
    
    public static void probEmptyAndOccupied(Cell cell, float distance, float angle, boolean inLineWithObstacle) {
    	double empty;
    	double occupied;
    	System.out.println("Distance: " + distance + " Angle: " + angle);
    	if(inLineWithObstacle) {
        	occupied = ((180 - distance)/180 + (15 - angle)/15)/2;
        	empty = (float) (1.0 - occupied);
        	bayesRule(cell, (float)empty,(float)occupied, true, false);
    	}
    	else {
        	empty = ((180 - distance)/180 + (15 - angle)/15)/2;
        	occupied = (float) (1.0 - empty);
        	bayesRule(cell, (float)empty,(float)occupied, false, false);
    	}
    	DecimalFormat df = new DecimalFormat("#.##");  
    	System.out.println(df.format(empty) + " " + df.format(occupied));
    	// Should Calculate now the second part, based on the last one and change in the map
    	
    	
    	
    }
    
    public static void bayesRule(Cell cell, float empty, float occupied,boolean occupiedB, boolean afterObstacle ) {
    	float newOccupied = (float) 0.5;
    	float newEmpty = (float) 0.5;
    	if(afterObstacle) {
    		if(cell.probabilityEmpty < cell.probabilityOccupied) {
    			newOccupied = (map[cell.y][cell.x].probabilityOccupied * occupied)/(map[cell.y][cell.x].probabilityOccupied * occupied + map[cell.y][cell.x].probabilityEmpty * empty);
    	    	
    		}
    		else {
    			newEmpty = (map[cell.y][cell.x].probabilityEmpty * empty)/(map[cell.y][cell.x].probabilityOccupied * occupied + map[cell.y][cell.x].probabilityEmpty * empty);
            	
    		}
    	}
    	else {
    		if(occupiedB) {
    			newOccupied = (map[cell.y][cell.x].probabilityOccupied * occupied)/(map[cell.y][cell.x].probabilityOccupied * occupied + map[cell.y][cell.x].probabilityEmpty * empty);
	    	}
	    	else {
	        	if(occupiedB) {
	        		newEmpty = (map[cell.y][cell.x].probabilityEmpty * empty)/(map[cell.y][cell.x].probabilityOccupied * occupied + map[cell.y][cell.x].probabilityEmpty * empty);
	        	}
	    	}

    	}
    	
    	map[cell.y][cell.x].probabilityEmpty = newEmpty;
    	map[cell.y][cell.x].probabilityOccupied = newOccupied;
    }
    
	public static int getDirectionHeading(float angle) {
		int side;
		if(angle>360)
			side = Math.round(((angle%360)/90));
		else
			side = Math.round(angle/90);
		if(side == 4 || side == -1) {
			side = 0;
		}
		return side;
	}
}
