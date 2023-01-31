package maze;

public class Cell {
    private final int row;
    private final int col;
    private boolean visited;
    private String type;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.visited = false;
        this.type = "";
    }

    public void setVisited() {
        this.visited = true;
    }

    public boolean isVisited() {
        return this.visited;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    @Override
    public boolean equals(Object compared) {
        if (this == compared) {
            return true;
        }
        if (!(compared instanceof Cell comparedCell)) {
            return false;
        }
        return this.row == comparedCell.row
                && this.col == comparedCell.col;
    }
}
