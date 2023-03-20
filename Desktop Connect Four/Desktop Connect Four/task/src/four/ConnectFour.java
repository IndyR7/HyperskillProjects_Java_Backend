package four;

import javax.swing.*;
import java.awt.*;

public class ConnectFour extends JFrame {

    private boolean xPlays = true;
    private boolean isRunning = true;
    JButton[][] buttons = new JButton[7][6];

    public ConnectFour() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setTitle("Connect Four");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        createBoardButtons();
        createResetButton();

        setVisible(true);
    }

    public void createBoardButtons() {
        JPanel panel = new JPanel(new GridLayout(6, 7));
        panel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        for (int i = 6; i > 0; i--) {
            for (char c = 'A'; c < 'H'; c++) {
                JButton cellButton = new JButton(" ");

                cellButton.setName("Button" + c + i);
                cellButton.setFocusPainted(false);
                cellButton.setBackground(Color.LIGHT_GRAY);

                int column = c - 'A';

                cellButton.addActionListener(actionEvent -> {
                    if (isRunning) {
                        for (int row = 5; row >= 0; row--) {
                            JButton button = buttons[column][row];
                            if (button.getText().equals(" ")) {
                                button.setText(xPlays ? "X" : "O");
                                xPlays = !xPlays;
                                checkForWinner(button.getText());
                                break;
                            }
                        }
                    }
                });

                int row = 6 - Math.abs(i);
                buttons[column][row] = cellButton;

                panel.add(cellButton);
            }
        }
        add(panel);
    }

    private void createResetButton() {
        JButton resetButton = new JButton("Reset");
        resetButton.setName("ButtonReset");
        resetButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        resetButton.setFocusPainted(false);

        resetButton.addActionListener(actionEvent -> {
            for (int r = 0; r < 6; r++) {
                for (int c = 0; c < 7; c++) {
                    buttons[c][r].setText(" ");
                    buttons[c][r].setBackground(Color.LIGHT_GRAY);
                    xPlays = true;
                    isRunning = true;
                }
            }
        });
        add(resetButton);
    }

    private void checkForWinner(String player) {
        checkVertical(player);
        checkHorizontal(player);
        checkPositiveDiagonal(player);
        checkNegativeDiagonal(player);
    }

    private void checkHorizontal(String player) {
        for (int row = 0; row < buttons.length - 3; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                if (buttons[row][col].getText().equals(player) &&
                        buttons[row + 1][col].getText().equals(player) &&
                        buttons[row + 2][col].getText().equals(player) &&
                        buttons[row + 3][col].getText().equals(player)) {
                    buttons[row][col].setBackground(Color.BLUE);
                    buttons[row + 1][col].setBackground(Color.BLUE);
                    buttons[row + 2][col].setBackground(Color.BLUE);
                    buttons[row + 3][col].setBackground(Color.BLUE);

                    isRunning = false;
                }
            }
        }
    }

    private void checkVertical(String player) {
        for (JButton[] button : buttons) {
            for (int col = 0; col < button.length - 3; col++) {
                if (button[col].getText().equals(player) &&
                        button[col + 1].getText().equals(player) &&
                        button[col + 2].getText().equals(player) &&
                        button[col + 3].getText().equals(player)) {
                    button[col].setBackground(Color.BLUE);
                    button[col + 1].setBackground(Color.BLUE);
                    button[col + 2].setBackground(Color.BLUE);
                    button[col + 3].setBackground(Color.BLUE);

                    isRunning = false;
                }
            }
        }
    }

    private void checkPositiveDiagonal(String player) {
        for (int row = 0; row < buttons.length - 3; row++) {
            for (int col = 0; col < buttons[row].length - 3; col++) {
                if (buttons[row][col].getText().equals(player) &&
                        buttons[row + 1][col + 1].getText().equals(player) &&
                        buttons[row + 2][col + 2].getText().equals(player) &&
                        buttons[row + 3][col + 3].getText().equals(player)) {
                    buttons[row][col].setBackground(Color.BLUE);
                    buttons[row + 1][col + 1].setBackground(Color.BLUE);
                    buttons[row + 2][col + 2].setBackground(Color.BLUE);
                    buttons[row + 3][col + 3].setBackground(Color.BLUE);

                    isRunning = false;
                }
            }
        }
    }

    private void checkNegativeDiagonal(String player) {
        for (int row = 0; row < buttons.length - 3; row++) {
            for (int col = 3; col < buttons[row].length; col++) {
                if (buttons[row][col].getText().equals(player) &&
                        buttons[row + 1][col - 1].getText().equals(player) &&
                        buttons[row + 2][col - 2].getText().equals(player) &&
                        buttons[row + 3][col - 3].getText().equals(player)) {
                    buttons[row][col].setBackground(Color.BLUE);
                    buttons[row + 1][col - 1].setBackground(Color.BLUE);
                    buttons[row + 2][col - 2].setBackground(Color.BLUE);
                    buttons[row + 3][col - 3].setBackground(Color.BLUE);

                    isRunning = false;
                }
            }
        }
    }
}