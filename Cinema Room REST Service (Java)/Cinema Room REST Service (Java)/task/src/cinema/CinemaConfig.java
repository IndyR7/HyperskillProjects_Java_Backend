package cinema;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class CinemaConfig {
    @Bean
    public CinemaRoom cinemaRoom() {
        Seat[][] availableSeats = new Seat[9][9];

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int price = row <= 3 ? 10 : 8;
                availableSeats[row][col] = new Seat(row + 1, col + 1, price, true);
            }
        }

        return new CinemaRoom(9, 9, availableSeats);
    }

    @Bean
    public Map<String, Ticket> purchasedTickets() {
        return new HashMap<>();
    }

    @Bean
    public Stats stats() {
        return new Stats();
    }
}
