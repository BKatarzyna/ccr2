/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.car;

import endpoint.CarEndpoint;
import endpoint.FormEndpoint;
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

@ManagedBean(name = "bookCarPageBean")
@ViewScoped
public class BookCarPageBean implements Serializable{

    @Inject
    private FormEndpoint formEndpoint;
    private Form form;
    private long formId;

    @Inject
    private CarEndpoint carEndpoint;

//    private Car car;
    private List<Car> allCars;
    private List<Car> filteredCars = new ArrayList<>();

    @PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap();
        formId = Long.parseLong(params.get("id"));
        form = formEndpoint.findForm(formId);
        checkCarAvailability(form);
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

    public void selectCar() throws IOException {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String id = params.get("id");
        FacesContext.getCurrentInstance().getExternalContext().redirect("selectCar.xhtml?id=" + id + "&formId=" + formId);
    }

    public void checkCarAvailability(Form form) {
        boolean available;
        allCars = carEndpoint.findAllCars();
        for (Car car : allCars) {
            available = true;
            if (form.getNumberOfPeople() <= car.getNumberOfSeats()) {
                for (Accessibility access : car.getAccessibilityIds()) {
                    if ((form.getAccessibilityId().getDepartureDate().before(access.getReturnDate()) && form.getAccessibilityId().getDepartureDate().after(access.getDepartureDate()))
                            || (form.getAccessibilityId().getReturnDate().before(access.getReturnDate()) && form.getAccessibilityId().getReturnDate().after(access.getDepartureDate()))) {
                        available = false;
                    }
                }
                if (available) {
                    filteredCars.add(car);
                }                
            }
        }
    }
}
