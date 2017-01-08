/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.location;

import endpoint.LocationEndpoint;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import model.Location;
import web.utils.ContextUtils;

/**
 *
 * @author Lenovo
 */
@SessionScoped
public class LocationSession implements Serializable{

    private static final Logger LOG = Logger.getLogger(LocationSession.class.getName());    
    private static final long serialVersionUID = 1L;
    
    @Inject
    private LocationEndpoint locationEndpoint;

    private List<Location> allLocations;
    
    @PostConstruct
    public void init(){
        LOG.severe("Session started: " + ContextUtils.getSessionID());
        System.out.println(ContextUtils.getSessionID());
        allLocations = locationEndpoint.findAllLocations();                
    }
    public List<Location> getAllLocations() {
        return allLocations;
    }

    public Location findLocationByDescription(String locationName) {
        return locationEndpoint.findLocationByDescription(locationName);
    }

     @PrePassivate 
     
    private void aaaa() {
        LOG.severe("Session prePassivate: ****************************************************" + ContextUtils.getSessionID());
    }
     @PostActivate 
     private void bbb() {
        LOG.severe("Session postActivate: ****************************************************" + ContextUtils.getSessionID());
    }
}
