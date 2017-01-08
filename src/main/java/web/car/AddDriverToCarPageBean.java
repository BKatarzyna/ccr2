/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.car;

import endpoint.AccountEndpoint;
import endpoint.CarEndpoint;
import exception.AppBaseException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import model.Account;
import model.Car;
import model.Driver;

/**
 *
 * @author Lenovo
 */
@ManagedBean(name = "addDriverToCarPageBean")
@ViewScoped
public class AddDriverToCarPageBean implements Serializable {
    
    @Inject
    private CarEndpoint carEndpoint;

    @Inject
    private AccountEndpoint accountEndpoint;
    private List<Driver> drivers = new ArrayList<>();
    private List<Driver> carDrivers = new ArrayList<>();
    
    private Long carId;
    private Car carToEdit;

    @PostConstruct
    public void init() {
                Map<String, String> params = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap();
        carId = Long.parseLong(params.get("id"));
        carToEdit = carEndpoint.findCar(carId);
        
        List<Account> accounts = accountEndpoint.findAccountByType("DRIVER");
        for (Account account : accounts) {
            drivers.add((Driver)account);
        }
    }
    
    public String addDriver() throws AppBaseException{
        Car c = carEndpoint.findCarByRegistrationNumber(carToEdit.getRegistrationNumber());
        c.getDrivers().addAll(carDrivers);
        for (Driver driver : carDrivers) {
            driver.getCars().add(c);
        }
        carEndpoint.editCar(c);
        return "success";
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public List<Driver> getCarDrivers() {
        return carDrivers;
    }

    public void setCarDrivers(List<Driver> carDrivers) {
        this.carDrivers = carDrivers;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Car getCarToEdit() {
        return carToEdit;
    }

    public void setCarToEdit(Car carToEdit) {
        this.carToEdit = carToEdit;
    }
 
    
}
