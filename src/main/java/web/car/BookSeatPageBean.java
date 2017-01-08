/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.car;

import endpoint.AccessibilityEndpoint;
import endpoint.CarEndpoint;
import endpoint.FormEndpoint;
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
import model.Accessibility;
import model.Car;
import model.Form;

/**
 *
 * @author Lenovo
 */
@ManagedBean(name = "bookSeatPageBean")
@ViewScoped
public class BookSeatPageBean implements Serializable{

    @Inject
    private FormEndpoint formEndpoint;
    private Form form;
    private long formId;

    @Inject
    private CarEndpoint carEndpoint;
    @Inject
    private AccessibilityEndpoint accessibilityEndpoint;

    private Car carToSelect = new Car();
    private List<Car> allCars;
    private List<Car> filteredCars = new ArrayList<>();
    private List<Accessibility> accessibilityToEdit = new ArrayList<>();

    @PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap();
        formId = Long.parseLong(params.get("id"));
        form = formEndpoint.findForm(formId);
        checkSeatAvailability(form);
    }

    public List<Car> getAllCars() {
        return allCars;
    }

    public List<Car> getFilteredCars() {
        return filteredCars;
    }

    public void setFilteredCars(List<Car> filteredCars) {
        this.filteredCars = filteredCars;
    }

    public String selectCar() throws IOException, AppBaseException {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Long id = Long.parseLong(params.get("id"));

        carToSelect = carEndpoint.findCar(id);

        form.getAccessibilityId().setCarId(carToSelect);

        if (!accessibilityToEdit.isEmpty()) {
            int freeSeats = accessibilityToEdit.get(0).getFreeSeatsNumber() - form.getNumberOfPeople();
            for (Accessibility access : accessibilityToEdit) {
                access.setFreeSeatsNumber(freeSeats);
                accessibilityEndpoint.edit(access);
            }
            form.getAccessibilityId().setFreeSeatsNumber(freeSeats);
            form.getAccessibilityId().setDriverId(accessibilityToEdit.get(0).getDriverId());
        }

//        driver.getAccessibilityIds().add(form.getAccessibilityId());
        carToSelect.getAccessibilityIds().add(form.getAccessibilityId());
        formEndpoint.editForm(form);

        return "success";
    }

    public void checkSeatAvailability(Form form) {
        boolean available;
        allCars = carEndpoint.findAllCars();
        for (Car car : allCars) {
            available = false;
            for (Accessibility access : car.getAccessibilityIds()) {
                if (form.getAccessibilityId().getDepartureFrom().equals(access.getDepartureFrom())
                        && form.getAccessibilityId().getDepartureDate().equals(access.getDepartureDate())
                        && form.getAccessibilityId().getDestination().equals(access.getDestination())
                        && form.getAccessibilityId().getReturnDate().equals(access.getReturnDate())
                        && form.getNumberOfPeople() <= access.getFreeSeatsNumber()) {
                    available = true;
                    accessibilityToEdit.add(access);
                }
                if (available) {
                    filteredCars.add(car);
                }
            }
        }
    }

    public Car getCarToSelect() {
        return carToSelect;
    }

    public void setCarToSelect(Car carToSelect) {
        this.carToSelect = carToSelect;
    }

    public List<Accessibility> getAccessibilityToEdit() {
        return accessibilityToEdit;
    }

    
}
