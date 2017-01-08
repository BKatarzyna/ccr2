/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Lenovo
 */
@Entity
@DiscriminatorValue("DRIVER")
public class Driver extends Account implements Serializable {

    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "DRIVER_CAR",
        joinColumns = {@JoinColumn(name ="DRIVERS_ACCOUNT_ID", referencedColumnName = "ACCOUNT_ID")},
        inverseJoinColumns = {@JoinColumn(name = "CARS_CARID", referencedColumnName = "CARID")})
    private List<Car> cars = new ArrayList<Car>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "LOCATION_ID")
    private Location locationId;
    
    @OneToMany(mappedBy = "driverId")
    private List<Accessibility> accessibilityIds = new ArrayList<>();
    
   
    public Driver() {
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public Location getLocationId() {
        return locationId;
    }

    public void setLocationId(Location locationId) {
        this.locationId = locationId;
    }

    public List<Accessibility> getAccessibilityIds() {
        return accessibilityIds;
    }

    public void setAccessibilityIds(List<Accessibility> accessibilityIds) {
        this.accessibilityIds = accessibilityIds;
    }

    
}
