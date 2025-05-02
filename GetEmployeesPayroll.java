import java.sql.*;
import java.util.Scanner;

public class GetEmployeesPayroll {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Introduction block
        System.out.println("Welcome to the Employee Payroll System!");
        System.out.println("Please select an action:");
        System.out.println("1. View Employee Payroll Report");
        System.out.println("2. Update Employee Information");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (choice == 2) {
            System.out.println("Exiting the application. Goodbye!");
            return;
        } else if (choice != 1) {
            System.out.println("Invalid choice. Exiting the application.");
            return;
        }

        Payroll p1 = new Payroll();
        String url = "jdbc:mysql://localhost:3306/employeeData";
        String user = "root";
        String password = "RootUser89@";
        StringBuilder output = new StringBuilder("");
        String sqlcommand = "SELECT e.Fname, e.Lname, e.email, jt.job_title, e.empid " +
                "FROM employees e  " +
                "JOIN employee_job_titles ejt ON e.empid = ejt.empid " +
                "JOIN job_titles jt ON ejt.job_title_id = jt.job_title_id  " +
                "ORDER BY e.empid ; ";

        try (Connection myConn = DriverManager.getConnection(url, user, password)) {
            Statement myStmt = myConn.createStatement();

            output.append("\nEMPLOYEE PAYROLL REPORT by McDavid Adofoli\n");
            ResultSet myRS = myStmt.executeQuery(sqlcommand);
            while (myRS.next()) {
                output.append("Name= " + myRS.getString("e.Fname") + " " + myRS.getString("e.Fname") + "\t");
                output.append("Title=" + myRS.getString("jt.job_title") + "     " + myRS.getString("e.email") + "\n");
                output.append(p1.getPayByMonth(myRS.getInt("e.empid"), myConn));
                System.out.print(output.toString());
                output.setLength(0);
                
            }
            myConn.close();
        } catch (Exception e) {
            System.out.println("ERROR " + e.getLocalizedMessage());
        } finally {
        }
        System.out.println("\nPress Enter to clear console screen...\n");
        Scanner myScann = new Scanner(System.in);
        String str = myScann.nextLine() ;
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
        
    }
    
     
}
