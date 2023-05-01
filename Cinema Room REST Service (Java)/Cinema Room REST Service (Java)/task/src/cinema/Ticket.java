package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

public class Ticket {
    private String token;
    private Seat seat;

    public Ticket(String token, Seat seat) {
        this.token = token;
        this.seat = seat;
    }

    @JsonView(View.purchased.class) // should only be returned when POST-request equals "/purchase"
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @JsonView(View.purchased.class) // should only be returned when POST-request equals "/purchase"
    @JsonProperty("ticket")
    public Seat getSeat() {
        return seat;
    }

    @JsonView(View.returned.class) // should only be returned when POST-request equals "/return"
    @JsonProperty("returned_ticket")
    public Seat getReturnedSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }
}
