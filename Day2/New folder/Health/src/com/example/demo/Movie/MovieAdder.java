package com.example.demo.Movie;

import java.util.Scanner;

public class MovieAdder {
    private String[] movieNames;
    private int[] totalSeats;
    private double[] ticketPrices;

    public MovieAdder(int maxMovies) {
        movieNames = new String[maxMovies];
        totalSeats = new int[maxMovies];
        ticketPrices = new double[maxMovies];
    }

    public void addMovie() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter movie details:");

        for (int i = 0; i < movieNames.length; i++) {
            System.out.println("Movie " + (i + 1) + ":");
            System.out.print("Name: ");
            movieNames[i] = scanner.nextLine();
            System.out.print("Total Seats: ");
            totalSeats[i] = scanner.nextInt();
            System.out.print("Ticket Price: $");
            ticketPrices[i] = scanner.nextDouble();
            scanner.nextLine(); 
        }
    }

    public String[] getMovieNames() {
        return movieNames;
    }

    public int[] getTotalSeats() {
        return totalSeats;
    }

    public double[] getTicketPrices() {
        return ticketPrices;
    }
}
