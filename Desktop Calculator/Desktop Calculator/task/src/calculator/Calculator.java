package calculator;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Calculator extends JFrame {

    private JLabel resultLabel;
    private JLabel equationLabel;
    private StringBuilder equation;
    private boolean isSingleDot;

    public Calculator() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 600);
        setLayout(null);
        setTitle("Calculator");

        equation = new StringBuilder();

        initLabels();
        initButtons();

        setVisible(true);
    }

    private void initButtons() {
        initSquareRootButton();
        initPlusMinusButton();
        initPower2Button();
        initPowerYButton();
        initParenthesesButton();
        initClearResultButton();
        initClearButton();
        initDeleteButton();
        initNumberButtons();
        initOperatorButtons();
        initDotButton();
        initEqualsButton();
    }

    private void initLabels() {
        resultLabel = new JLabel();
        resultLabel.setName("ResultLabel");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 40));
        resultLabel.setHorizontalAlignment(JLabel.RIGHT);
        resultLabel.setText("0");
        resultLabel.setBounds(-20, 30, 290, 50);
        add(resultLabel);

        equationLabel = new JLabel();
        equationLabel.setName("EquationLabel");
        equationLabel.setFont(new Font("Arial", Font.BOLD, 18));
        equationLabel.setForeground(Color.GREEN);
        equationLabel.setHorizontalAlignment(JLabel.RIGHT);
        equationLabel.setBounds(-20, 90, 290, 30);
        add(equationLabel);
    }

    private void initPlusMinusButton() {
        JButton plusMinusButton = new JButton("±");
        plusMinusButton.setName("PlusMinus");
        plusMinusButton.setBackground(Color.WHITE);
        plusMinusButton.setBounds(30, 440, 60, 60);

        add(plusMinusButton);

        plusMinusButton.addActionListener(actionEvent -> {
            int start = equation.isEmpty() ? 0
                    : equation.substring(equation.length() - 1).matches("\\d+") && equation.length() == 1 ? 0
                    : equation.substring(equation.length() - 2, equation.length()).equals("(-") ? -2
                    : equation.substring(equation.length() - 1).matches("\\D") ? equation.length()
                    : equation.substring(equation.length() - 3, equation.length() - 1).equals("(-") ? -3
                    : equation.length() - 1;
            int end = start == -2 ? equation.length() : equation.length() - 1;
            if (start < 0) {
                equation.replace(equation.length() + start, end, "");
            } else {
                equation.insert(start, "(-");
            }
            equationLabel.setText(equation.toString());
        });
    }

    private void initParenthesesButton() {
        JButton parenthesesButton = new JButton("( )");
        parenthesesButton.setName("Parentheses");
        parenthesesButton.setBackground(Color.getHSBColor(145, 0.0f, 0.85f));

        parenthesesButton.setBounds(30, 140, 60, 60);

        add(parenthesesButton);

        parenthesesButton.addActionListener(actionEvent -> {
            String parToAdd = leftParsEqualsRightPars() ? "("
                    : String.valueOf(equation.charAt(equation.length() - 1)).matches("[÷×+\\-^(√]") ? "("
                    : ")";
            equation.append(parToAdd);
            equationLabel.setText(equation.toString());
        });
    }

    private void initPowerYButton() {
        JButton powerYButton = new JButton("Xʸ");
        powerYButton.setName("PowerY");
        powerYButton.setFont(new Font("Arial", Font.ITALIC, 14));
        powerYButton.setBackground(Color.getHSBColor(145, 0.0f, 0.85f));

        powerYButton.setBounds(90, 200, 60, 60);

        add(powerYButton);

        powerYButton.addActionListener(actionEvent -> {
            equation.append("^(");
            equationLabel.setText(equation.toString());
        });
    }

    private void initPower2Button() {
        JButton power2Button = new JButton("X²");
        power2Button.setName("PowerTwo");
        power2Button.setFont(new Font("Arial", Font.ITALIC, 14));
        power2Button.setBackground(Color.getHSBColor(145, 0.0f, 0.85f));

        power2Button.setBounds(30, 200, 60, 60);

        add(power2Button);

        power2Button.addActionListener(actionEvent -> {
            equation.append("^(2)");
            equationLabel.setText(equation.toString());
        });
    }

    private void initSquareRootButton() {
        JButton sqrtButton = new JButton("√");
        sqrtButton.setName("SquareRoot");
        sqrtButton.setBackground(Color.getHSBColor(145, 0.0f, 0.85f));

        sqrtButton.setBounds(150, 200, 60, 60);

        add(sqrtButton);

        sqrtButton.addActionListener(actionEvent -> {
            equation.append("√(");
            equationLabel.setText(equation.toString());
        });
    }

    private void initClearResultButton() {
        JButton clearResultButton = new JButton("CE");
        clearResultButton.setName("ClearResult");
        clearResultButton.setBackground(Color.getHSBColor(145, 0.0f, 0.85f));

        clearResultButton.setBounds(90, 140, 60, 60);

        add(clearResultButton);

        clearResultButton.addActionListener(actionEvent -> resultLabel.setText("0"));
    }

    private void initClearButton() {
        JButton clearButton = new JButton("C");
        clearButton.setName("Clear");
        clearButton.setBackground(Color.getHSBColor(145, 0.0f, 0.85f));

        clearButton.setBounds(150, 140, 60, 60);

        add(clearButton);

        clearButton.addActionListener(actionEvent -> {
            equation = new StringBuilder();
            isSingleDot = false;
            equationLabel.setText(equation.toString());

            resultLabel.setText("0");
        });
    }

    private void initDeleteButton() {
        JButton deleteButton = new JButton("Del");
        deleteButton.setName("Delete");
        Font font = deleteButton.getFont();
        deleteButton.setFont(new Font(font.getName(), font.getStyle(), 10));
        deleteButton.setBackground(Color.getHSBColor(145, 0.0f, 0.85f));

        deleteButton.setBounds(210, 140, 60, 60);

        add(deleteButton);

        deleteButton.addActionListener(actionEvent -> {
            equation.delete(equation.length() - 1, equation.length());
            equationLabel.setText(equation.toString());
        });
    }

    private void initDotButton() {
        JButton dotButton = new JButton(".");
        dotButton.setName("Dot");
        dotButton.setBackground(Color.WHITE);

        dotButton.setBounds(150, 440, 60, 60);

        add(dotButton);

        dotButton.addActionListener(actionEvent -> {
            isSingleDot = equation.isEmpty()
                    || String.valueOf(equation.charAt(equation.length() - 1)).matches("[÷×+-]");
            equation.append(".");
            equationLabel.setText(equation.toString());
        });
    }

    private void initEqualsButton() {
        JButton equalsButton = new JButton("=");
        equalsButton.setName("Equals");
        equalsButton.setBackground(Color.CYAN);

        equalsButton.setBounds(210, 440, 60, 60);

        add(equalsButton);

        equalsButton.addActionListener(actionEvent -> {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator('.');
            DecimalFormat df = new DecimalFormat("#.###", symbols);
            if (String.valueOf(equation.charAt(equation.length() - 1)).matches("[÷×+-]")) {
                equationLabel.setForeground(Color.RED.darker());
                return;
            }
            reformatDoubles();
            try {
                resultLabel.setText(df.format(Calc.getResult(equation.toString())));
                equationLabel.setText(equation.toString());
            } catch (Exception e) {
                equationLabel.setForeground(Color.RED.darker());
            }
        });
    }

    private void initOperatorButtons() {
        int y = 200;
        for (int i = 0; i < 4; i++) {
            String operator = i == 0 ? "÷" : i == 1 ? "×" : i == 2 ? "-" : "+";
            String name = i == 0 ? "Divide" : i == 1 ? "Multiply" : i == 2 ? "Subtract" : "Add";

            JButton operatorButton = new JButton(operator);
            operatorButton.setName(name);
            operatorButton.setBackground(Color.getHSBColor(145, 0.0f, 0.85f));

            operatorButton.setBounds(210, y, 60, 60);

            add(operatorButton);

            operatorButton.addActionListener(actionEvent -> {
                equationLabel.setForeground(Color.GREEN);
                if (equation.isEmpty()) {
                    return;
                }
                if (String.valueOf(equation.charAt(equation.length() - 1)).matches("[÷×+-]")) {
                    equation.replace(equation.length() - 1, equation.length(), operator.toLowerCase());
                } else {
                    reformatDoubles();
                    equation.append(operator.toLowerCase());
                }
                equationLabel.setText(equation.toString());
            });

            y += 60;
        }
    }

    private void initNumberButtons() {
        int x = 150;
        int y = 260;
        int count = 0;
        String name;

        for (int i = 9; i >= 0; i--) {
            name = i == 9 ? "Nine" : i == 8 ? "Eight" : i == 7 ? "Seven"
                    : i == 6 ? "Six" : i == 5 ? "Five" : i == 4 ? "Four"
                    : i == 3 ? "Three" : i == 2 ? "Two" : i == 1 ? "One"
                    : "Zero";

            JButton numberButton = new JButton(String.valueOf(i));
            numberButton.setName(name);
            numberButton.setBackground(Color.WHITE);

            x = i == 0 ? 90 : x;

            numberButton.setBounds(x, y, 60, 60);

            add(numberButton);

            numberButton.addActionListener(actionEvent -> {
                equation.append(numberButton.getText());
                equationLabel.setForeground(Color.GREEN);
                equationLabel.setText(equation.toString());
            });

            x = count == 2 ? 150 : x - 60;
            y = count == 2 ? y + 60 : y;
            count = count == 2 ? 0 : count + 1;
        }
    }

    private void reformatDoubles() {
        if (isSingleDot) {
            equation.insert(equation.length() - 2, "0");
        }
        if (String.valueOf(equation.charAt(equation.length() - 1)).equals(".")) {
            equation.append("0");
        }
    }

    private boolean leftParsEqualsRightPars() {
        int leftParCount = 0, rightParCount = 0;
        for (int i = 0; i < equation.length(); i++) {
            leftParCount = equation.charAt(i) == '(' ? leftParCount + 1 : leftParCount;
            rightParCount = equation.charAt(i) == ')' ? rightParCount + 1 : rightParCount;

        }
        return leftParCount == rightParCount;
    }
}