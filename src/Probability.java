import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.Array;

public class Probability {
	
	PilotRobot robot;
		
	public Probability(PilotRobot me) {
		robot = me;
	}
	  public void probabilisticModel(float distance) {
	    	// Understand heading
	    	int head = robot.getDirectionHeading();
	    	ArrayList<Cell> cellsToUpdate = new ArrayList<>();
	    	int x,y;
	    	switch (head) {
			case 0: // Facing the initial position
				y = robot.stateCell.y;
			  	if(!(distance - (0.25*(8-y)) > 0.25)) {
					for(int i=y; i < (8); i++) {
						int j = 1;
						cellsToUpdate.add(robot.map[i][robot.stateCell.x]);
						if(i - robot.stateCell.y >=3) {
							if(robot.stateCell.x + j <= 6) {
								cellsToUpdate.add(robot.map[i][robot.stateCell.x + j]);
							}
							if( robot.stateCell.x -j >= 1) {
								cellsToUpdate.add(robot.map[i][robot.stateCell.x - j]);
							}
							j++;
						}
					}
					calcProbForAllCells(cellsToUpdate, distance, head);
			  	}
				break;
			case 1: // Turned to the right from the initial position
				x = robot.stateCell.x;
		    	// Calculate if its a wall, // If yes, break. If not get which cells are being affected.
				if(!(distance - (0.25*(7-x)) > 0.25)) {
					for(int i=x; i < (7); i++) {
						int j = 1;
						cellsToUpdate.add(robot.map[robot.stateCell.y][i]);
						if(i - robot.stateCell.x >=3) {
							if(robot.stateCell.y + j <= 7) {
								cellsToUpdate.add(robot.map[robot.stateCell.y + j][i]);
							}
							if( robot.stateCell.y - j >= 1) {
								cellsToUpdate.add(robot.map[robot.stateCell.y - j][i]);
							}
							j++;
						}
					}
					calcProbForAllCells(cellsToUpdate, distance, head);
				}
				break;
				
			case 2: // Turned 180 from the initial position
				y = robot.stateCell.y;
				if(!((distance - (0.25*(y))) > 0.25)) {
					for(int i=y; i >= 1;i--) {
						int j =1;
						cellsToUpdate.add(robot.map[i][robot.stateCell.x]);
						if(robot.stateCell.y - i >=3) {
							if(robot.stateCell.x + j <= 6) {
								cellsToUpdate.add(robot.map[i][robot.stateCell.x + j]);
							}
							if( robot.stateCell.x -j >= 1) {
								cellsToUpdate.add(robot.map[i][robot.stateCell.x - j]);
							}
							j++;
						}
					}
					calcProbForAllCells(cellsToUpdate, distance, head);
				}
				break;
				
			case 3: // Turned to the left from the initial position
				x = robot.stateCell.x;
				if((((0.25*x) - distance) > 0.25)) {
					for(int i=x; i >= 1;i--) {
						int j = 1;
						cellsToUpdate.add(robot.map[robot.stateCell.y][i]);
						if(robot.stateCell.x - i >=3) {
							if(robot.stateCell.y + j <= 6) {
								cellsToUpdate.add(robot.map[robot.stateCell.y + j][i]);
							}
							if( robot.stateCell.y - j >= 1) {
								cellsToUpdate.add(robot.map[robot.stateCell.y - j][i]);
							}
							j++;
						}
					}
				}
					for(int i = 0;i<cellsToUpdate.size();i++) {
						Cell aux = cellsToUpdate.get(i);
					}
				calcProbForAllCells(cellsToUpdate, distance, head);
				break;
			default:
				break;
			}	
	    }
	    
	    public void calcProbForAllCells(ArrayList<Cell> cells,float distance, int head) {
	    	Cell state = null;
	    	if(cells.size() > 0) {
	    		state = cells.get(0);
		    	for(int i = 0;i < cells.size()-1;i++) {
					Cell aux = cells.get(i);
		    		switch (head) {
					case 0:
						if(aux.y * 0.26 > (distance) && aux.y * 0.26 < (distance+0.26)) { // At the obstacle
							probEmptyAndOccupied(aux,distance, getAngle(aux,state), true);
						}
						else if(aux.y * 0.26 < distance) {
							probEmptyAndOccupied(aux,distance, getAngle(aux, state), false);
						}
						else {
							// CHange to callnig bayesRUle with 0.5 0.5
							bayesRule(aux, (float) 0.5,(float) 0.5, false, true);
						}
						break;
					case 1:
						if(aux.x * 0.26 > (distance) && aux.x * 0.26 < (distance+0.26)) { // At the obstacle
							probEmptyAndOccupied(aux,distance, getAngle(aux, state), true);
						}
						else if(aux.y * 0.26 < distance) { // Before obstacle
							probEmptyAndOccupied(aux,distance, getAngle(aux,state), false);
						}
						else {
							bayesRule(aux, (float) 0.5,(float) 0.5, false, true);
						}
						break;
					case 2:
						if((state.y-aux.y) * 0.26 > (distance) && (state.y-aux.y) * 0.26 < (distance+0.26)) { // After the obstacle
							probEmptyAndOccupied(aux,distance, getAngle(aux, state), true);
						}
						else if((state.y-aux.y) * 0.26 < distance) {
							probEmptyAndOccupied(aux,distance, getAngle(aux,state), false);
						}
						else {
							bayesRule(aux, (float) 0.5,(float) 0.5, false, true);
						}
						break;
					case 3:
						if((state.x-aux.x) * 0.26 > (distance) && (state.x-aux.x) * 0.26 < (distance+0.26)) { // At the obstacl
							probEmptyAndOccupied(aux, distance, getAngle(aux, state), true);
						}
						else if((state.x-aux.x)* 0.26 < distance) { // Before obstacle
							probEmptyAndOccupied(aux, distance, getAngle(aux, state), false);
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
	    
	    }
	    
	    public float getAngle(Cell target, Cell now) {
	        float angle = (float) Math.toDegrees(Math.atan2(target.x - now.x, target.y - now.y));

	        if(angle < 0){
	            angle = Math.abs(angle);
	        }

	        return angle;
	    }
	    
	    public void probEmptyAndOccupied(Cell cell, float distance, float angle, boolean inLineWithObstacle) {
	    	double empty;
	    	double occupied;
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
	    	// Should Calculate now the second part, based on the last one and change in the map
	    	
	    	
	    	
	    }
	    
	    public void bayesRule(Cell cell, float empty, float occupied,boolean occupiedB, boolean afterObstacle ) {
	    	float newOccupied = (float) 0.5;
	    	float newEmpty = (float) 0.5;
	    	if(afterObstacle) {
	    		if(cell.probabilityEmpty < cell.probabilityOccupied) {
	    			newOccupied = (robot.map[cell.y][cell.x].probabilityOccupied * occupied)/(robot.map[cell.y][cell.x].probabilityOccupied * occupied + robot.map[cell.y][cell.x].probabilityEmpty * empty);
	    	    	
	    		}
	    		else {
	    			newEmpty = (robot.map[cell.y][cell.x].probabilityEmpty * empty)/(robot.map[cell.y][cell.x].probabilityOccupied * occupied + robot.map[cell.y][cell.x].probabilityEmpty * empty);
	            	
	    		}
	    	}
	    	else {
	    		if(occupiedB) {
	    			newOccupied = (robot.map[cell.y][cell.x].probabilityOccupied * occupied)/(robot.map[cell.y][cell.x].probabilityOccupied * occupied + robot.map[cell.y][cell.x].probabilityEmpty * empty);
		    	}
		    	else {
		        	newEmpty = (robot.map[cell.y][cell.x].probabilityEmpty * empty)/(robot.map[cell.y][cell.x].probabilityOccupied * occupied + robot.map[cell.y][cell.x].probabilityEmpty * empty);
		    	}

	    	}
	    	
	    	robot.map[cell.y][cell.x].probabilityEmpty = newEmpty;
	    	robot.map[cell.y][cell.x].probabilityOccupied = newOccupied;
	    }
}
