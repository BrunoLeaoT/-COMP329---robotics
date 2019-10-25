class Cell{
	int x;
	int y;
	boolean visited;
	boolean obstacle;
	float probabilityEmpty;
	float probabilityOccupied;
	public Cell(int y ,int x) {
		this.x = x;
		this.y = y ;
		visited = false;
		obstacle = false;
		probabilityEmpty = (float) 0.5;
		probabilityOccupied = (float) 0.5;
	}
	
	public void setVisited(boolean state) {
		visited = state;
	}
	
	public boolean getVisited() {
		return visited;
	}
};

class Paths{
	Cell first = new Cell(2,2);
	Cell second = new Cell(2,5);
	Cell third = new Cell(6,5);
	Cell fourth = new Cell(6,2);
	Cell five = new Cell(2,1);
}