/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "ADMINISTRATOR")
@DiscriminatorValue("ADMINISTRATOR")
public class Administrator extends Account implements Serializable {

    public Administrator() {
    }
       
}
