/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.location;

import endpoint.LocationEndpoint;
import exception.AppBaseException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Location;

/**
 *
 * @author Lenovo
 */
@Named("addLocationPageBean")
@RequestScoped
public class AddLocationPageBean {
    
    @Inject
    private LocationEndpoint locationEndpoint;
    
    private Location newLocation = new Location();
     
    public String createLocation() throws AppBaseException{
        locationEndpoint.createLocation(newLocation);
        return "success";
    }
    public Location getNewLocation() {
        return newLocation;
    }

    public void setNewLocation(Location newLocation) {
        this.newLocation = newLocation;
    }

    
    
}
