package helloWorld;

class Cell{
	int x;
	int y;
	boolean visited;
	boolean obstacle;
	public Cell(int y ,int x) {
		this.x = x;
		this.y = y ;
		visited = false;
		obstacle = false;
	}
	
	public void setVisited(boolean state) {
		visited = state;
	}
	
	public boolean getVisited() {
		return visited;
	}
};

class Paths{
	Cell first = new Cell(1,1);
	Cell second = new Cell(1,4);
	Cell third = new Cell(5,4);
	Cell fourth = new Cell(5,1);
}