package tictactoe;

import java.util.Random;

public class AI implements Player {
    private final String difficulty;
    private final GameBoard gameBoard;

    public AI(GameBoard gameBoard, String difficulty) {
        this.difficulty = difficulty.toLowerCase();
        this.gameBoard = gameBoard;
    }

    @Override
    public void makeMove() {
        gameBoard.setCurrentPlayer();
        System.out.printf("Making move level \"%s\"\n", difficulty);
        if (difficulty.equals("easy")) {
            makeEasyMove();
        } else if (difficulty.equals("medium")) {
            makeMediumMove();
        } else {
            makeHardMove();
        }
    }

    private void makeHardMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (gameBoard.isEmpty(x, y)) {
                    gameBoard.add(x, y, gameBoard.getCurrentPlayer());
                    int score = minimax(gameBoard.getCurrentPlayer().equals("X") ? "O" : "X", 0);
                    gameBoard.add(x, y, " ");
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = x;
                        bestMove[1] = y;
                    }
                }
            }
        }
        gameBoard.add(bestMove[0], bestMove[1], gameBoard.getCurrentPlayer());
    }


    private void makeMediumMove() {
        if (!tryWinningMove()) {
            makeEasyMove();
        }
    }

    private void makeEasyMove() {
        while (true) {
            int x = new Random().nextInt(0, 3);
            int y = new Random().nextInt(0, 3);
            if (gameBoard.isEmpty(x, y)) {
                gameBoard.add(x, y, gameBoard.getCurrentPlayer());
                break;
            }
        }
    }

    private boolean tryWinningMove() {
        String currentPlayer = gameBoard.getCurrentPlayer();
        String opposingPlayer = gameBoard.getCurrentPlayer().equals("X") ? "O" : "X";

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (gameBoard.isEmpty(x, y)) {
                    gameBoard.add(x, y, currentPlayer);
                    if (gameBoard.isWinner()) {
                        return true;
                    }
                    gameBoard.add(x, y, opposingPlayer);
                    if (gameBoard.isWinner()) {
                        gameBoard.add(x, y, currentPlayer);
                        return true;
                    }
                    gameBoard.add(x, y, " ");
                }
            }
        }
        return false;
    }

    public int minimax(String player, int depth) {
        String currentPlayer = gameBoard.getCurrentPlayer();
        String opposingPlayer = currentPlayer.equals("X") ? "O" : "X";

        if (!gameBoard.isRunning()) {
            if (gameBoard.getWinner().equals(currentPlayer)) {
                return 10 - depth;
            }
            if (gameBoard.getWinner().equals(opposingPlayer)) {
                return depth - 10;
            }
            return 0;
        }


        int bestScore;
        if (player.equals(currentPlayer)) {
            bestScore = Integer.MIN_VALUE;
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    if (gameBoard.isEmpty(x, y)) {
                        gameBoard.add(x, y, player);
                        int score = minimax(opposingPlayer, depth + 1);
                        gameBoard.add(x, y, " ");
                        bestScore = Math.max(bestScore, score);
                    }
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    if (gameBoard.isEmpty(x, y)) {
                        gameBoard.add(x, y, player);
                        int score = minimax(currentPlayer, depth + 1);
                        gameBoard.add(x, y, " ");
                        bestScore = Math.min(bestScore, score);
                    }
                }
            }
        }
        return bestScore;
    }
}