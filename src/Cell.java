public class Cell {
    private boolean alive;
    private int x, y;
    
    public Cell(int x, int y, boolean alive) {
    	this.x = x;
    	this.y = y;
    	this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }
    
    public int getX() {
    	return x;
    }
    
    public int getY() {
    	return y;
    }
}