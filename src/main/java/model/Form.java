/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lenovo
 */
@Table(name = "FORM")
@NamedQueries({
    @NamedQuery(name = "Form.findAll", query = "SELECT f FROM Form f"),
    @NamedQuery(name = "Form.findByStatus", query = "SELECT f FROM Form f WHERE f.status = :status"),
    @NamedQuery(name = "Form.findByEmployeeCreated", query = "SELECT f FROM Form f WHERE f.employeeCreatedId.login = :login"),
    @NamedQuery(name = "Form.findByManagerAccepted", query = "SELECT f FROM Form f WHERE f.managerAcceptedId.login = :login"),
    
    @NamedQuery(name = "Form.findFormToAccept", query = "SELECT f FROM Form f WHERE  f.employeeCreatedId = :employeeCreatedId"),
    @NamedQuery(name = "Form.findByDriver", query = "SELECT f FROM Form f WHERE f.accessibilityId.driverId.login = :login")})
       
@Entity
public class Form extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long formId;

    @NotNull
    @Column(name = "NUMBER_OF_PEOPLE")
    private Integer numberOfPeople;
    @NotNull
    @Column(name = "STATUS")
    public Status status;
    @NotNull
    @JoinColumn(name = "EMPLOYEE_CREATED_ID")//, referencedColumnName = "ACCOUNTID")
    @OneToOne(optional = false)
    private Employee employeeCreatedId;
    
    @ManyToOne
    @JoinColumn(name = "MANAGER_ACCEPTED_ID")
    private Manager managerAcceptedId;
    @NotNull
    @JoinColumn(name = "ACCESSIBILITY_ID")//, referencedColumnName = "ACCESSIBILITYID")
    @OneToOne(optional = false, cascade = CascadeType.ALL)//, cascade = {CascadeType.PERSIST, CascadeType.REMOVE} - powoduje tworzenie drugi raz obiektu, który jest w bazie
    private Accessibility accessibilityId;

    private String comments;
    
    public Long getId() {
        return formId;
    }

    public Form() {
    }
    //konstruktor wykorzystywany do testów
    public Form(Integer numberOfPeople, Status status, Employee employeeCreatedId, Accessibility accessibilityId) {
        this.numberOfPeople = numberOfPeople;
        this.status = status;
        this.employeeCreatedId = employeeCreatedId;
        this.accessibilityId = accessibilityId;
    }

    public Manager getManagerAcceptedId() {
        return managerAcceptedId;
    }

    public void setManagerAcceptedId(Manager managerAcceptedId) {
        this.managerAcceptedId = managerAcceptedId;
    }

    
    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Employee getEmployeeCreatedId() {
        return employeeCreatedId;
    }

    public void setEmployeeCreatedId(Employee employeeCreatedId) {
        this.employeeCreatedId = employeeCreatedId;
    }

    public Accessibility getAccessibilityId() {
        return accessibilityId;
    }

    public void setAccessibilityId(Accessibility accessibilityId) {
        this.accessibilityId = accessibilityId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }       
    
    @Override
    protected Object getBusinessKey() {
        return formId;
    }
    
}
