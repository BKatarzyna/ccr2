/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import endpoint.AccountEndpoint;
import exception.AppBaseException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Account;
import model.Administrator;
import model.Driver;
import model.Employee;
import model.Manager;
import web.utils.ContextUtils;

/**
 *
 * @author Lenovo
 */
@Named("accountSession")
@SessionScoped
public class AccountSession implements Serializable {

    public void setAccountEdit(Account accountEdit) {
        this.accountEdit = accountEdit;
    }
     
    
    private static final Logger LOG = Logger.getLogger(AccountSession.class.getName());
    private static final long serialVersionUID = 1L;
    
    @Inject
    private AccountEndpoint accountEndpoint;
    
   
    private Manager createManager;
    private Driver createDriver;    
    private Employee createEmployee;        
    private Administrator createAdministrator;

    private Account accountEdit;
    private Account accountChangePassword;

    public AccountSession() {
    }
    
    public String wyloguj() {
        ContextUtils.invalidateSession();
        return "logout";
    }
    
    public String getMyLogin() {
        return ContextUtils.getUserName();
    }
    public Account getAccountEdit() {
        return accountEdit;
    }

    public Account getAccountChangePassword() {
        return accountChangePassword;
    }    
           
    public String createManagerAccount(Manager manager) throws AppBaseException  {
        createManager = manager; 
        accountEndpoint.create(createManager);
        createManager = null;
        return "success";
    }

    public String createDriverAccount(Driver driver) throws AppBaseException {
        createDriver = driver; 
            accountEndpoint.create(createDriver);
     
        createDriver = null;
        return "success";
    }
    public String createEmployeeAccount(Employee employee) throws AppBaseException {
        createEmployee = employee; 
            accountEndpoint.create(createEmployee);
     
        createEmployee = null;
        return "success";
    }
         
    
        
    public String findAccountToEdit(Account account) {
        accountEdit = accountEndpoint.findAccountToEdit(account);
        return "editAccount";
    }

    public String saveEdittedAccount(Account account) throws AppBaseException {
        accountEndpoint.saveEdittedAccount(account);
        return "success";
    }
    public String findAccountToPasswordChange(Account account) {
        this.accountChangePassword = account;
        return "changePassword";
    }
    public String changePassword(String password) throws AppBaseException {
        accountEndpoint.changePassword(accountChangePassword, password);
        return "success";
    }
    
    public String checkAndChangePassword(String oldPassword, String newPassword) throws AppBaseException {
        accountEndpoint.checkAndChangePassword(oldPassword, newPassword);
        return "success";
    }
    
    public List<Account> findAllAccounts() {
        return accountEndpoint.findAllAccounts();
    }

    public List<Account> matchAccounts(String loginPattern, String namePattern, String surnamePattern, String emailPattern) {
        return accountEndpoint.matchAccounts(loginPattern, namePattern, surnamePattern, emailPattern);
    }
    
    public Account findMyAccount() {
        return accountEndpoint.findMyAccount();
    }

    @PostConstruct
    private void init() {
        LOG.severe("Session started: " + ContextUtils.getSessionID());
    }
    
}
