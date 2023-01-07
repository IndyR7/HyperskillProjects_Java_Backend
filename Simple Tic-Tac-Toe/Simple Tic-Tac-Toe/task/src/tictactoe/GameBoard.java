package tictactoe;

public class GameBoard {
    private final String[][] gameBoard;
    private String currentPlayer;

    public GameBoard() {
        this.gameBoard = new String[][]{{" ", " ", " "},
                {" ", " ", " "},
                {" ", " ", " "}};
        currentPlayer = "O";
    }

    public boolean isNotFinished() {
        return !(isWinner() || isDraw());
    }

    private String getWinner() {
        if (isWinner()) {
            if (xWins()) {
                return "X";
            }
            return "O";
        }
        return "There is no winner!";
    }

    public boolean isWinner() {
        return xWins() || oWins();
    }

    private boolean xWins() {
        return checkRows("X") || checkColumns("X") || checkDiagonals("X");
    }

    private boolean oWins() {
        return checkRows("O") || checkColumns("O") || checkDiagonals("O");
    }

    private boolean isDraw() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (isEmpty(x, y)) {
                    return false;
                }
            }
        }
        return !isWinner();
    }

    private boolean checkRows(String input) {
        for (int x = 0; x < 3; x++) {
            if (gameBoard[x][0].equals(input) && gameBoard[x][1].equals(input) && gameBoard[x][2].equals(input)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumns(String input) {
        for (int y = 0; y < 3; y++) {
            if (gameBoard[0][y].equals(input) && gameBoard[1][y].equals(input) && gameBoard[2][y].equals(input)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonals(String input) {
        if (gameBoard[0][0].equals(input) && gameBoard[1][1].equals(input) && gameBoard[2][2].equals(input)) {
            return true;
        }
        return gameBoard[0][2].equals(input) && gameBoard[1][1].equals(input) && gameBoard[2][0].equals(input);
    }

    public boolean isEmpty(int x, int y) {
        return gameBoard[x][y].equals(" ");
    }

    public String getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void setCurrentPlayer() {
        if (this.currentPlayer.equals("X")) {
            this.currentPlayer = "O";
        } else {
            this.currentPlayer = "X";
        }
    }

    public void add(int x, int y, String player) {
        this.gameBoard[x][y] = player;
    }

    public void printResult() {
        printField();
        if (isDraw()) {
            System.out.println("Draw\n");
        } else if (isNotFinished()) {
            System.out.println("Game not finished\n");
        } else {
            System.out.println(getWinner() + " wins\n");
        }
    }

    public void printField() {
        System.out.println("---------");
        System.out.printf("| %s %s %s |\n", gameBoard[0][0], gameBoard[0][1], gameBoard[0][2]);
        System.out.printf("| %s %s %s |\n", gameBoard[1][0], gameBoard[1][1], gameBoard[1][2]);
        System.out.printf("| %s %s %s |\n", gameBoard[2][0], gameBoard[2][1], gameBoard[2][2]);
        System.out.println("---------");
    }
}
