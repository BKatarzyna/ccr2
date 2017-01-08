/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.car;

import endpoint.CarEndpoint;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import model.Car;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Lenovo
 */
//@Named("carListPageBean")
//@RequestScoped
@ManagedBean(name = "carListPageBean")
@ViewScoped
public class CarListPageBean implements Serializable{
    
    @Inject
    private CarEndpoint carEndpoint;
    
    private Car car;
    private List<Car> allCars;
    private List<Car> filteredCars = new ArrayList<>();
    
    @PostConstruct
    public void init(){
        allCars = carEndpoint.findAllCars();
        filteredCars = allCars;
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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

      
    public void onRowSelect(SelectEvent event) throws IOException {
        if (event != null){
            //tego n ie ma, bo przy kliknięciu na wiersz tabeli ustala się obiekt car
//       car = ((Car) event.getObject());     
       //przekierowanie do strony z parametrem
       
       FacesContext.getCurrentInstance().getExternalContext().redirect("editCar.xhtml?id=" +car.getId());
        }
    }
}
