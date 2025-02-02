package com.Employee.Service;

import java.util.List;

import com.Employee.Entity.Employee;

public interface EmployeeService {
	List<Employee> getAllEmployees();
    void saveEmployee(Employee employee);
    Employee getEmployeeById(Long id);
    void deleteEmployeeById(Long id);
}
