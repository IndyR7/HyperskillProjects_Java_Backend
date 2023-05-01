package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Stats {
    private int currentIncome;
    private int numberOfAvailableSeats;
    private int numberOfPurchasedTickets;

    public Stats() {
        this.currentIncome = 0;
        this.numberOfAvailableSeats = 81;
        this.numberOfPurchasedTickets = 0;
    }

    @JsonProperty("current_income")
    public int getCurrentIncome() {
        return currentIncome;
    }

    public void setCurrentIncome(int currentIncome) {
        this.currentIncome = currentIncome;
    }

    public void addIncome(int income) {
        this.currentIncome += income;
    }

    public void removeIncome(int income) {
        this.currentIncome -= income;
    }

    @JsonProperty("number_of_available_seats")
    public int getNumberOfAvailableSeats() {
        return numberOfAvailableSeats;
    }

    public void setNumberOfAvailableSeats(int numberOfAvailableSeats) {
        this.numberOfAvailableSeats = numberOfAvailableSeats;
    }

    public void addAvailableSeat() {
        this.numberOfAvailableSeats++;
    }

    public void removeAvailableSeat() {
        this.numberOfAvailableSeats--;
    }

    @JsonProperty("number_of_purchased_tickets")
    public int getNumberOfPurchasedTickets() {
        return numberOfPurchasedTickets;
    }

    public void setNumberOfPurchasedTickets(int numberOfPurchasedTickets) {
        this.numberOfPurchasedTickets = numberOfPurchasedTickets;
    }

    public void addPurchasedTicket() {
        this.numberOfPurchasedTickets++;
    }

    public void removePurchasedTicket() {
        this.numberOfPurchasedTickets--;
    }
}
