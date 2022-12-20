package cinema;

import java.util.*;

public class UI {
    private Scanner scanner;

    public UI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();
        CinemaRoom cinemaRoom = new CinemaRoom(rows, seats);
        commandCenter : while (true) {
            System.out.println("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit");
            int command = scanner.nextInt();
            switch (command) {
                case 1:
                    show(cinemaRoom);
                    continue;
                case 2:
                    buy(cinemaRoom);
                    continue;
                case 3:
                    statistics(cinemaRoom);
                    continue;
                default:
                    break commandCenter;
            }
        }
    }

    public void show(CinemaRoom cinemaRoom) {
        cinemaRoom.printRoom();
    }

    public void buy(CinemaRoom cinemaRoom) {
        while (true) {
            System.out.println("Enter a row number:");
            int row = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            int seat = scanner.nextInt();
            if (cinemaRoom.isLegal(row, seat)) {
                cinemaRoom.add(row, seat);
                System.out.println("Ticket price: $" + cinemaRoom.getPrice(row));
                break;
            } else {
                System.out.println(cinemaRoom.getError(row, seat));
            }
        }
    }
    public void statistics(CinemaRoom cinemaRoom) {
        System.out.println("Number of purchased tickets: " + cinemaRoom.getTicketsSold());
        System.out.printf("Percentage %.2f%%:\n", cinemaRoom.getPercentageSold());
        System.out.println("Current income: $" + cinemaRoom.getCurrentIncome());
        System.out.println("Total income: $" + cinemaRoom.getMaxIncome());
    }
}
