package maze;

import java.util.ArrayList;

public class MazeSolver {
    private final Cell[][] maze;

    public MazeSolver(Maze maze) {
        this.maze = new Cell[maze.getHeight()][maze.getWidth()];
        for (int r = 0; r < this.maze.length; r++) {
            for (int c = 0; c < this.maze[0].length; c++) {
                this.maze[r][c] = new Cell(r, c);
                this.maze[r][c].setType(maze.getMaze()[r][c].getType());
            }
        }
        solveMaze();
    }

    private void solveMaze() {
        ArrayList<Cell> path = new ArrayList<>();
        Cell startingCell = this.maze[getStartingRow()][0];
        Cell endingCell = this.maze[getEndingRow()][maze[0].length - 1];
        boolean solved = dfs(startingCell, endingCell, path);
        if (solved) {
            for (Cell cell : path) {
                this.maze[cell.getRow()][cell.getCol()].setType("//");
            }
        }
    }

    private boolean dfs(Cell current, Cell end, ArrayList<Cell> path) {
        path.add(current); // adds the current cell to the correct path
        if (current.equals(end)) {// if current cell equals ending cell, path is complete
            return true;
        }
        for (Cell neighbor : getNeighbours(current)) { // check each neighbour of the current cell
            if (!path.contains(neighbor)) {
                // repeat for each neighbour that isn't already in the path
                if (dfs(neighbor, end, path)) {
                    return true;
                }
            }
        }

        // if there are no neighbours left and current cell doesn't equal ending cell,
        // remove current cell from the path and return false
        path.remove(path.size() - 1);
        return false;
    }

    private ArrayList<Cell> getNeighbours(Cell cell) {
        ArrayList<Cell> neighbours = new ArrayList<>();
        int row = cell.getRow();
        int col = cell.getCol();
        if (row > 0 && !maze[row - 1][col].getType().equals(Maze.WALL)) {
            neighbours.add(maze[row - 1][col]);
        }
        if (row < maze.length - 1 && !maze[row + 1][col].getType().equals(Maze.WALL)) {
            neighbours.add(maze[row + 1][col]);
        }
        if (col > 0 && !maze[row][col - 1].getType().equals(Maze.WALL)) {
            neighbours.add(maze[row][col - 1]);
        }
        if (col < maze[0].length - 1 && !maze[row][col + 1].getType().equals(Maze.WALL)) {
            neighbours.add(maze[row][col + 1]);
        }
        return neighbours;
    }

    private int getStartingRow() {
        for (int r = 0; r < maze.length; r++) {
            if (maze[r][0].getType().equals(Maze.SPACE)) {
                return r;
            }
        }
        return -1;
    }

    private int getEndingRow() {
        for (int r = 0; r < maze.length; r++) {
            if (maze[r][maze[0].length - 1].getType().equals(Maze.SPACE)) {
                return r;
            }
        }
        return -1;
    }

    public void printSolvedMaze() {
        StringBuilder sbMaze = new StringBuilder();
        for (Cell[] cells : this.maze) {
            for (int c = 0; c < this.maze[0].length; c++) {
                sbMaze.append(cells[c].getType());
            }
            sbMaze.append("\n");
        }
        System.out.println(sbMaze);
    }
}
