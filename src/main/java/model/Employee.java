/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "EMPLOYEE")
@DiscriminatorValue("EMPLOYEE")
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e"),
    @NamedQuery(name = "Employee.findBySurname", query = "SELECT e FROM Employee e WHERE e.surname = :surname"),
    
    @NamedQuery(name = "Employee.findByPosition", query = "SELECT e FROM Employee e WHERE e.position = :position"),
    @NamedQuery(name = "Employee.findByManager", query = "SELECT e FROM Employee e WHERE e.manager = :manager"),
    @NamedQuery(name = "Employee.findByManagerSurname", query = "SELECT e FROM Employee e WHERE e.manager.surname=:surname"),
    })
@XmlRootElement
public class Employee extends Account implements Serializable {

    @Size(max = 64)
    @Column(name = "POSITION")
    private String position;
    @NotNull
    @ManyToOne//(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MANAGER")
    private Manager manager;

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Employee() {
    }
    //konstruktor wykorzystywany do testów
    public Employee(String type, String login, String password, String name, String surname, String email, String position, Manager manager) {
        super(type, login, password, name, surname, email);
        this.position = position;
        this.manager = manager;
    }

    
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
 
}
