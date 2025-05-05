
import java.sql.*;
import java.util.Scanner;

public class GetEmployeesPayroll {
    public GetEmployeesPayroll() {
    }

    public static void main(String[] args) {
        Login login = new Login();

        // Authenticate user
        if (!login.authenticate()) {
            return; // Exit if authentication fails
        }
        Scanner scanner = new Scanner(System.in);

        // Main menu loop
        while (true) {
            // Introduction block
            System.out.println("Welcome to the Employee Payroll System!");
            System.out.println("Please select an action:");
            System.out.println("1. View Employee Payroll Report");
            System.out.println("2. Search Employee Information");
            System.out.println("3. Update Employee Information");
            System.out.println("4. Insert New Employee");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (or type 'quit' to exit): ");

            // Check if the user wants to quit
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("quit")) {
                System.out.println("Exiting the application. Goodbye!");
                break;
            }

            // Parse the input as an integer choice
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number or 'quit' to exit.");
                continue;
            }

            // Handle menu choices
            if (choice == 5) {
                System.out.println("Exiting the application. Goodbye!");
                break;
            } else if (choice == 1) {
                viewPayrollReport();
                System.out.println();
            } else if (choice == 2) {
                EmployeeSearch employeeSearch = new EmployeeSearch();
                employeeSearch.searchAndEditEmployee();
            } else if (choice == 3) {
                UpdateEmployee updateEmployee = new UpdateEmployee();
                updateEmployee.updateEmployeeInfo();
                System.out.println();
            } else if (choice == 4) {
                InsertEmployee insertEmployee = new InsertEmployee();
                insertEmployee.insertNewEmployee();
                System.out.println();
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewPayrollReport() {
        Payroll p1 = new Payroll();
        String url = "jdbc:mysql://localhost:3306/employeeData";
        String user = "root";
        String password = "RootUser89@";
        StringBuilder output = new StringBuilder();
        Scanner scanner = new Scanner(System.in);
    
        // Prompt the user to specify the employee
        System.out.println("Enter the Employee ID or Name to view their payroll report:");
        String input = scanner.nextLine();
    
        // SQL query to filter by employee ID or name
        String sqlcommand = "SELECT e.Fname, e.Lname, e.email, jt.job_title, e.empid " +
                            "FROM employees e " +
                            "JOIN employee_job_titles ejt ON e.empid = ejt.empid " +
                            "JOIN job_titles jt ON ejt.job_title_id = jt.job_title_id " +
                            "WHERE e.empid = ? OR CONCAT(e.Fname, ' ', e.Lname) LIKE ? " +
                            "ORDER BY e.empid;";
    
        try (Connection myConn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = myConn.prepareStatement(sqlcommand)) {
    
            // Set parameters for the query
            pstmt.setString(1, input); // Employee ID
            pstmt.setString(2, "%" + input + "%"); // Employee Name (partial match)
    
            ResultSet myRS = pstmt.executeQuery();
    
            if (!myRS.isBeforeFirst()) { // Check if the result set is empty
                System.out.println("No employee found with the given ID or name.");
                return;
            }
    
            output.append("\nEMPLOYEE PAYROLL REPORT \n");
            while (myRS.next()) {
                output.append("Name= ").append(myRS.getString("e.Fname")).append(" ").append(myRS.getString("e.Lname")).append("\t");
                output.append("Title= ").append(myRS.getString("jt.job_title")).append("     ").append(myRS.getString("e.email")).append("\n");
                output.append(p1.getPayByMonth(myRS.getInt("e.empid"), myConn));
                System.out.print(output.toString());
                output.setLength(0); // Clear the StringBuilder for the next record
            }
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}