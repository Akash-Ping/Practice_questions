import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class EmployeeSorter {

    public static void main(String[] args) {
        try {
            List<Employee> employees = readCSV("Employees.csv");
            Map<String, List<Employee>> departmentMap = groupByDepartment(employees);
            sortEmployeesBySalary(departmentMap);
            printSortedEmployees(departmentMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Employee> readCSV(String filePath) throws IOException {
        List<Employee> employees = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        boolean headerSkipped = false;
    
        while ((line = reader.readLine()) != null) {
            if (!headerSkipped) {
                headerSkipped = true;
                continue; // Skip header
            }
            String[] parts = line.split(",");
            if (parts.length < 5) {
                System.err.println("Skipping line: " + line + " - Not enough fields");
                continue;
            }
            String firstName = parts[0].trim();
            String lastName = parts[1].trim();
            String department = parts[2].trim();
            String position = parts[3].trim();
            double salary = 0.0; // Default salary if not provided or parse error
            try {
                salary = Double.parseDouble(parts[4].trim());
            } catch (NumberFormatException e) {
                System.err.println("Error parsing salary for line: " + line);
            }
    
            employees.add(new Employee(firstName, lastName, department, position, salary));
        }
    
        reader.close();
        return employees;
    }
    

    private static Map<String, List<Employee>> groupByDepartment(List<Employee> employees) {
        Map<String, List<Employee>> departmentMap = new HashMap<>();

        for (Employee employee : employees) {
            departmentMap.computeIfAbsent(employee.getDepartment(), k -> new ArrayList<>()).add(employee);
        }

        return departmentMap;
    }

    private static void sortEmployeesBySalary(Map<String, List<Employee>> departmentMap) {
        for (List<Employee> employees : departmentMap.values()) {
            employees.sort(Comparator.comparingDouble(Employee::getSalary));
        }
    }

    private static void printSortedEmployees(Map<String, List<Employee>> departmentMap) {
        for (Map.Entry<String, List<Employee>> entry : departmentMap.entrySet()) {
            System.out.println("Department: " + entry.getKey());
            for (Employee employee : entry.getValue()) {
                System.out.println(employee);
            }
            System.out.println();
        }
    }

    static class Employee {
        private String firstName;
        private String lastName;
        private String department;
        private String position;
        private double salary;

        public Employee(String firstName, String lastName, String department, String position, double salary) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.department = department;
            this.position = position;
            this.salary = salary;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getDepartment() {
            return department;
        }

        public String getPosition() {
            return position;
        }

        public double getSalary() {
            return salary;
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", department='" + department + '\'' +
                    ", position='" + position + '\'' +
                    ", salary=" + salary +
                    '}';
        }
    }
}
