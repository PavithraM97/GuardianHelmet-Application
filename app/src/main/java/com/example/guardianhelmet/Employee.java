package com.example.guardianhelmet;

// Employee.java
public class Employee {
    private String employeeName;
    private long totalWorkTime;
    private long totalBreakTime;

    public Employee() {
        // Default constructor required for Firestore
    }

    public Employee(String employeeName, long totalWorkTime, long totalBreakTime) {
        this.employeeName = employeeName;
        this.totalWorkTime = totalWorkTime;
        this.totalBreakTime = totalBreakTime;
    }

    // Getter methods
    public String getEmployeeName() {
        return employeeName;
    }

    public long getTotalWorkTime() {
        return totalWorkTime;
    }

    public long getTotalBreakTime() {
        return totalBreakTime;
    }
}