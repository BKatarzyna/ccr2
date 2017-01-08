/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lenovo
 */

//@NamedQueries({
//    @NamedQuery(name = "Accessibility.findAll", query = "SELECT a FROM Accessibility a"),
//    @NamedQuery(name = "Accessibility.findByAccessibilityid", query = "SELECT a FROM Accessibility a WHERE a.accessibilityId = :accessibilityid"),
//    @NamedQuery(name = "Accessibility.findByDestination", query = "SELECT a FROM Accessibility a WHERE a.destination = :destination"),
//    @NamedQuery(name = "Accessibility.findByFreeseatsnumber", query = "SELECT a FROM Accessibility a WHERE a.freeSeatsNumber = :freeseatsnumber"),
//    @NamedQuery(name = "Accessibility.findByDeparturedate", query = "SELECT a FROM Accessibility a WHERE a.departureDate = :departuredate"),
//    @NamedQuery(name = "Accessibility.findByReturndate", query = "SELECT a FROM Accessibility a WHERE a.returnDate = :returndate")})
@Entity
public class Accessibility extends AbstractEntity implements Serializable {

    @Id
    @Column(name = "ACCESSIBILITY_ID", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accessibilityId;
    @NotNull
    @Size(min = 3, max = 64)
    @Column(name = "DEPARTURE_FROM", nullable = false)
    private String departureFrom;
    @NotNull
    @Size(min = 3, max = 64)
    @Column(name = "DESTINATION", nullable = false)
    private String destination;
    @Column(name = "FREE_SEATS_NUMBER")
    private int freeSeatsNumber;
    @NotNull
    @Column(name = "DEPARTURE_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    @Future
    private Date departureDate;
    @NotNull
    @Column(name = "DEPARTURE_TIME", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date departureTime;
    
    @NotNull
    @Column(name = "RETURN_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    @Future
    private Date returnDate;
    @NotNull
    @Column(name = "RETURN_TIME", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date returnTime;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "DRIVERID")
    private Driver driverId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CARID")
    private Car carId;

    public Accessibility() {
    }
    //konstruktor wykorzystywany do testów
    public Accessibility(String departureFrom, String destination, int freeSeatsNumber, Date departureDate, Date departureTime, Date returnDate, Date returnTime) {
        this.departureFrom = departureFrom;
        this.destination = destination;
        this.freeSeatsNumber = freeSeatsNumber;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.returnDate = returnDate;
        this.returnTime = returnTime;
    }

    public Long getId() {
        return accessibilityId;
    }

    @Override
    protected Object getBusinessKey() {
        return accessibilityId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getFreeSeatsNumber() {
        return freeSeatsNumber;
    }

    public void setFreeSeatsNumber(int freeSeatsNumber) {
        this.freeSeatsNumber = freeSeatsNumber;
    }

    public String getDepartureFrom() {
        return departureFrom;
    }

    public void setDepartureFrom(String departureFrom) {
        this.departureFrom = departureFrom;
    }

    public Driver getDriverId() {
        return driverId;
    }

    public void setDriverId(Driver driverId) {
        this.driverId = driverId;
    }

    public Car getCarId() {
        return carId;
    }

    public void setCarId(Car carId) {
        this.carId = carId;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }


    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }


}
