package com.example.demo.Login;

import java.util.HashMap;
import java.util.Map;

public class LoginSystem {
    private static Map<String, String> users = new HashMap<>();

    static {
        // Sample user data (username - password)
        users.put("user1", "password1");
        users.put("user2", "password2");
    }

    public static boolean login(String username, String password) {
        if (users.containsKey(username) && users.get(username).equals(password)) {
            System.out.println("Login successful! Welcome, " + username + ".");
            return true;
        } else {
            System.out.println("Invalid username or password. Please try again.");
            return false;
        }
    }
}
