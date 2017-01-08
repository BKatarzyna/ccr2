/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lenovo
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Location.findByLocationid", query = "SELECT a FROM Location a WHERE a.locationId = :locationid"),
    @NamedQuery(name = "Location.findByLocationDescription", query = "SELECT a FROM Location a WHERE a.description = :description"),
    @NamedQuery(name = "Location.findByLocationShortName", query = "SELECT a FROM Location a WHERE a.shortName = :shortname"),
})
public class Location extends AbstractEntity implements Serializable{
    
    @Id
    @Column(name = "LOCATION_ID", updatable = false)
    @GeneratedValue
    private Long locationId;
    @NotNull
    @Size(min = 3,  max = 64)
    private String city;
    @Size(min = 3,  max = 64)
    @NotNull
    private String street;
    @NotNull
    @Column(unique = true)
    @Size(min = 3,  max = 32)
    private String shortName;
    private Integer number;
    

    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    
    @OneToMany(mappedBy = "locationId")
    private List<Driver> drivers;
    @OneToMany(mappedBy = "locationId")
    private List<Car> cars;

    public Location() {
    }
    
    @Override
    public Object getId() {
        return locationId;
    }
   
    @Override
    protected Object getBusinessKey() {
        return locationId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
 
    
}
