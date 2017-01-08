/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.car;

import endpoint.CarEndpoint;
import endpoint.CarImageEndpoint;
import exception.AppBaseException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import model.Car;
import model.CarImage;
import model.Driver;
import model.Location;
import org.primefaces.event.FileUploadEvent;
import web.location.LocationSession;

/**
 *
 * @author Lenovo
 */
@ManagedBean(name = "editCarPageBean")
@ViewScoped
public class EditCarPageBean implements Serializable{

    @Inject
    private CarEndpoint carEndpoint;
    @Inject
    private LocationSession locationSession;
    @Inject
    private CarImageEndpoint carImageEndpoint;
    private Long carId;
    private Car carToEdit;
    private List<Location> allLocations = new ArrayList<>();
    private String locationName;
    private CarImage carImage = new CarImage();
    private List<Driver> drivers = new ArrayList<>();

    @PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap();
        carId = Long.parseLong(params.get("id"));
        carToEdit = carEndpoint.findCar(carId);
        allLocations = locationSession.getAllLocations();
        drivers = carToEdit.getDrivers();        
    }

    public String editCar() throws AppBaseException {
        if (locationName != null) {
            carToEdit.setLocationId(locationSession.findLocationByDescription(locationName));
        }
        carToEdit.setCarImage(carImage);
        carEndpoint.editCar(carToEdit);
        return "success";
    }

    public void imageUpload(FileUploadEvent event) throws IOException, AppBaseException {
        byte[] im = event.getFile().getContents();
        carImage.setImage(im);
        carImageEndpoint.createImage(carImage);
    }
    
    public void addDriver() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("addDriverToCar.xhtml?id=" +carToEdit.getId());
    }

    public String deleteCar() throws AppBaseException {
        carEndpoint.deleteCar(carToEdit);
        return "success";
    }

    public String cancel() {
        return "carList.xhtml";
    }

    public Car getCarToEdit() {
        return carToEdit;
    }

    public void setCarToEdit(Car carToEdit) {
        this.carToEdit = carToEdit;
    }

    public List<Location> getAllLocations() {
        return allLocations;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

}
