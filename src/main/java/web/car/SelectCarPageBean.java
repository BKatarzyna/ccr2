/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.car;

import endpoint.AccessibilityEndpoint;
import endpoint.CarEndpoint;
import endpoint.DriverEndpoint;
import endpoint.FormEndpoint;
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
import model.Accessibility;
import model.Car;
import model.Driver;
import model.Form;
import model.Status;

/**
 *
 * @author Lenovo
 */
//@Named("selectCarPageBean")
//@RequestScoped
@ManagedBean(name = "selectCarPageBean")
@ViewScoped
public class SelectCarPageBean implements Serializable {

    @Inject
    private CarEndpoint carEndpoint;
    @Inject
    private AccessibilityEndpoint accessibilityEndpoint;
    @Inject
    private FormEndpoint formEndpoint;
    @Inject
    private DriverEndpoint driverEndpoint;

    private Accessibility accessibility = new Accessibility();
    private Long carId;
    private Long formId;
    private Car carToSelect;
    private Form form;
    private Driver driver = new Driver();
    private String driverName;
    private List<Driver> drivers = new ArrayList<>();

    @PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap();
        carId = Long.parseLong(params.get("id"));
        formId = Long.parseLong(params.get("formId"));
        carToSelect = carEndpoint.findCar(carId);
        form = formEndpoint.findForm(formId);

        checkDriverAvailability(form);
    }

    public void checkDriverAvailability(Form form) {
        boolean available = true;
        for (Driver d : carToSelect.getDrivers()) {
            for (Accessibility access : d.getAccessibilityIds()) {
                if ((form.getAccessibilityId().getDepartureDate().before(access.getReturnDate()) && form.getAccessibilityId().getDepartureDate().after(access.getDepartureDate()))
                        || (form.getAccessibilityId().getReturnDate().before(access.getReturnDate()) && form.getAccessibilityId().getReturnDate().after(access.getDepartureDate()))) {
                    available = false;
                }
            }
            if (available) {
                drivers.add(d);
            }
            available = true;
        }
    }

    public String carSelected() throws AppBaseException {
        for (Driver d : drivers) {
            if (driverName.equals(d.toString())) {
                driver = d;
            }
        }

        form.getAccessibilityId().setCarId(carToSelect);
        form.getAccessibilityId().setFreeSeatsNumber(carToSelect.getNumberOfSeats() - form.getNumberOfPeople());
        form.getAccessibilityId().setDriverId(driver);
        form.setStatus(Status.RESERVED);
        driver.getAccessibilityIds().add(form.getAccessibilityId());
        carToSelect.getAccessibilityIds().add(form.getAccessibilityId());        
        formEndpoint.editForm(form);

        carEndpoint.editCar(carToSelect);
//        driverEndpoint.editDriver(driver);
        return "success";
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Car getCarToSelect() {
        return carToSelect;
    }

    public void setCarToSelect(Car carToSelect) {
        this.carToSelect = carToSelect;
    }

    public Long getCarId() {
        return carId;
    }

    public Long getFormId() {
        return formId;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

}
