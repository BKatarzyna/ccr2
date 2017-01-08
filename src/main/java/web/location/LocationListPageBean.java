/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.location;

import endpoint.LocationEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import model.Location;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Lenovo
 */
@Named("locationListPageBean")
@RequestScoped
public class LocationListPageBean  {
    
    @Inject
    private LocationEndpoint locationEndpoint;
    
    private Location location;
    private List<Location> allLocations;
    private List<Location> filteredLocations = new ArrayList<>();
    
    @PostConstruct
    public void init(){
        allLocations = locationEndpoint.findAllLocations();
        filteredLocations = allLocations;
    }
    
    public List<Location> getAllLocations() {
        return allLocations;
    }

    public List<Location> getFilteredLocations() {
        return filteredLocations;
    }

    public void setFilteredLocations(List<Location> filteredLocations) {
        this.filteredLocations = filteredLocations;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

      
    public void onRowSelect(SelectEvent event) throws IOException {
        if (event != null){
            //tego n ie ma, bo przy kliknięciu na wiersz tabeli ustala się obiekt location
//       location = ((Location) event.getObject());     
       
       //przekierowanie do strony z parametrem
       FacesContext.getCurrentInstance().getExternalContext().redirect("editLocation.xhtml?id=" +location.getId());
        }
    }
}
