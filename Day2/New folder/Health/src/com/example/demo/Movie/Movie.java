package com.example.demo.Movie;

public abstract class Movie {
    protected String name;
    protected int totalSeats;
    protected double ticketPrice;

    public Movie(String name, int totalSeats, double ticketPrice) {
        this.name = name;
        this.totalSeats = totalSeats;
        this.ticketPrice = ticketPrice;
    }

    public abstract void displayMovieDetails();

    public void bookTickets(int numTickets) {
        if (numTickets <= totalSeats) {
            double totalAmount = numTickets * ticketPrice;
            System.out.println("Total amount payable: $" + totalAmount);

            totalSeats -= numTickets;
            System.out.println("Booking successful. Enjoy the movie!");
        } else {
            System.out.println("Insufficient seats available.");
        }
    }

    public String getName() {
        return name;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }
}
