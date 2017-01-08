/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.location;

import endpoint.LocationEndpoint;
import exception.AppBaseException;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import model.Location;

/**
 *
 * @author Lenovo
 */
@ManagedBean(name = "editLocationPageBean")
@ViewScoped
public class EditLocationPageBean implements Serializable{

    @Inject
    private LocationEndpoint locationEndpoint;
    private Long locationId;
    private Location locationToEdit;

    @PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap();
        locationId = Long.parseLong(params.get("id"));
        locationToEdit = locationEndpoint.findLocation(locationId);

    }

    public String editLocation() throws AppBaseException {
        locationEndpoint.editLocation(locationToEdit);
        return "success";
    }
    public String deleteLocation() throws AppBaseException {
        locationEndpoint.deleteLocation(locationToEdit);
        return "success";
    }
    public String cancel() {
        return "locationList.xhtml";
    }

    public Location getLocationToEdit() {
        return locationToEdit;
    }

    public void setLocationToEdit(Location locationToEdit) {
        this.locationToEdit = locationToEdit;
    }

}
