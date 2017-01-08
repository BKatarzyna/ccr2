/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import endpoint.AccountEndpoint;
import exception.AppBaseException;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import model.Account;
import model.Employee;
import model.Manager;
import web.employee.EmployeeSession;

@ManagedBean(name = "addEmployeePageBean")
@ViewScoped
public class AddEmployeePageBean {
    
    private Account account =  new Account();
    
    @Inject
    private AccountEndpoint accountEndpoint;
    @Inject
    private AccountSession accountSession;
    @Inject
    private EmployeeSession employeeSession;
    
    private Long id;
    private List<Employee> allEmployees;
    private List<Employee> managerEmployees;

     
    @PostConstruct
    private void init() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        id = Long.parseLong(params.get("id"));
        account = accountEndpoint.findAccountByID(id);
        allEmployees = employeeSession.findAllEmployees();  

    }
  
    public Account getAccount() {
        return account;
    }

    public List<Employee> getAllEmployees() {
        return allEmployees;
    }

    public List<Employee> getManagerEmployees() {
        return managerEmployees;
    }

    public void setManagerEmployees(List<Employee> managerEmployees) {
        this.managerEmployees = managerEmployees;
    }

       
    public String saveEmployeeList() throws AppBaseException{        
        for(Employee employee : managerEmployees){
            employee.setManager((Manager)account);
            employeeSession.editEmployee(employee);
        }
        return "success";
    }
    

    // czy to potrzebne?
    public void refresh() {
        init();
    }
}
