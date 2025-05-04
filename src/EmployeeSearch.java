import java.sql.*;
import java.util.Scanner;

public class EmployeeSearch {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employeeData";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "RootUser89@";

    public void searchAndEditEmployee() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Search Employee by:");
        System.out.println("1. Name");
        System.out.println("2. Date of Birth (DOB)");
        System.out.println("3. Social Security Number (SSN)");
        System.out.println("4. Employee ID (empid)");
        System.out.print("Enter your choice: ");
        int searchChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String query = "";
        String searchValue = "";

        switch (searchChoice) {
            case 1:
                System.out.print("Enter Employee Name: ");
                searchValue = scanner.nextLine();
                query = "SELECT * FROM employees WHERE CONCAT(Fname, ' ', Lname) LIKE ?";
                break;
            case 2:
                System.out.print("Enter Employee DOB (YYYY-MM-DD): ");
                searchValue = scanner.nextLine();
                query = "SELECT * FROM employees WHERE DOB = ?";
                break;
            case 3:
                System.out.print("Enter Employee SSN: ");
                searchValue = scanner.nextLine();
                query = "SELECT * FROM employees WHERE SSN = ?";
                break;
            case 4:
                System.out.print("Enter Employee ID: ");
                searchValue = scanner.nextLine();
                query = "SELECT * FROM employees WHERE empid = ?";
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
                return;
        }

        try (Connection myConn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = myConn.prepareStatement(query)) {

            pstmt.setString(1, searchValue);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Employee Found:");
                System.out.println("ID: " + rs.getInt("empid"));
                System.out.println("Name: " + rs.getString("Fname") + " " + rs.getString("Lname"));
                System.out.println("DOB: " + rs.getString("DOB"));
                System.out.println("SSN: " + rs.getString("SSN"));
                System.out.println("Email: " + rs.getString("email"));

                System.out.println("\nDo you want to edit this employee's information? (yes/no)");
                String editChoice = scanner.nextLine();

                if (editChoice.equalsIgnoreCase("yes")) {
                    System.out.print("Enter new email: ");
                    String newEmail = scanner.nextLine();

                    String updateQuery = "UPDATE employees SET email = ? WHERE empid = ?";
                    try (PreparedStatement updateStmt = myConn.prepareStatement(updateQuery)) {
                        updateStmt.setString(1, newEmail);
                        updateStmt.setInt(2, rs.getInt("empid"));
                        int rowsUpdated = updateStmt.executeUpdate();
                        if (rowsUpdated > 0) {
                            System.out.println("Employee information updated successfully.");
                        } else {
                            System.out.println("Failed to update employee information.");
                        }
                    }
                }
            } else {
                System.out.println("No employee found with the given information.");
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getLocalizedMessage());
        }
    }
}