package com.example.demo.Movie;

public class MovieViewer {
    private String[] movieNames;
    private int[] totalSeats;
    private double[] ticketPrices;

    public MovieViewer(String[] movieNames, int[] totalSeats, double[] ticketPrices) {
        this.movieNames = movieNames;
        this.totalSeats = totalSeats;
        this.ticketPrices = ticketPrices;
    }

    public void displayMovies() {
        System.out.println("\nAvailable movies in the theater:");
        for (int i = 0; i < movieNames.length; i++) {
            System.out.println((i + 1) + ". " + movieNames[i]);
            System.out.println("   Total Seats: " + totalSeats[i]);
            System.out.println("   Ticket Price: $" + ticketPrices[i]);
        }
    }
}
