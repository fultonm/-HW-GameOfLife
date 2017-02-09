
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.Scanner;

public class GameOfLife {

    /**
     *
     */
    public static final Color COLOR_ALIVE = new Color(153, 204, 255);
    public static final Color COLOR_DEAD = Color.WHITE;
    /**
     * The number of milliseconds to pause between each frame
     */
    public static final int SLEEP_TIME = 50;
    /**
     * The number of game ticks to execute before rendering a frame
     */
    public static final int TICKS_PER_FRAME = 1;
    /**
     * The number of rows and columns
     */
    public static final int BOARD_SIZE = 70;
    /**
     * DrawingPanel window size in pixels
     */
    public static final int SCRN_SIZE = 800;
    /**
     * The size of each cell
     */
    public static final int SQ_SIZE = SCRN_SIZE / BOARD_SIZE;
    /**
     * The random generator for setting initial state
     */
    public static final Random r = new Random();
    /**
     *
     */
    public static final Scanner s = new Scanner(System.in);
    /**
     * The game board which holds each cell
     */
    public static Cell[][] board = new Cell[BOARD_SIZE][BOARD_SIZE];
    /**
     * The graphics engine
     */
    public static DrawingPanel dp;
    /**
     * The Graphics object used for displaying cells
     */
    public static Graphics g;

    /**
     * The main game loop
     *
     * @param args Not used
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // Let the first argument be the file name of the seed pattern.
        board = args.length > 0
                ? initializeBoard(args[0]) : initializeBoard(null);
        startDrawingPanel();
        do {
            draw();
            for (int i = 0; i < TICKS_PER_FRAME; i++) {
                tick();
            }
        } while (true);
    }

    private static void startDrawingPanel() {
        dp = new DrawingPanel(SCRN_SIZE, SCRN_SIZE);
        g = dp.getGraphics();
    }

    /**
     * Check each cell for transition conditions and populate a new board
     * accordingly, then replace the old board
     */
    private static void tick() {
        Cell[][] newBoard = new Cell[BOARD_SIZE][BOARD_SIZE];
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                Cell cell = board[x][y];
                int liveNeighbors = countLiveNeighbors(cell);
                if (cell.isAlive() && (liveNeighbors < 2 || liveNeighbors > 3)) {
                    newBoard[x][y] = new Cell(x, y, false);
                } else if (cell.isAlive() && (liveNeighbors == 2 || liveNeighbors == 3)) {
                    newBoard[x][y] = new Cell(x, y, true);
                } else if (!cell.isAlive() && liveNeighbors == 3) {
                    newBoard[x][y] = new Cell(x, y, true);
                } else {
                    newBoard[x][y] = new Cell(x, y, false);
                }
            }
        }
        board = newBoard;
    }

    /**
     * Checks each neighbor
     *
     * @param cell
     * @return
     */
    private static int countLiveNeighbors(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        int liveNeighbors = 0;
        if (x > 0) {
            if (board[x - 1][y].isAlive()) {
                liveNeighbors++;
            }
            if (y > 0) {
                if (board[x - 1][y - 1].isAlive()) {
                    liveNeighbors++;
                }
            }
            if (y < BOARD_SIZE - 1) {
                if (board[x - 1][y + 1].isAlive()) {
                    liveNeighbors++;
                }
            }
        }
        if (x < BOARD_SIZE - 1) {
            if (board[x + 1][y].isAlive()) {
                liveNeighbors++;
            }
            if (y > 0) {
                if (board[x + 1][y - 1].isAlive()) {
                    liveNeighbors++;
                }
            }
            if (y < BOARD_SIZE - 1) {
                if (board[x + 1][y + 1].isAlive()) {
                    liveNeighbors++;
                }
            }
        }
        if (y > 0) {
            if (board[x][y - 1].isAlive()) {
                liveNeighbors++;
            }
        }
        if (y < BOARD_SIZE - 1) {
            if (board[x][y + 1].isAlive()) {
                liveNeighbors++;
            }
        }
        return liveNeighbors;
    }

    /**
     *
     */
    private static void draw() {
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                Cell cell = board[x][y];
                if (cell.isAlive()) {
                    g.setColor(COLOR_ALIVE);
                } else {
                    g.setColor(COLOR_DEAD);
                }
                g.fillRect(x * SQ_SIZE, y * SQ_SIZE, SQ_SIZE, SQ_SIZE);
            }
        }
        dp.sleep(SLEEP_TIME);
    }

    /**
     * @throws Exception
     *
     */
    private static Cell[][] initializeBoard(String seedFile) throws Exception {
        Cell[][] seedPattern = new Cell[BOARD_SIZE][BOARD_SIZE];
        //System.out.println("Do you want to use a seed pattern? (Y/n)");
        //String response = s.nextLine();
        //if (response.toLowerCase().equals("y")) {
        if (seedFile != null && !seedFile.equals("")) {
            System.out.format("%nThe seed file must have one comma-seperated x, y value per line. Example:%n"
                    + "%n2, 3%n" + "3, 3%n" + "2, 4%n" + "3, 4%n"
                    + "%nThis seed file would make a square of live cells from 2, 3 to 3, 4%n");

            //System.out.format("%nEnter the name of the seed file: ");
            //String fileName = s.nextLine();
            //Cell[][] p = SeedPatternParser.parse(fileName, BOARD_SIZE);
            seedPattern = SeedPatternParser.parse(seedFile, BOARD_SIZE);
            return seedPattern;
        } else {
            for (int x = 0; x < BOARD_SIZE; x++) {
                for (int y = 0; y < BOARD_SIZE; y++) {
                    Cell sq = new Cell(x, y, r.nextBoolean());
                    seedPattern[x][y] = sq;
                }
            }
            return seedPattern;
        }

    }

}
