package src;

import java.util.HashMap;
import java.util.Scanner;

public class Login {
    private HashMap<String, String> users;

    public Login() {
        // Going to hard code these credentials for testing purposes
        users = new HashMap<>();
        users.put("admin", "password123");
        users.put("employee", "empPass456");
    }

    public boolean authenticate() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Employee Payroll System Login!");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (users.containsKey(username) && users.get(username).equals(password)) {
            System.out.println("Login successful! Redirecting to the main menu...\n");
            return true;
        } else {
            System.out.println("Invalid credentials. Exiting the application.");
            return false;
        }
    }
}