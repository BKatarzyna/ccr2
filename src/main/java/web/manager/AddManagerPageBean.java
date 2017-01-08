/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.manager;

import exception.AppBaseException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Account;
import model.Employee;
import model.Manager;
import web.AccountSession;
import web.employee.EmployeeSession;

/**
 *
 * @author Lenovo
 */
@Named("addManagerPageBean")
@RequestScoped
public class AddManagerPageBean {

    private Account account = new Account();

    @Inject
    private AccountSession accountSession;
    @Inject
    private EmployeeSession employeeSession;
    @Inject
    private ManagerSession managerSession;

    private List<Employee> allEmployees;
    private List<Employee> managerEmployees;
    private List<Manager> allManagers;
    private Manager employeeManager;

    @PostConstruct
    private void init() {
        account = accountSession.getAccountEdit();
        allEmployees = employeeSession.findAllEmployees();
        allManagers = managerSession.findAllManagers();
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

    public List<Manager> getAllManagers() {
        return allManagers;
    }

    public Manager getEmployeeManager() {
        return employeeManager;
    }

    public void setEmployeeManager(Manager employeeManager) {
        this.employeeManager = employeeManager;
    }

    public String addEmployees() {
        return "addEmployees";
    }

    public String saveEmployeeList() throws AppBaseException {
        //manager.getEmployees().addAll(managerEmployees);
        for (Employee employee : managerEmployees) {
            employee.setManager((Manager) account);
            employeeSession.editEmployee(employee);
        }
        return "success";
    }

    public String saveManager() throws AppBaseException {
        Employee employee = (Employee) account;
        employee.setManager(employeeManager);
        employeeSession.editEmployee(employee);

        return "success";
    }

    // czy to potrzebne?
    public void refresh() {
        init();
    }

}
