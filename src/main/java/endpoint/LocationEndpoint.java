/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoint;

import exception.AppBaseException;
import facade.LocationFacade;
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
import model.Location;

/**
 *
 * @author Lenovo
 */
@Stateful
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class LocationEndpoint extends AbstractEndpoint implements SessionSynchronization{

    @Inject
    private LocationFacade locationFacade;
    
    public List findAllLocations(){
        return locationFacade.findAll();
    }
    public void createLocation(Location newLocation) throws AppBaseException{
        try {
            locationFacade.create(newLocation);
        } catch (AppBaseException ex) {
            Logger.getLogger(LocationEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public Location findLocation(long locationId) {
        return locationFacade.find(locationId);
    }
    
    public void editLocation(Location locationToEdit) throws AppBaseException  {        
        try {
            locationFacade.edit(locationToEdit);
        } catch (AppBaseException ex) {
            Logger.getLogger(LocationEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public void deleteLocation(Location locationToEdit) throws AppBaseException {
        try {
            locationFacade.remove(locationToEdit);
        } catch (AppBaseException ex) {
            Logger.getLogger(LocationEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public Location findLocationByDescription(String locationName) {
        return locationFacade.findLocationByDescription(locationName);
    }
}
