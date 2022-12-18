package tictactoe;

public class GameBoard {
    private String[][] gameBoard;
    private int x;
    private int y;
    private boolean parsable;
    private String currentPlayer;


    public GameBoard() {
        this.gameBoard = convertToField();
        parsable = false;
        this.currentPlayer = "X";
    }
    public void takeTurn() {
        if (this.currentPlayer.equals("X")) {
            this.currentPlayer = "O";
        }
        else {
            this.currentPlayer = "X";
        }
        add();
        printField();
    }

    public void printResult() {
        printField();
        if (isDraw()) {
            System.out.println("Draw");
        } else if (!gameBoardIsLegal()) {
            System.out.println("Impossible");
        } else if (isNotFinished()) {
            System.out.println("Game not finished");
        } else {
            System.out.println(winner() + " wins");
        }
    }

    public void printField() {
        for (int x = 0; x < gameBoard.length; x++) {
            for (int y = 0; y < gameBoard[x].length; y++) {
                System.out.print(gameBoard[x][y]);
                if (x < 4) {
                    if (y == 8) {
                        System.out.println();
                    }
                }
            }
        }
        System.out.println();
    }
    public void printError() {
        if (!parsable) {
            System.out.println("You should enter numbers!");
        } else if (!areLegalCoordinates()) {
            System.out.println("Coordinates should be from 1 to 3!");
        } else {
            System.out.println("This cell is occupied! Choose another one!");
        }
    }
    public String winner() {
        if (isWinner()) {
            if (xWins()) {
                return "X";
            }
            return "O";
        }
        return "There is no winner!";
    }
    public boolean isNotFinished() {
        return !(isWinner() || isDraw()) && gameBoardIsLegal();
    }
    private boolean isDraw() {
        for (int x = 1; x < 4; x++) {
            for (int y = 2; y < 7; y += 2) {
                if (this.gameBoard[x][y].equals("_") || this.gameBoard[x][y].equals(" ")) {
                    return false;
                }
            }
        }
        if (isWinner()) {
            return false;
        }
        return true;
    }
    private boolean gameBoardIsLegal() {
        if (xWins() && oWins()) {
            return false;
        }
        int xCount = 0;
        int oCount = 0;
        for (int x = 1; x < 4; x++) {
            for (int y = 2; y < 7; y += 2) {
                switch (this.gameBoard[x][y]) {
                    case "X":
                        xCount++;
                        break;
                    case "O":
                        oCount++;
                        break;
                }
            }
        }
        return xCount - oCount < 2 && xCount - oCount > -2;
    }
    private boolean isWinner() {
        return xWins() || oWins();
    }

    private boolean xWins() {
        return checkRows("X") || checkColumns("X") || checkDiagonals("X");
    }

    private boolean oWins() {
        return checkRows("O") || checkColumns("O") || checkDiagonals("O");
    }
    private boolean checkRows(String input) {
        for (int x = 1; x < 4; x++) {
            if (gameBoard[x][2].equals(input) && gameBoard[x][4].equals(input) && gameBoard[x][6].equals(input)) {
                return true;
            }
        }
        return false;
    }
    private boolean checkColumns(String input) {
        for (int y = 2; y < 7; y += 2) {
            if (gameBoard[1][y].equals(input) && gameBoard[2][y].equals(input) && gameBoard[3][y].equals(input)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonals(String input) {
        if (gameBoard[1][2].equals(input) && gameBoard[2][4].equals(input) && gameBoard[3][6].equals(input)) {
            return true;
        }
        if (gameBoard[1][6].equals(input) && gameBoard[2][4].equals(input) && gameBoard[3][2].equals(input)) {
            return true;
        }
        return false;
    }
    public boolean moveIsLegal(String input) {
        return isParsableToIntegers(input) && areLegalCoordinates() && isEmpty();
    }
    private boolean isParsableToIntegers(String input) {
        try {
            convertToCoordinates(input);
        } catch (Exception e) {
            return false;
        }
        return parsable = true;
    }
    private boolean areLegalCoordinates() {
        return x <= 3 && x >= 1 && y <= 6 && y >= 2;
    }

    private boolean isEmpty() {
        return gameBoard[this.x][this.y].equals(" ");
    }

    private void convertToCoordinates(String input) {
        String[] chars = input.split(" ");
        this.x = Integer.parseInt(chars[0]);
        this.y = Integer.parseInt(chars[1]) * 2;
    }
    private String[][] convertToField() {
        String[][] layout = new String[5][9];
        for (int x = 1; x < 5; x++) {
            layout[x][0] = "|";
            layout[x][8] = "|";
        }
        for (int y = 0; y < 9; y++) {
            layout[0][y] = "-";
            layout[4][y] = "-";
        }
        for (int x = 1; x < 4; x++) {
            for (int y = 2; y < 7; y += 2) {
                layout[x][y] = " ";
                if (layout[x][y].equals("_")) {
                    layout[x][y] = " ";
                }
            }
        }
        for (int x = 1; x < 4; x++) {
            for (int y = 1; y < 9; y += 2) {
                layout[x][y] = " ";
            }
        }
        return layout;
    }

    public void add() {
        gameBoard[x][y] = currentPlayer;
    }
}
