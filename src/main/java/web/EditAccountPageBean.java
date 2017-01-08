/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import endpoint.AccountEndpoint;
import endpoint.ManagerEndpoint;
import exception.AppBaseException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import model.Account;
import model.Driver;
import model.Employee;
import model.Location;
import model.Manager;
import web.location.LocationSession;
import web.utils.AccountUtils;

/**
 *
 * @author Lenovo
 */
//@Named(value="editAccountPageBean")
//@RequestScoped
@ManagedBean(name = "editAccountPageBean")
@ViewScoped
public class EditAccountPageBean implements Serializable {

    @Inject
    private AccountEndpoint accountEndpoint;
    private Long accountId;
    private Account accountToEdit;
    @Inject
    private LocationSession locationSession;
    private List<Location> allLocations = new ArrayList<>();
    private String locationName;

    @Inject
    private ManagerEndpoint managerEndpoint;
    private List<Manager> allManagers = new ArrayList<>();
    private String managerName;

    @PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap();
        accountId = Long.parseLong(params.get("id"));
        accountToEdit = accountEndpoint.findAccountByID(accountId);
        allLocations = locationSession.getAllLocations();
        allManagers = managerEndpoint.findAllManagers();
    }

    public String editAccount() throws AppBaseException {
        if (accountToEdit instanceof Employee) {
            Employee account = (Employee) accountToEdit;
            for (Manager m : allManagers) {
                if (m.toString().equals(managerName)) {
                    account.setManager(m);
                }
            }
            accountToEdit = account;
        }
        if (accountToEdit instanceof Driver) {
            Driver account = (Driver) accountToEdit;
            account.setLocationId(locationSession.findLocationByDescription(locationName));
            accountToEdit = account;
        }

        accountEndpoint.saveEdittedAccount(accountToEdit);
        return "success";
    }

    
    
    public String cancel() {
        return "accountList.xhtml";
    }

    public void activateAccount() {
        accountEndpoint.activateAccount(accountToEdit);
//        ContextUtils.emitSuccessMessage(AccountListPageBean.GENERAL_MSG_ID);
    }

    public void deactivateAccount() {
        accountEndpoint.deactivateAccount(accountToEdit);
//        ContextUtils.emitSuccessMessage(AccountListPageBean.GENERAL_MSG_ID);
    }

    public Account getAccountToEdit() {
        return accountToEdit;
    }

    public void setAccountToEdit(Account accountToEdit) {
        this.accountToEdit = accountToEdit;
    }

    public boolean isDriver() {
        return AccountUtils.isDriver(accountToEdit);
    }

    public boolean isEmployee() {
        return AccountUtils.isEmployee(accountToEdit);
    }

    public boolean isAdministrator() {
        return AccountUtils.isAdministrator(accountToEdit);
    }

    public boolean isManager() {
        return AccountUtils.isManager(accountToEdit);
    }

    public List<Location> getAllLocations() {
        return allLocations;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public List<Manager> getAllManagers() {
        return allManagers;
    }

    public void setAllManagers(List<Manager> allManagers) {
        this.allManagers = allManagers;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }



}
