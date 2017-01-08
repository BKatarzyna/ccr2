package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lenovo
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Car.findByCarId", query = "SELECT a FROM Car a WHERE a.carId = :carid"),
    @NamedQuery(name = "Car.findCarByRegistrationNumber", query = "SELECT a FROM Car a WHERE a.registrationNumber = :registrationnumber"),})
public class Car extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long carId;

    private String model;
    @Size(max = 11)
    private String registrationNumber;
        
    @Size(max = 255)
    private String description;
    @NotNull
    private Integer numberOfSeats;
    
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "CAR_IMAGE")
    private CarImage carImage;
    
    //@NotNull
    @ManyToOne
    @JoinColumn(name = "LOCATIONID")
    private Location locationId;

    @ManyToMany(mappedBy = "cars", cascade = CascadeType.MERGE )
    private List<Driver> drivers = new ArrayList<Driver>();

    @OneToMany(mappedBy = "carId")
    private List<Accessibility> accessibilityIds = new ArrayList<>();

    public Car() {
    }
    
    public Car(String model, String registrationNumber, Integer numberOfSeats) {
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.numberOfSeats = numberOfSeats;
    }
    
    @Override
    public Object getId() {
        return carId;
    }

    @Override
    protected Object getBusinessKey() {
        return carId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Location getLocationId() {
        return locationId;
    }

    public void setLocationId(Location locationId) {
        this.locationId = locationId;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public CarImage getCarImage() {
        return carImage;
    }

    public void setCarImage(CarImage carImage) {
        this.carImage = carImage;
    }
    
    public List<Accessibility> getAccessibilityIds() {
        return accessibilityIds;
    }

    public void setAccessibilityIds(List<Accessibility> accessibilityIds) {
        this.accessibilityIds = accessibilityIds;
    }

}
