package maze;

import java.util.ArrayList;
import java.util.Random;

public class Maze {
    private Cell[][] maze;
    private Cell[][] smallerMaze;
    static final String WALL = "██";
    static final String SPACE = "  ";
    private int HEIGHT;
    private int WIDTH;

    public Maze(int size) {
        HEIGHT = size - 2;
        WIDTH = size - 2;
        this.maze = new Cell[HEIGHT + 2][WIDTH + 2];
        this.smallerMaze = new Cell[HEIGHT][WIDTH];
        generateMaze();
    }

    public Maze(String mazeToLoad) {
        loadMaze(mazeToLoad);
    }

    private void loadMaze(String mazeToLoad) {
        String[] rows = mazeToLoad.split("\n"); //splits the given maze by row
        HEIGHT = rows.length; // height is equal to the number of rows
        WIDTH = rows[0].length(); // width is equal to length of the rows
        this.maze = new Cell[HEIGHT][WIDTH / 2]; // initialize a new maze
        for (int r = 0; r < HEIGHT; r++) {
            int j = 0;
            for (int c = 0; c < WIDTH - 1; c += 2) {
                // String "s" is equal to char at (r, c) + char at (r, c+1)
                String s = String.valueOf(rows[r].charAt(c)) + rows[r].charAt(c + 1);
                this.maze[r][j] = new Cell(r, j); // initialize maze[r][c] with Cell(r, c)
                this.maze[r][j++].setType(s); // set the type of the newly initialized cell to "s"
            }
        }
    }

    public void generateMaze() {
        do {
            generateStartingMazes(); // generates the main maze and the smaller maze, initializing both with walls only
            generateSmallerMaze();  // generates the smaller maze using Prims' algorithm
            mergeMazes(); // merges the smaller maze with the main maze
            createPoints(); // creates the starting-point and ending-point of the main maze
        } while (!isLegalMaze()); // checks whether the maze doesn't contain 3x3 blocks
    }

    private void createPoints() {
        boolean startingPointIsDetermined = false;
        boolean endingPointIsDetermined = false;
        while (!startingPointIsDetermined || !endingPointIsDetermined) {
            int startingRow = new Random().nextInt(HEIGHT - 1) + 1;
            int endingRow = new Random().nextInt(HEIGHT - 1) + 1;

            if (!startingPointIsDetermined) { // chooses a random starting point
                if (this.maze[startingRow][1].getType().equals(SPACE)) {
                    this.maze[startingRow][0].setType(SPACE);
                    startingPointIsDetermined = true;
                }
            }
            if (!endingPointIsDetermined) { // chooses a random ending point
                if (this.maze[endingRow][WIDTH].getType().equals(SPACE)) {
                    this.maze[endingRow][WIDTH + 1].setType(SPACE);
                    endingPointIsDetermined = true;
                }
            }
        }
    }

    public Cell[][] getMaze() {
        return this.maze;
    }

    private void mergeMazes() {
        for (int r = 1; r <= HEIGHT; r++) {
            if (WIDTH >= 0) System.arraycopy(smallerMaze[r - 1], 0, this.maze[r], 1, WIDTH);
        }
    }

    private void generateSmallerMaze() { // generates a random maze using Prims' algorithm

        // list containing only cells which aren't visited yet
        ArrayList<Cell> walls = new ArrayList<>();

        // chooses a random starting cell
        Cell startingCell = smallerMaze[new Random().nextInt(HEIGHT)][new Random().nextInt(WIDTH)];

        // adds the starting-cell to the walls-list
        walls.add(startingCell);

        // sets the starting-cell to visited
        startingCell.setVisited();

        // iterates over the walls-list while it contains walls
        while (!walls.isEmpty()) {
            int numberOfVisitedCells = 0;

            // chooses a random cell from the walls-list
            Cell currentCell = walls.get(new Random().nextInt(walls.size()));

            // checks for each neighbour of the current cell if it's already visited
            for (Cell cell : getNeighbours(currentCell.getRow(), currentCell.getCol())) {
                if (cell.isVisited()) {
                    numberOfVisitedCells++;
                }
            }

            // creates a pass if and only if 0 or 1 of the current cells' neighbours are already visited
            if (numberOfVisitedCells < 2) {
                smallerMaze[currentCell.getRow()][currentCell.getCol()].setType(SPACE);
                smallerMaze[currentCell.getRow()][currentCell.getCol()].setVisited();

                // adds the unvisited neighbours to the list
                walls.addAll(getNeighbours(currentCell.getRow(), currentCell.getCol()));
            }

            // removes the visited cell from the wall-list
            walls.remove(currentCell);
        }
    }

    private void generateStartingMazes() {
        // initializes both the main maze as the smaller maze, both only containing walls
        for (int r = 0; r < maze.length; r++) {
            for (int c = 0; c < maze[0].length; c++) {
                this.maze[r][c] = new Cell(r, c);
                this.maze[r][c].setType(WALL);
                if (r < HEIGHT && c < WIDTH) {
                    this.smallerMaze[r][c] = new Cell(r, c);
                    this.smallerMaze[r][c].setType(WALL);
                }
            }
        }
    }

    private ArrayList<Cell> getNeighbours(int row, int col) {
        ArrayList<Cell> neighbours = new ArrayList<>();
        if (row + 1 < HEIGHT) {
            neighbours.add(smallerMaze[row + 1][col]);
        }
        if (row - 1 >= 0) {
            neighbours.add(smallerMaze[row - 1][col]);
        }
        if (col + 1 < WIDTH) {
            neighbours.add(smallerMaze[row][col + 1]);
        }
        if (col - 1 >= 0) {
            neighbours.add(smallerMaze[row][col - 1]);
        }
        return neighbours;  // returns a list of all legal neighbours from the given coordinates
    }

    private boolean isLegalMaze() {
        //checks if the maze contains 3x3 Blocks
        for (int r = 1; r <= HEIGHT; r++) {
            for (int c = 1; c <= WIDTH; c++) {
                if (maze[r + 1][c - 1].getType().equals(WALL) && maze[r + 1][c].getType().equals(WALL)
                        && maze[r + 1][c + 1].getType().equals(WALL) //checks row + 1
                        && maze[r][c - 1].getType().equals(WALL) && maze[r][c].getType().equals(WALL)
                        && maze[r][c + 1].getType().equals(WALL) //checks row
                        && maze[r - 1][c - 1].getType().equals(WALL) && maze[r - 1][c].getType().equals(WALL)
                        && maze[r - 1][c + 1].getType().equals(WALL)) // checks row - 1
                    return false;
            }
        }
        return true; // returns true if no 3x3 blocks exist
    }

    public int getHeight() {
        return this.maze.length;
    }

    public int getWidth() {
        return this.maze.length;
    }

    @Override
    public String toString() {
        StringBuilder sbMaze = new StringBuilder();
        for (Cell[] cell : maze) {
            for (int c = 0; c < maze[0].length; c++) {
                sbMaze.append(cell[c].getType());
            }
            sbMaze.append("\n");
        }
        return sbMaze.toString();
    }
}