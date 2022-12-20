package cinema;

public class CinemaRoom {
    private final String[][] cinemaRoom;
    private final int rows;
    private final int seats;
    private final int front;
    private final int back;
    private int income;
    private final int size;
    private int ticketsSold;

    public CinemaRoom(int rows, int seats) {
        this.rows = rows;
        this.seats = seats;
        this.cinemaRoom = convertToRoom();
        this.front = rows / 2;
        this.back = rows - front;
        this.income = 0;
        this.size = rows * seats;
        this.ticketsSold = 0;
    }

    public void add(int row, int seat) {
        cinemaRoom[row][seat * 2] = "B";
        this.income += getPrice(row);
        this.ticketsSold++;
    }

    public boolean purchaseSuccessful(int row, int seat) {
        if (isLegal(row, seat)) {
            add(row, seat);
            return true;
        }
        return false;
    }
    private boolean isTaken(int row, int seat) {
        return cinemaRoom[row][seat * 2].equals("B");
    }

    private boolean isExistingSeat(int row, int seat) {
        return row <= this.rows && seat <= this.seats;
    }
    private boolean isFront(int row) {
        return row <= front;
    }

    private boolean isSmallRoom() {
        return this.rows * this.seats <= 60;
    }

    public boolean isLegal(int row, int seat) {
        return isExistingSeat(row, seat) && !isTaken(row, seat);
    }
    public double getPercentageSold() {
        return 100.0 * this.ticketsSold / size;
    }

    public int getCurrentIncome() {
        return this.income;
    }

    public int getMaxIncome() {
        return isSmallRoom() ? size * 10 : seats * (front * 10 + back * 8);
    }

    public int getTicketsSold() {
        return this.ticketsSold;
    }
    public int getPrice(int row) {
        return isSmallRoom() ? 10 : isFront(row) ? 10 : 8;
    }
    public String getError(int row, int seat) {
        return !isExistingSeat(row, seat) ? "Wrong input!"
                : "That ticket has already been purchased!";
    }
    public void printRoom() {
        System.out.println("Cinema:");
        for (String[] strings : cinemaRoom) {
            for (int c = 0; c < strings.length; c++) {
                System.out.print(strings[c]);
                if (c == this.seats * 2) {
                    System.out.println();
                }
            }
        }
    }
    private String[][] convertToRoom() {
        int rowsConverted = this.rows + 1;
        int seatsConverted = this.seats * 2 + 1;
        String[][] room = new String[rowsConverted][seatsConverted];
        int i = 1;
        int j = 1;
        room[0][0] = " ";
        for (int r = 1; r < rowsConverted; r++) {
            room[r][0] = String.valueOf(i++);
        }
        for (int c = 2; c < seatsConverted; c += 2) {
            room[0][c] = String.valueOf(j++);
        }
        for (int r = 1; r < room.length; r++) {
            for (int c = 2; c < room[r].length; c += 2) {
                room[r][c] = "S";
            }
        }
        for (int r = 0; r < room.length; r++) {
            for (int c = 1; c < room[r].length; c += 2) {
                room[r][c] = " ";
            }
        }
        return room;
    }
}
