package minesweeper;

import java.util.Random;

public class Game {
    private final String[][] gameBoard = new String[9][9];
    private final String[][] hiddenBoard = new String[9][9];
    private final int minesRequested;
    private boolean isRunning;
    private boolean playerWins;

    public Game(int minesRequested) {
        this.minesRequested = minesRequested;
        this.isRunning = true;
        this.playerWins = true;
        initGameBoards();
    }

    private void initGameBoards() {
        initEmptySpots();
        initMines();
        initCounts();
    }

    private void initEmptySpots() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                this.gameBoard[r][c] = ".";
                this.hiddenBoard[r][c] = ".";
            }
        }
    }

    private void initMines() {
        for (int i = 0; i < minesRequested; i++) {
            int r = new Random().nextInt(9);
            int c = new Random().nextInt(9);
            if (isEmpty(r, c)) {
                hiddenBoard[r][c] = "X";
                continue;
            }
            i--;
        }
    }

    private void initCounts() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (isEmpty(r, c) && getCount(r, c) != 0 && !isMine(r, c)) {
                    hiddenBoard[r][c] = String.valueOf(getCount(r, c));
                }
            }
        }
    }

    private int getCount(int row, int col) {
        int count = 0;
        for (int r = limit(row - 1); r <= limit(row + 1); r++) {
            for (int c = limit(col - 1); c <= limit(col + 1); c++) {
                if (isMine(r, c))
                    count++;
            }
        }
        return count;
    }

    private int limit(int limit) {
        return limit < 0 ? 0 : Math.min(limit, 8);
    }

    public boolean add(int x, int y, String command) {
        if (gameBoard[y][x].equals(hiddenBoard[y][x]) && !isEmpty(y, x)) {
            return false;
        }
        if (command.equalsIgnoreCase("mine")) {
            addMineMarker(y, x);
        }
        if (command.equalsIgnoreCase("free")) {
            addFreeMarker(y, x);
        }
        checkGameState();
        return true;
    }

    private void addMineMarker(int r, int c) {
        if (gameBoard[r][c].equals("*")) {
            this.gameBoard[r][c] = ".";
            return;
        }
        if (gameBoard[r][c].equals(".")) {
            this.gameBoard[r][c] = "*";
            this.hiddenBoard[r][c] = isEmpty(r, c) ? "*" : this.hiddenBoard[r][c];
        }
    }

    private void addFreeMarker(int r, int c) {
        if (isMine(r, c)) {
            this.playerWins = false;
            this.isRunning = false;
            return;
        }
        explore(r, c);
    }

    private void explore(int row, int col) {
        if (isNumber(row, col)) {
            this.gameBoard[row][col] = this.hiddenBoard[row][col];
        }
        if (isEmpty(row, col)) {
            this.gameBoard[row][col] = "/";
            this.hiddenBoard[row][col] = "/";
            for (int r = limit(row - 1); r <= limit(row + 1); r++) {
                for (int c = limit(col - 1); c <= limit(col + 1); c++) {
                    explore(r, c);
                }
            }
        }
    }

    private void checkGameState() {
        int emptySpots = 0;
        int minesGuessed = 0;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (this.gameBoard[r][c].equals(".")) {
                    emptySpots++;
                }
                if (this.gameBoard[r][c].equals("*") && isMine(r, c)) {
                    minesGuessed++;
                }
            }
        }
        if (emptySpots == minesRequested || minesGuessed == minesRequested) {
            this.isRunning = false;
        }
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    private boolean isNumber(int r, int c) {
        return this.hiddenBoard[r][c].matches("[0-9]");
    }

    private boolean isEmpty(int r, int c) {
        return this.hiddenBoard[r][c].matches("[.*]");
    }

    private boolean isMine(int r, int c) {
        return this.hiddenBoard[r][c].equals("X");
    }

    public void printField() {
        System.out.printf(" |123456789|\n-|---------|\n%s-|---------|\n", getGameState());
    }

    public void printResult() {
        System.out.printf("%s\n", playerWins ? "Congratulations! You found all the mines!"
                : "You stepped on a mine and failed!");
    }

    private String getGameState() {
        StringBuilder userField = new StringBuilder();
        StringBuilder hiddenField = new StringBuilder();
        int count = 1;
        for (int r = 0; r < 9; r++) {
            userField.append(count).append("|");
            hiddenField.append(count).append("|");
            for (int c = 0; c < 9; c++) {
                userField.append(gameBoard[r][c]);
                hiddenField.append(hiddenBoard[r][c]);
            }
            userField.append("|\n");
            hiddenField.append("|\n");
            count++;
        }
        return isRunning() ? userField.toString() : hiddenField.toString();
    }
}