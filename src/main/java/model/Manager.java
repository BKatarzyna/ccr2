/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("MANAGER")
@NamedQuery(name = "Manager.findEmployess", query = "SELECT a FROM Manager a WHERE a.type = :type")
public class Manager extends Account implements Serializable {
        
    
    private String department;
    
    @OneToMany(mappedBy = "manager")
    private List<Employee> employees = new ArrayList<Employee>();
    
    @OneToMany
    private List<Form> forms = new ArrayList<Form>();

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Form> getForms() {
        return forms;
    }

    public void setForms(List<Form> forms) {
        this.forms = forms;
    }

    public Manager() {
    }
    //konstruktor wykorzystywany do testów
    public Manager(String department, String type, String login, String password, String name, String surname, String email) {
        super(type, login, password, name, surname, email);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    } 
}
