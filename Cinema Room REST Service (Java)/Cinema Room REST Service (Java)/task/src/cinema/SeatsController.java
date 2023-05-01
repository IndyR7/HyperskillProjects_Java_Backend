package cinema;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
public class SeatsController {

    private final CinemaRoom cinemaRoom;
    private final Map<String, Ticket> purchasedTickets;
    private final Stats stats;

    @Autowired
    public SeatsController(CinemaRoom cinemaRoom, Map<String, Ticket> purchasedTickets, Stats stats) {
        this.cinemaRoom = cinemaRoom;
        this.purchasedTickets = purchasedTickets;
        this.stats = stats;
    }

    @GetMapping("/seats")
    public CinemaRoom getSeats() {
        return this.cinemaRoom;
    }

    @JsonView(View.purchased.class)
    @PostMapping("/purchase")
    public ResponseEntity<Object> purchaseSeat(@RequestBody Map<String, Integer> payload) {
        int row = payload.get("row");
        int column = payload.get("column");
        Seat seat = cinemaRoom.getSeat(row, column);

        if (seat == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessage.NON_EXISTING_SEAT);
        }
        if (!seat.isAvailable()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessage.UNAVAILABLE);
        }

        seat.setAvailability(false);

        String token = UUID.randomUUID().toString();
        Ticket ticket = new Ticket(token, seat);

        purchasedTickets.put(token, ticket);
        stats.addIncome(seat.getPrice());
        stats.addPurchasedTicket();
        stats.removeAvailableSeat();

        return ResponseEntity.status(HttpStatus.OK).body(ticket);
    }


    @JsonView(View.returned.class)
    @PostMapping("/return")
    public ResponseEntity<Object> returnTicket(@RequestBody Map<String, String> payload) {
        String token = payload.get("token");

        if (purchasedTickets.containsKey(token)) {
            Ticket ticket = purchasedTickets.get(token);

            ticket.getSeat().setAvailability(true);
            purchasedTickets.remove(token);
            stats.addAvailableSeat();
            stats.removeIncome(ticket.getSeat().getPrice());
            stats.removePurchasedTicket();

            return ResponseEntity.status(HttpStatus.OK).body(ticket);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessage.WRONG_TOKEN);
    }

    @PostMapping("/stats")
    public ResponseEntity<Object> returnStats(@RequestParam(required = false) Map<String, String> payload) {
        String password = payload == null ? null : payload.getOrDefault("password", null);

        if (password == null || !password.equals("super_secret")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorMessage.WRONG_PASSWORD);
        }

        return ResponseEntity.status(HttpStatus.OK).body(stats);
    }
}
