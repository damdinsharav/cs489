package edu.miu.cs489.model;

import java.time.LocalDate;

public class Employee {
    long employeeId;
    String firstName;
    String lastName;
    double salary;
    LocalDate employmentDate;
    PensionPlan pensionPlan;

    public Employee(long employeeId, String firstName, String lastName, double salary, LocalDate employmentDate) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.employmentDate = employmentDate;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    public PensionPlan getPensionPlan() {
        return pensionPlan;
    }

    public void setPensionPlan(PensionPlan pensionPlan) {
        this.pensionPlan = pensionPlan;
    }

    public String toJSONString() {
        String employeeJSON = String.format("{\n  \"employeeId\": %d,\n  \"firstName\": \"%s\",\n  \"lastName\": \"%s\",\n  \"salary\": %.2f,\n  \"employmentDate\": \"%s\"",
                employeeId, firstName, lastName, salary, employmentDate.toString());

        if (pensionPlan != null) {
            String pensionPlanJSON = String.format(",\n  \"pensionPlan\": {\n    \"planReferenceNumber\": \"%s\",\n    \"enrollmentDate\": \"%s\",\n    \"monthlyContribution\": %.2f\n  }",
                    pensionPlan.planReferenceNumber, pensionPlan.enrollmentDate.toString(), pensionPlan.monthlyContribution);
            employeeJSON += pensionPlanJSON;
        }

        employeeJSON += "\n}";

        return employeeJSON;
    }
}
