import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Employee {
    String name;
    String dob; // Date of Birth
    String ssn; // Social Security Number
    String employeeId;

    public Employee(String name, String dob, String ssn, String employeeId) {
        this.name = name;
        this.dob = dob;
        this.ssn = ssn;
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", ssn='" + ssn + '\'' +
                ", employeeId='" + employeeId + '\'' +
                '}';
    }
}

public class employeeUpdate {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("John Doe", "01/01/1980", "123-45-6789", "E001"));
        employees.add(new Employee("Jane Smith", "02/02/1990", "987-65-4321", "E002"));
        employees.add(new Employee("Emily Davis", "03/03/1985", "456-78-9012", "E003"));
        employees.add(new Employee("Michael Brown", "04/04/1975", "789-01-2345", "E004"));

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Employee ID to update:");
        String searchEmployeeId = scanner.nextLine();

        Employee employeeToUpdate = null;

        // Search for the employee by ID
        for (Employee employee : employees) {
            if (employee.employeeId.equals(searchEmployeeId)) {
                employeeToUpdate = employee;
                break;
            }
        }

        if (employeeToUpdate != null) {
            System.out.println("Employee found: " + employeeToUpdate);
            System.out.println("Enter new name (leave blank to keep current):");
            String newName = scanner.nextLine();
            System.out.println("Enter new date of birth (leave blank to keep current):");
            String newDob = scanner.nextLine();
            System.out.println("Enter new SSN (leave blank to keep current):");
            String newSsn = scanner.nextLine();

            // Update details if new values are provided
            if (!newName.isBlank()) {
                employeeToUpdate.name = newName;
            }
            if (!newDob.isBlank()) {
                employeeToUpdate.dob = newDob;
            }
            if (!newSsn.isBlank()) {
                employeeToUpdate.ssn = newSsn;
            }

            System.out.println("Employee details updated: " + employeeToUpdate);
        } else {
            System.out.println("Employee with ID " + searchEmployeeId + " not found.");
        }

        scanner.close();
    }
}