/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.car;

import endpoint.CarEndpoint;
import endpoint.DriverEndpoint;
import exception.AppBaseException;
import java.beans.*;
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
import model.Driver;

/**
 *
 * @author Lenovo
 */
@ManagedBean(name = "addCarToDriverPageBean")
@ViewScoped
public class AddCarToDriverPageBean implements Serializable {
    
    @Inject
    private DriverEndpoint driverEndpoint; 
    @Inject
    private CarEndpoint carEndpoint;
    
    private Driver driverToEdit;
    private Long driverId;
    private List<Car> allCars = new ArrayList<>();
    private List<Car> locationCars = new ArrayList<>();
    private List<Car> driverCars = new ArrayList<>();
    
    
    @PostConstruct
    public void init(){
        Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        driverId = Long.parseLong(params.get("id"));
        driverToEdit = driverEndpoint.findDriver(driverId);
        allCars = carEndpoint.findAllCars();
        for (Car car : allCars) {
            if(car.getLocationId().equals(driverToEdit.getLocationId())){
                locationCars.add(car);
            }
        }
    }

    public String addCars() throws AppBaseException{
        driverToEdit.getCars().addAll(driverCars);
        for (Car car : driverCars) {
            car.getDrivers().add(driverToEdit);
        }
        driverEndpoint.editDriver(driverToEdit);
        return "success";
    }
    public Driver getDriverToEdit() {
        return driverToEdit;
    }

    public Long getDriverId() {
        return driverId;
    }

    public List<Car> getAllCars() {
        return allCars;
    }

    public void setAllCars(List<Car> allCars) {
        this.allCars = allCars;
    }

    public List<Car> getLocationCars() {
        return locationCars;
    }

    public void setLocationCars(List<Car> locationCars) {
        this.locationCars = locationCars;
    }

    public List<Car> getDriverCars() {
        return driverCars;
    }

    public void setDriverCars(List<Car> driverCars) {
        this.driverCars = driverCars;
    }
    
    
    
}
