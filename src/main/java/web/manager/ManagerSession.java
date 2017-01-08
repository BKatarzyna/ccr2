/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.manager;

import endpoint.ManagerEndpoint;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import model.Manager;

/**
 *
 * @author Lenovo
 */
@SessionScoped
public class ManagerSession implements Serializable{

     @Inject
    private ManagerEndpoint managerEndpoint;
    
    
    public List<Manager> findAllManagers() {
        return managerEndpoint.findAllManagers();
    }
//    public void editManager(Manager manager){
//        managerEndpoint.editManager(manager);
//    }
}
