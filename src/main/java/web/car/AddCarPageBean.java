/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.car;

import endpoint.CarEndpoint;
import endpoint.CarImageEndpoint;
import endpoint.LocationEndpoint;
import exception.AppBaseException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import model.Car;
import model.CarImage;
import model.Location;

import org.primefaces.event.FileUploadEvent;


@ManagedBean(name = "addCarPageBean")
@ViewScoped
public class AddCarPageBean implements Serializable{

    @Inject
    private CarEndpoint carEndpoint;
    @Inject
    private LocationEndpoint locationEndpoint;
    @Inject
    private CarImageEndpoint carImageEndpoint;
   
    private Car newCar = new Car();
    private CarImage carImage = new CarImage();
    private List<Location> allLocations;
    private String locationName;
    private byte[] image;

    @PostConstruct
    public void init() {
        allLocations = locationEndpoint.findAllLocations();
    }

    public void createCar() throws IOException { 
        newCar.setCarImage(carImage);
        newCar.setLocationId(locationEndpoint.findLocationByDescription(locationName));
        try {
            carEndpoint.createCar(newCar);
        } catch (AppBaseException ex) {
            Logger.getLogger(AddCarPageBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().getExternalContext().redirect("addDriverToCar.xhtml?id=" +newCar.getId());

    }

    public void imageUpload(FileUploadEvent event) throws IOException, AppBaseException {
        byte[] im = event.getFile().getContents();
        carImage.setImage(im);
        carImageEndpoint.createImage(carImage);
    }

    public Car getNewCar() {
        return newCar;
    }

    public void setNewCar(Car newCar) {
        this.newCar = newCar;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
