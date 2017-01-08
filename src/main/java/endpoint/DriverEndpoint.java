/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoint;

import exception.AppBaseException;
import facade.DriverFacade;
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
import model.Driver;

/**
 *
 * @author Lenovo
 */
@Stateful
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class DriverEndpoint extends AbstractEndpoint implements SessionSynchronization{

    @Inject
    private DriverFacade driverFacade;
    
    public List findAllDrivers(){
        return driverFacade.findAll();
    }
    public void createDriver(Driver newDriver) throws AppBaseException{
        try {
            driverFacade.create(newDriver);
        } catch (AppBaseException ex) {
            Logger.getLogger(DriverEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public Driver findDriver(long driverId) {
        return driverFacade.find(driverId);
    }
    
    public void editDriver(Driver driverToEdit) throws AppBaseException  {        
        try {
            driverFacade.edit(driverToEdit);
        } catch (AppBaseException ex) {
            Logger.getLogger(DriverEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

}
