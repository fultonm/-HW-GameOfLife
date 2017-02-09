import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SeedPatternParser {

	public static Cell[][] parse(String fileName, int boardSize) throws Exception {
		Scanner s = new Scanner(new File(fileName));
		ArrayList<Cell> seedCells = new ArrayList<Cell>();
		int xMin = Integer.MAX_VALUE, yMin = Integer.MAX_VALUE;
		int xMax = Integer.MIN_VALUE, yMax = Integer.MIN_VALUE;
		while (s.hasNextLine()) {
			String line = s.nextLine().replace(" ", "");
			String[] vals = line.split(",");
			if (vals.length != 2) {
				throw new Exception("Seed file has wrong format on line " + (seedCells.size() + 1) + ": " + line);
			}
			int x = Integer.parseInt(vals[0]);
			int y = Integer.parseInt(vals[1]);
			if (x < xMin) {
				xMin = x;
			}
			if (x > xMax) {
				xMax = x;
			}
			if (y < yMin) {
				yMin = y;
			}
			if (y > yMax) {
				yMax = y;
			}
			seedCells.add(new Cell(x, y, true));
		}
		if (xMax - xMin > boardSize || yMax - yMin > boardSize) {
			throw new Exception("The seed pattern is too big for the current board size");
		}
		int xBuf = (boardSize - (xMax - xMin) - 1) / 2;
		int yBuf = (boardSize - (yMax - yMin) - 1) / 2;
		ArrayList<Cell> movedCells = new ArrayList<Cell>();
		for (Cell cell : seedCells) {
			movedCells.add(new Cell(cell.getX() + xBuf, cell.getY() + yBuf, true));
		}
		Cell[][] board = new Cell[boardSize][boardSize];
		for (int x = 0; x < boardSize; x++) {
			for (int y = 0; y < boardSize; y++) {
				if (haveSeedCell(x, y, movedCells)) {
					board[x][y] = new Cell(x, y, true);
				} else {
					board[x][y] = new Cell(x, y, false);
				}
			}
		}
		return board;
	}
	
	public static boolean haveSeedCell(int x, int y, ArrayList<Cell> seedCells) {
		for(Cell cell : seedCells) {
			if (cell.getX() == x && cell.getY() == y) {
				return true;
			}
		}
		return false;
	}

}
