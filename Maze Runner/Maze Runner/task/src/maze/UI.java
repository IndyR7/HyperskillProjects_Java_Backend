package maze;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class UI {
    private final Scanner scanner;
    private boolean firstOperation;
    private Maze maze;

    public UI(Scanner scanner) {
        this.scanner = scanner;
        firstOperation = true;
    }

    public void start() {
        while (true) {
            System.out.print(getMenu());
            int operation = scanner.nextInt();
            scanner.nextLine();
            if (!isLegalOperation(operation)) {
                System.out.println("Incorrect option. Please try again");
                continue;
            }
            if (operation == 0) {
                break;
            }
            performOperation(operation);
            firstOperation = false;
        }
        System.out.println("Bye!");
    }

    private void performOperation(int operation) {
        switch (operation) {
            case 1 -> generateMaze();
            case 2 -> {
                String fileToLoad = scanner.nextLine();
                loadMaze(fileToLoad);
            }
            case 3 -> {
                String file = scanner.nextLine();
                saveMaze(file);
            }
            case 4 -> displayMaze();
            case 5 -> solveMaze();
            default -> System.out.println("Incorrect input");
        }
    }

    private void solveMaze() {
        MazeSolver mazeSolver = new MazeSolver(this.maze);
        mazeSolver.printSolvedMaze();
    }

    private void generateMaze() {
        System.out.println("Enter the size of a new maze");
        this.maze = new Maze(scanner.nextInt());
        scanner.nextLine();
        displayMaze();
    }

    private void displayMaze() {
        System.out.println(maze);
    }

    private void saveMaze(String filePath) {
        try {
            File file = new File(filePath);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(maze.toString());
            fileWriter.close();
        } catch (IOException e) {
            System.out.printf("The file %s does not exist\n", filePath);
        }
    }

    private void loadMaze(String filePath) {
        try {
            String mazeToLoad = Files.readString(Paths.get(filePath));
            this.maze = new Maze(mazeToLoad);
        } catch (Exception e) {
            System.out.printf("The file %s does not exist\n", filePath);
        }
    }

    private String getMenu() {
        StringBuilder sbMenu = new StringBuilder().append("=== Menu ===\n")
                .append("1. Generate a new maze\n2. Load a maze\n");
        if (!firstOperation) {
            sbMenu.append("3. Save the maze\n4. Display the maze\n5. Find the escape\n");
        }
        return sbMenu.append("0. Exit\n").toString();
    }

    private boolean isLegalOperation(int operation) {
        return operation >= 0 && firstOperation ? operation < 3 : operation < 6;
    }
}
