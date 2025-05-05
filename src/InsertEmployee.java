import java.sql.*;
import java.util.Scanner;

public class InsertEmployee {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employeeData";
    private static final String DB_USER = "username";
    private static final String DB_PASSWORD = "password";

    public void insertNewEmployee() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the following details for the new employee:");

        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Social Security Number (SSN): ");
        String ssn = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Hire Date (YYYY-MM-DD): ");
        String hireDate = scanner.nextLine();

        System.out.print("Salary: ");
        double salary;
        try {
            salary = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid salary. Please enter a valid number.");
            return;
        }

        // Insert query for employees table
        String insertEmployeeQuery = "INSERT INTO employees (Fname, Lname, email, HireDate, Salary, SSN) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection myConn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Insert into employees table
            try (PreparedStatement pstmt = myConn.prepareStatement(insertEmployeeQuery, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, firstName);
                pstmt.setString(2, lastName);
                pstmt.setString(3, email);
                pstmt.setString(4, hireDate);
                pstmt.setDouble(5, salary);
                pstmt.setString(6, ssn);

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Employee added successfully!");

                    // Get the generated employee ID
                    ResultSet generatedKeys = pstmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int empId = generatedKeys.getInt(1);
                        System.out.println("Generated Employee ID: " + empId);
                    }
                } else {
                    System.out.println("Failed to add the employee.");
                }
            }
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getLocalizedMessage());
        }
    }
}