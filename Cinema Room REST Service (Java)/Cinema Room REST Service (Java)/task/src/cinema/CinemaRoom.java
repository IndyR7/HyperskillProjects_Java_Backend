package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CinemaRoom {
    private int totalRows;
    private int totalColumns;
    private Seat[][] availableSeats;

    public CinemaRoom(int totalRows, int totalColumns, Seat[][] availableSeats) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        this.availableSeats = availableSeats;
    }

    @JsonProperty("total_rows")
    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    @JsonProperty("total_columns")
    public int getTotalColumns() {
        return totalColumns;
    }

    public void setTotalColumns(int totalColumns) {
        this.totalColumns = totalColumns;
    }

    @JsonProperty("available_seats")
    public List<Seat> getAvailableSeats() {
        // returns the 2D-array CinemaRoom as a list, as required by the task
        return Arrays.stream(availableSeats)
                .flatMap(Arrays::stream)
                .filter(Seat::isAvailable)
                .collect(Collectors.toList());
    }

    public void setAvailableSeats(Seat[][] availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Seat getSeat(int row, int col) {
        if (row < 1 || row > totalRows || col < 1 || col > totalColumns) {
            return null;
        }
        return availableSeats[row - 1][col - 1];
    }
}