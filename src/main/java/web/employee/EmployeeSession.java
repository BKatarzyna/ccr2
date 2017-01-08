/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.employee;

import endpoint.EmployeeEndpoint;
import exception.AppBaseException;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import model.Employee;

/**
 *
 * @author Lenovo
 */
@SessionScoped
public class EmployeeSession implements Serializable{

    //to chyba nie musi być dostępne w całej sesji, powinno wystarczyć EmployeeEndpoint
    @Inject
    private EmployeeEndpoint employeeEndpoint;
    
    
    public List<Employee> findAllEmployees() {
        return employeeEndpoint.findAllEmployees();
    }
    public void editEmployee(Employee employee) throws AppBaseException{
        employeeEndpoint.editEmployee(employee);
    }
}
