import java.sql.*;
import java.util.Scanner;

public class UpdateEmployee {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employeeData";
    private static final String DB_USER = "username";
    private static final String DB_PASSWORD = "password";

    public void updateEmployeeInfo() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Employee ID to update: ");
        int empId;
        try {
            empId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Employee ID. Please enter a valid number.");
            return;
        }

        // Display update options
        System.out.println("What information would you like to update?");
        System.out.println("1. First Name");
        System.out.println("2. Last Name");
        System.out.println("3. Email");
        System.out.println("4. Job Title");
        System.out.print("Enter your choice: ");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice. Please enter a valid number.");
            return;
        }

        String columnToUpdate = "";
        switch (choice) {
            case 1:
                columnToUpdate = "Fname";
                break;
            case 2:
                columnToUpdate = "Lname";
                break;
            case 3:
                columnToUpdate = "email";
                break;
            case 4:
                columnToUpdate = "job_title";
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
                return;
        }

        System.out.print("Enter the new value for " + columnToUpdate + ": ");
        String newValue = scanner.nextLine();

        // Update query
        String updateQuery = choice == 4
                ? "UPDATE job_titles jt JOIN employee_job_titles ejt ON jt.job_title_id = ejt.job_title_id " +
                  "SET jt.job_title = ? WHERE ejt.empid = ?"
                : "UPDATE employees SET " + columnToUpdate + " = ? WHERE empid = ?";

        try (Connection myConn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = myConn.prepareStatement(updateQuery)) {

            pstmt.setString(1, newValue);
            pstmt.setInt(2, empId);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Employee information updated successfully.");
            } else {
                System.out.println("No employee found with the given ID.");
            }
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getLocalizedMessage());
        }
    }
}