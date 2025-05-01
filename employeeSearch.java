public class employeeSearch {
    public static void main(String[] args) {
        String[] employees = {"John Doe", "Jane Smith", "Emily Davis", "Michael Brown"};
        String name = "Jane Smith";
        String dob;
        String ssn;
        String employeeId;
        boolean found = false;

        for (String employee : employees) {
            if (employee.equalsIgnoreCase(name)) {
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println("Employee found: " + name);
        } else {
            System.out.println("Employee not found: " + name);
        }
    }
    
}
