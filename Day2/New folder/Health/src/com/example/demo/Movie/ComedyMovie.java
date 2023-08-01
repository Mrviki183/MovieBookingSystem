package com.example.demo.Movie;

public class ComedyMovie extends Movie {
    private String email;
    private String phoneNumber;
    private int totalTicketsBooked;
    private double amountPaid;

    public ComedyMovie(String name, int totalSeats, double ticketPrice) {
        super(name, totalSeats, ticketPrice);
    }

    @Override
    public void displayMovieDetails() {
        System.out.println("Movie: " + name + " (Comedy Movie)");
        System.out.println("Available Seats: " + totalSeats);
        System.out.println("Ticket Price: $" + ticketPrice);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getTotalTicketsBooked() {
        return totalTicketsBooked;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setTotalTicketsBooked(int totalTicketsBooked) {
        this.totalTicketsBooked = totalTicketsBooked;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }
}
