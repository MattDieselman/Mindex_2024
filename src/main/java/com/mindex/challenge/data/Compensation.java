package com.mindex.challenge.data;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Compensation {
    private double salary;
    private Employee employee;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date effectiveDate;

    public Compensation(){

    }

    public double getSalary() {
        return salary;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}