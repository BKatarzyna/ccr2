/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoint;

import exception.AppBaseException;
import facade.AccountFacade;
import facade.EmployeeFacade;
import facade.FormFacade;
import facade.ManagerFacade;
import interceptor.LoggingInterceptor;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import model.Account;
import model.Employee;
import model.Manager;

/**
 *
 * @author Lenovo
 */
@Stateful
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ManagerEndpoint extends AbstractEndpoint implements SessionSynchronization{

    @Inject
    private EmployeeFacade employeeFacade;
    @Inject
    private FormFacade formFacade;
    @Inject
    private ManagerFacade managerFacade;
    @Inject
    private AccountFacade accountFacade;
    
    private List<Account> employees;
    
    public void findEmployess(){
        employees = accountFacade.findAccountByType("employee");
    }

    public void addEmployees(List<Employee> managerEmployees) {
        managerFacade.addEmployees(managerEmployees);
    }

    public List<Manager> findAllManagers() {
        return managerFacade.findAll();
    }

    public void editManager(Manager manager) throws AppBaseException {
        try {
            managerFacade.edit(manager);
        } catch (AppBaseException ex) {
            Logger.getLogger(ManagerEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public Manager findManagerById(Long id) {
        return managerFacade.find(id);
    }
    
}
