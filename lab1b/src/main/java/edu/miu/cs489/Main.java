package edu.miu.cs489;

import edu.miu.cs489.model.Employee;
import edu.miu.cs489.model.PensionPlan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        ArrayList<Employee> employees = PopulateEmployees();
        PrintListOfEmployeesSortedByLastnameAndSalary(employees);
        System.out.println("-----------------------------------------");
        PrintMonthlyUpcomingEnrollees(employees);
    }

    private static ArrayList<Employee> PopulateEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "Daniel", "Agar", 105945.50,LocalDate.of(2018, 1, 17)));
        employees.add(new Employee(2L, "Benard", "Shaw", 197750.00,LocalDate.of(2019, 4, 3)));
        employees.add(new Employee(3L, "Carly", "Agar", 842000.75,LocalDate.of(2014, 5, 16)));
        employees.add(new Employee(4L, "Wesley", "Schneider", 74500.00,LocalDate.of(2019, 5, 2)));
        employees.get(0).setPensionPlan(new PensionPlan("EX1089", LocalDate.of(2023,1,17), 100.00));
        employees.get(2).setPensionPlan(new PensionPlan("SM2307", LocalDate.of(2019,11,4), 1555.50));
        return employees;
    }

    private static void PrintListOfEmployeesSortedByLastnameAndSalary(ArrayList<Employee> employees) {
        // Sort the list
        employees.sort((e1, e2) -> {
            // Sort by last name in ascending order
            int lastNameComparison = e1.getLastName().compareTo(e2.getLastName());
            if (lastNameComparison != 0) {
                return lastNameComparison;
            } else {
                // Sort by yearly salary in descending order
                return Double.compare(e2.getSalary(), e1.getSalary());
            }
        });
        for(Employee employee:employees) {
            System.out.println(employee.toJSONString());
        }
    }

    private static void PrintMonthlyUpcomingEnrollees(ArrayList<Employee> employees) {
        employees.sort(Comparator.comparing(Employee::getEmploymentDate));
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfNextMonth = today.plusMonths(1).withDayOfMonth(1);
        LocalDate lastDayOfNextMonth = firstDayOfNextMonth.withDayOfMonth(firstDayOfNextMonth.lengthOfMonth());

        for (Employee employee : employees) {
            // Check if employee is not enrolled and their 5th year of employment date falls within next month
            if (employee.getPensionPlan() == null &&
                    (employee.getEmploymentDate().plusYears(5).isAfter(firstDayOfNextMonth.minusDays(1)) &&
                            employee.getEmploymentDate().plusYears(5).isBefore(lastDayOfNextMonth.plusDays(1)))) {
                System.out.println(employee.toJSONString());
            }
        }

    }

}