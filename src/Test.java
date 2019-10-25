import java.sql.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Test {
	static Cell map[][] = null;
	static Cell stateCell = new Cell(1,1);
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
		DecimalFormat df = new DecimalFormat("#.##");  
		float distance = (float) 1.45;
		probabilisticModel(distance,0);
    	for(int i = 7;i>0;i--) {
    		System.out.println(df.format(map[i][1].probabilityOccupied) +" - " + df.format(map[i][2].probabilityOccupied) + " - " +df.format(map[i][3].probabilityOccupied) +" - "+ df.format(map[i][4].probabilityOccupied) +" - "+  df.format(map[i][5].probabilityOccupied) +" - "+  df.format(map[i][6].probabilityOccupied));
    	}
		distance = (float) 1.45;
		probabilisticModel(distance,1);
    	for(int i = 7;i>0;i--) {
    		System.out.println(df.format(map[i][1].probabilityOccupied) +" - " + df.format(map[i][2].probabilityOccupied) + " - " +df.format(map[i][3].probabilityOccupied) +" - "+ df.format(map[i][4].probabilityOccupied) +" - "+  df.format(map[i][5].probabilityOccupied) +" - "+  df.format(map[i][6].probabilityOccupied));
    	}
    	stateCell = new Cell(1,2);
		distance = (float) 0.8;
		probabilisticModel(distance,1);
    	for(int i = 7;i>0;i--) {
    		System.out.println(df.format(map[i][1].probabilityOccupied) +" - " + df.format(map[i][2].probabilityOccupied) + " - " +df.format(map[i][3].probabilityOccupied) +" - "+ df.format(map[i][4].probabilityOccupied) +" - "+  df.format(map[i][5].probabilityOccupied) +" - "+  df.format(map[i][6].probabilityOccupied));
    	}
    	
    	stateCell = new Cell(1,3);
		distance = (float) 0.55;
		probabilisticModel(distance,1);
    	for(int i = 7;i>0;i--) {
    		System.out.println(df.format(map[i][1].probabilityOccupied) +" - " + df.format(map[i][2].probabilityOccupied) + " - " +df.format(map[i][3].probabilityOccupied) +" - "+ df.format(map[i][4].probabilityOccupied) +" - "+  df.format(map[i][5].probabilityOccupied) +" - "+  df.format(map[i][6].probabilityOccupied));
    	}
    	
    	stateCell = new Cell(1,4);
		distance = (float) 0.3;
		probabilisticModel(distance,1);
    	for(int i = 7;i>0;i--) {
    		System.out.println(df.format(map[i][1].probabilityOccupied) +" - " + df.format(map[i][2].probabilityOccupied) + " - " +df.format(map[i][3].probabilityOccupied) +" - "+ df.format(map[i][4].probabilityOccupied) +" - "+  df.format(map[i][5].probabilityOccupied) +" - "+  df.format(map[i][6].probabilityOccupied));
    	}
    	
       	stateCell = new Cell(1,4);
		distance = (float) 1.0;
		probabilisticModel(distance,0);
    	for(int i = 7;i>0;i--) {
    		System.out.println(df.format(map[i][1].probabilityOccupied) +" - " + df.format(map[i][2].probabilityOccupied) + " - " +df.format(map[i][3].probabilityOccupied) +" - "+ df.format(map[i][4].probabilityOccupied) +" - "+  df.format(map[i][5].probabilityOccupied) +" - "+  df.format(map[i][6].probabilityOccupied));
    	}
    	
       	stateCell = new Cell(2,4);
		distance = (float) 0.75;
		probabilisticModel(distance,0);
    	for(int i = 7;i>0;i--) {
    		System.out.println(df.format(map[i][1].probabilityOccupied) +" - " + df.format(map[i][2].probabilityOccupied) + " - " +df.format(map[i][3].probabilityOccupied) +" - "+ df.format(map[i][4].probabilityOccupied) +" - "+  df.format(map[i][5].probabilityOccupied) +" - "+  df.format(map[i][6].probabilityOccupied));
    	}
       	stateCell = new Cell(3,4);
		distance = (float) 0.5;
		probabilisticModel(distance,0);
    	for(int i = 7;i>0;i--) {
    		System.out.println(df.format(map[i][1].probabilityOccupied) +" - " + df.format(map[i][2].probabilityOccupied) + " - " +df.format(map[i][3].probabilityOccupied) +" - "+ df.format(map[i][4].probabilityOccupied) +" - "+  df.format(map[i][5].probabilityOccupied) +" - "+  df.format(map[i][6].probabilityOccupied));
    	}
       	stateCell = new Cell(3,5);
		distance = (float) 0.25;
		probabilisticModel(distance,0);
    	for(int i = 7;i>0;i--) {
    		System.out.println(df.format(map[i][1].probabilityOccupied) +" - " + df.format(map[i][2].probabilityOccupied) + " - " +df.format(map[i][3].probabilityOccupied) +" - "+ df.format(map[i][4].probabilityOccupied) +" - "+  df.format(map[i][5].probabilityOccupied) +" - "+  df.format(map[i][6].probabilityOccupied));
    	}
    	
       	stateCell = new Cell(6,5);
		distance = (float) 0.25;
		probabilisticModel(distance,2);
    	for(int i = 7;i>0;i--) {
    		System.out.println(df.format(map[i][1].probabilityOccupied) +" - " + df.format(map[i][2].probabilityOccupied) + " - " +df.format(map[i][3].probabilityOccupied) +" - "+ df.format(map[i][4].probabilityOccupied) +" - "+  df.format(map[i][5].probabilityOccupied) +" - "+  df.format(map[i][6].probabilityOccupied));
    	}
    	
       	stateCell = new Cell(6,5);
		distance = (float) 0.5;
		probabilisticModel(distance,3);
    	for(int i = 7;i>0;i--) {
    		System.out.println(df.format(map[i][1].probabilityOccupied) +" - " + df.format(map[i][2].probabilityOccupied) + " - " +df.format(map[i][3].probabilityOccupied) +" - "+ df.format(map[i][4].probabilityOccupied) +" - "+  df.format(map[i][5].probabilityOccupied) +" - "+  df.format(map[i][6].probabilityOccupied));
    	}
       	stateCell = new Cell(6,4);
		distance = (float) 0.25;
		probabilisticModel(distance,3);
    	for(int i = 7;i>0;i--) {
    		System.out.println(df.format(map[i][1].probabilityOccupied) +" - " + df.format(map[i][2].probabilityOccupied) + " - " +df.format(map[i][3].probabilityOccupied) +" - "+ df.format(map[i][4].probabilityOccupied) +" - "+  df.format(map[i][5].probabilityOccupied) +" - "+  df.format(map[i][6].probabilityOccupied));
    	}
    	
       	stateCell = new Cell(6,1);
		distance = (float) 0.75;
		probabilisticModel(distance,1);
    	for(int i = 7;i>0;i--) {
    		System.out.println(df.format(map[i][1].probabilityOccupied) +" - " + df.format(map[i][2].probabilityOccupied) + " - " +df.format(map[i][3].probabilityOccupied) +" - "+ df.format(map[i][4].probabilityOccupied) +" - "+  df.format(map[i][5].probabilityOccupied) +" - "+  df.format(map[i][6].probabilityOccupied));
    	}
       	stateCell = new Cell(6,1);
		distance = (float) 1.2;
		probabilisticModel(distance,2);
    	for(int i = 7;i>0;i--) {
    		System.out.println(df.format(map[i][1].probabilityOccupied) +" - " + df.format(map[i][2].probabilityOccupied) + " - " +df.format(map[i][3].probabilityOccupied) +" - "+ df.format(map[i][4].probabilityOccupied) +" - "+  df.format(map[i][5].probabilityOccupied) +" - "+  df.format(map[i][6].probabilityOccupied));
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
    	for(int i = 1;i<8;i++) {
    		for(int j = 1;j<7;j++) {
    			map[i][j] = new Cell(1,1);
        		map[i][j].x = j;
        		map[i][j].y = i;
        	}
    	}
    	return map;
    }
    
    public static void probabilisticModel(float distance,int heading) {
    	// Understand heading
    	int head = heading;
    	ArrayList<Cell> cellsToUpdate = new ArrayList<>();
    	int x,y;
    	System.out.println("Heading: " + head);
    	switch (head) {
		case 0: // Facing the initial position
			y = stateCell.y;
		  	if(((0.25*(7-y)) - distance > 0.25) || distance > 0.2) {
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
				for(int i = 0;i<cellsToUpdate.size();i++) {
					Cell aux = cellsToUpdate.get(i);
					System.out.println(aux.y + ":" + aux.x);
				}
				calcProbForAllCells(cellsToUpdate, distance, head);
		  	}
			break;
		case 1: // Turned to the right from the initial position
			x = stateCell.x;
	    	// Calculate if its a wall, // If yes, break. If not get which cells are being affected.
			if(((0.25*(6-x) - distance) > 0.25) || distance > 0.2) {
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
				for(int i = 0;i<cellsToUpdate.size();i++) {
					Cell aux = cellsToUpdate.get(i);
					System.out.println(aux.y + ":" + aux.x);
				}
				calcProbForAllCells(cellsToUpdate, distance, head);
			}
			break;
			
		case 2: // Turned 180 from the initial position
			y = stateCell.y;
			if(( (0.25*(y-1)-distance) > 0.25) || distance > 0.2) {
				for(int i=y; i >= 1;i--) {
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
				for(int i = 0;i<cellsToUpdate.size();i++) {
					Cell aux = cellsToUpdate.get(i);
					System.out.println(aux.y + ":" + aux.x);
				}
				calcProbForAllCells(cellsToUpdate, distance, head);
			}
			break;
			
		case 3: // Turned to the left from the initial position
			x = stateCell.x;
			if((((0.25*(x-1) - distance) > 0.25)) || distance > 0.2) {
				for(int i=x; i >= 1;i--) {
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
    	if(cells.size()>0) {
	    	Cell state = cells.get(0);
	    	for(int i = 0;i<cells.size();i++) {
				Cell aux = cells.get(i);
				
	    		switch (head) {
				case 0:
					if(aux.y * 0.26 > (distance) && aux.y * 0.26 < (distance+0.3)) { // At the obstacle
						probEmptyAndOccupied(aux,distance, getAngle(aux, state,head), true);
					}
					else if(aux.y * 0.26 < distance) {
						probEmptyAndOccupied(aux,distance, getAngle(aux, state,head), false);
					}
					else {
						// CHange to callnig bayesRUle with 0.5 0.5
						bayesRule(aux, (float) 0.5,(float) 0.5);
					}
					break;
				case 1:
					if(aux.x * 0.26 > (distance) && aux.x * 0.26 < (distance+0.3)) { // At the obstacle
						probEmptyAndOccupied(aux,distance, getAngle(aux, state,head), true);
					}
					else if(aux.y * 0.26 < distance) { // Before obstacle
						probEmptyAndOccupied(aux,distance, getAngle(aux, state,head), false);
					}
					else {
						bayesRule(aux, (float) 0.5,(float) 0.5);
					}
					break;
				case 2:
					if((state.y-aux.y) * 0.26 > (distance) && (state.y-aux.y) * 0.26 < (distance+0.3)) { // After the obstacle
						probEmptyAndOccupied(aux,distance, getAngle(aux, state,head), true);
					}
					else if((state.y-aux.y) * 0.26 < distance) {
						probEmptyAndOccupied(aux,distance, getAngle(aux, state,head), false);
					}
					else {
						bayesRule(aux, (float) 0.5,(float) 0.5);
					}
					break;
				case 3:
					if((state.x-aux.x) * 0.26 > (distance) && (state.x-aux.x) * 0.26 < (distance+0.3)) { // At the obstacle
						System.out.println("At :" + aux.y +  ":" + aux.x);
						probEmptyAndOccupied(aux, distance, getAngle(aux, state,head), true);
					}
					else if((state.x-aux.x)* 0.26 < distance) { // Before obstacle
						System.out.println("Before: " + aux.y +  ":" + aux.x);
						probEmptyAndOccupied(aux, distance, getAngle(aux, state,head), false);
					}
					else {
						bayesRule(aux, (float) 0.5,(float) 0.5);
					}
					break;
				default:
					break;
				}
	    	}
    	}
    }
    
    public static float getAngle(Cell target, Cell now, int head) {
    	System.out.println(target.y +"-"+ now.y +"-"+ target.x +"-"+ now.x);
        double angle = 0;// = Math.atan2((target.x - now.x), (target.y - now.y)) * 180 / Math.PI;
        
        if(!(target.x == now.x || target.y == now.y))
        {
        	if(head == 1 || head == 3)
        		angle = Math.toDegrees(Math.atan2(Math.abs(target.y - now.y), Math.abs(target.x - now.x)));
        	else
        		angle = Math.toDegrees(Math.atan2( Math.abs(target.x - now.x),Math.abs(target.y - now.y)));
        }
        
        if (angle < 0) {
            return (float) (-1 * angle);
        } else {
            return (float) (angle);
        }
    }
    
    public static void probEmptyAndOccupied(Cell cell, float distance, float angle, boolean inLineWithObstacle) {
    	double empty;
    	double occupied;
    	System.out.println("Distance: " + distance + " Angle: " + angle);
    	if(inLineWithObstacle) {
        	occupied = (((180 - distance)/180 + (15 - angle)/15)/2) * 0.98;
        	empty = (float) (1.0 - occupied);
        	bayesRule(cell, (float)empty,(float)occupied);
    	}
    	else {
        	empty = ((180 - distance)/180 + (15 - angle)/15)/2;
        	occupied = (float) (1.0 - empty);
        	bayesRule(cell, (float)empty,(float)occupied);
    	}
    	DecimalFormat df = new DecimalFormat("#.##");  
    	System.out.println(df.format(empty) + " " + df.format(occupied));

    }
    
    public static void bayesRule(Cell cell, float empty, float occupied ) {
    	float newOccupied = (float) 0.5;
    	float newEmpty = (float) 0.5;
    	newOccupied = (map[cell.y][cell.x].probabilityOccupied * occupied)/(map[cell.y][cell.x].probabilityOccupied * occupied + map[cell.y][cell.x].probabilityEmpty * empty);
    	newEmpty = (map[cell.y][cell.x].probabilityEmpty * empty)/(map[cell.y][cell.x].probabilityOccupied * occupied + map[cell.y][cell.x].probabilityEmpty * empty);
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