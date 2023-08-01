package com.example.demo.Movie;

import java.sql.*;
import java.util.Scanner;

public class BookMyshow {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Spring?createDatabaseIfNotExist=true";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "tiger";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            createDatabaseIfNotExists(conn);
            createMoviesTableIfNotExists(conn);

            Movie[] movies = {
                new ActionMovie("Avengers: Endgame", 50, 10.0),
                new ComedyMovie("The Lion King", 40, 8.5),
                new ActionMovie("Inception", 35, 9.0)
            };

            startMovieBookingSystem(conn, movies);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void startMovieBookingSystem(Connection conn, Movie[] movies) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String userName = scanner.nextLine();

        System.out.print("Enter your email: ");
        String userEmail = scanner.nextLine();

        System.out.print("Enter your phone number: ");
        String userPhoneNumber = scanner.nextLine();

        System.out.println("\nWelcome, " + userName + " to Movie Booking System");
        System.out.println("Choose a movie:");
        for (int i = 0; i < movies.length; i++) {
            System.out.println((i + 1) + ". " + movies[i].getName());
        }

        int choice;
        do {
            System.out.print("Enter your choice (1-" + movies.length + "): ");
            choice = scanner.nextInt();
        } while (choice < 1 || choice > movies.length);

        int selectedMovieIndex = choice - 1;
        Movie selectedMovie = movies[selectedMovieIndex];

        System.out.println("\nMovie: " + selectedMovie.getName());
        System.out.println("Available Seats: " + selectedMovie.getTotalSeats());
        System.out.println("Ticket Price: $" + selectedMovie.getTicketPrice());

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Book a ticket");
            System.out.println("2. Check available seats");
            System.out.println("3. Change movie");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            int menuChoice = scanner.nextInt();

            switch (menuChoice) {
                case 1:
                    bookTicket(scanner, conn, selectedMovie, userName, userEmail, userPhoneNumber);
                    break;
                case 2:
                    checkAvailableSeats(selectedMovie.getTotalSeats());
                    break;
                case 3:
                    main(null);
                    break;
                case 4:
                    System.out.println("Thank you for using Movie Booking System, " + userName + ".");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void bookTicket(Scanner scanner, Connection conn, Movie selectedMovie, String userName, String userEmail, String userPhoneNumber) {
        System.out.print("Enter the number of tickets you want to book: ");
        int numTickets = scanner.nextInt();

        if (numTickets <= selectedMovie.getTotalSeats()) {
            double totalAmount = numTickets * selectedMovie.getTicketPrice();
            System.out.println("Total amount payable: $" + totalAmount);

            System.out.print("Confirm booking? (Y/N): ");
            String confirmation = scanner.next();

            if (confirmation.equalsIgnoreCase("Y")) {
                selectedMovie.bookTickets(numTickets);
                if (selectedMovie instanceof ActionMovie) {
                    ((ActionMovie) selectedMovie).setEmail(userEmail);
                    ((ActionMovie) selectedMovie).setPhoneNumber(userPhoneNumber);
                } else if (selectedMovie instanceof ComedyMovie) {
                    ((ComedyMovie) selectedMovie).setEmail(userEmail);
                    ((ComedyMovie) selectedMovie).setPhoneNumber(userPhoneNumber);
                }

                try {
                    updateMovieInDatabase(conn, selectedMovie);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Booking canceled.");
            }
        } else {
            System.out.println("Insufficient seats available.");
        }
    }

    public static void checkAvailableSeats(int availableSeats) {
        System.out.println("Available Seats: " + availableSeats);
    }

    public static void createDatabaseIfNotExists(Connection conn) throws SQLException {
        String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS Spring";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createDatabaseQuery);
        }
    }

    public static void createMoviesTableIfNotExists(Connection conn) throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS movies (" +
                "name VARCHAR(255) NOT NULL," +
                "total_seats INT NOT NULL," +
                "ticket_price DOUBLE NOT NULL," +
                "email VARCHAR(255)," +
                "phone_number VARCHAR(20)," +
                "total_tickets_booked INT NOT NULL," +
                "amount_paid DOUBLE NOT NULL," +
                "PRIMARY KEY (name)" +
                ")";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableQuery);
        }
    }

    public static void updateMovieInDatabase(Connection conn, Movie movie) throws SQLException {
        String updateQuery = "INSERT INTO movies (name, total_seats, ticket_price, email, phone_number, total_tickets_booked, amount_paid) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE email = ?, phone_number = ?, total_tickets_booked = ?, amount_paid = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setString(1, movie.getName());
            pstmt.setInt(2, movie.getTotalSeats());
            pstmt.setDouble(3, movie.getTicketPrice());
            if (movie instanceof ActionMovie) {
                pstmt.setString(4, ((ActionMovie) movie).getEmail());
                pstmt.setString(5, ((ActionMovie) movie).getPhoneNumber());
                pstmt.setInt(6, ((ActionMovie) movie).getTotalTicketsBooked1());
                pstmt.setDouble(7, ((ActionMovie) movie).getAmountPaid());
            } else if (movie instanceof ComedyMovie) {
                pstmt.setString(4, ((ComedyMovie) movie).getEmail());
                pstmt.setString(5, ((ComedyMovie) movie).getPhoneNumber());
                pstmt.setInt(6, ((ComedyMovie) movie).getTotalTicketsBooked());
                pstmt.setDouble(7, ((ComedyMovie) movie).getAmountPaid());
            }
            pstmt.setString(8, movie instanceof ActionMovie ? ((ActionMovie) movie).getEmail() : ((ComedyMovie) movie).getEmail());
            pstmt.setString(9, movie instanceof ActionMovie ? ((ActionMovie) movie).getPhoneNumber() : ((ComedyMovie) movie).getPhoneNumber());
            pstmt.setInt(10, movie instanceof ActionMovie ? ((ActionMovie) movie).getTotalTicketsBooked() : ((ComedyMovie) movie).getTotalTicketsBooked());
            pstmt.setDouble(11, movie instanceof ActionMovie ? ((ActionMovie) movie).getAmountPaid() : ((ComedyMovie) movie).getAmountPaid());
            pstmt.executeUpdate();
        }
    }
}
