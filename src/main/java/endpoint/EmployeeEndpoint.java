/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoint;

import exception.AppBaseException;
import facade.EmployeeFacade;
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
import model.Employee;

/**
 *
 * @author Lenovo
 */
@Stateful
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class EmployeeEndpoint extends AbstractEndpoint implements SessionSynchronization{

    @Inject
    private EmployeeFacade employeeFacade;
    
    public List<Employee> findAllEmployees(){
        return employeeFacade.findAllEmployees();
    }
    public void editEmployee(Employee employee) throws AppBaseException{
        try {
            employeeFacade.edit(employee);
        } catch (AppBaseException ex) {
            Logger.getLogger(EmployeeEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
}
