/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import endpoint.AccountEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import model.Account;
import model.Type;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "accountListPageBean")
@ViewScoped
//@Named(value="accountListPageBean")
//@RequestScoped
public class AccountListPageBean {
    @Inject
    private AccountEndpoint accountEndpoint;
    
    private List<Account> filteredAccounts;
    private Account accountToEdit;
    private List<Account> accounts;
    
    @PostConstruct
    private void initModel() {
        accounts = accountEndpoint.findAllAccounts();
        filteredAccounts = accounts;
    }
    
    public List<Account> getFilteredAccounts() {
        return filteredAccounts;
    }

    public void setFilteredAccounts(List<Account> filteredAccounts) {
        this.filteredAccounts = filteredAccounts;
    }

    public Account getAccountToEdit() {
        return accountToEdit;
    }

    public void setAccountToEdit(Account accountToEdit) {
        this.accountToEdit = accountToEdit;
    }
    
    public void onRowSelect(SelectEvent event) throws IOException {
//       accountEndpoint.setAccountEdit((Account) event.getObject());       
       //przekierowanie do strony z parametrem
       FacesContext.getCurrentInstance().getExternalContext().redirect("editAccount.xhtml?id=" +accountToEdit.getId());
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
    
    
    
    
    
    
//    static final String GENERAL_MSG_ID = "accountListForm:accountList";
//
//    @Inject
//    private AccountSession accountSession;
//    
//    
//    private DataModel<Account> accountDataModel;
//    
//    private String loginPattern = "";
//    private String namePattern = "";
//    private String surnamePattern = "";
//    private String emailPattern = "";
    
//    @PostConstruct
//    private void initModel() {
//        accounts = accountSession.matchAccounts(loginPattern, namePattern, surnamePattern, emailPattern);
//        accountDataModel = new ListDataModel<>(accounts);
//    }
    

//    public DataModel<Account> getAccountDataModel() {
//        return accountDataModel;
//    }
//
//    public AccountListPageBean() {
//    }
//
//    public String getLoginPattern() {
//        return loginPattern;
//    }
//
//    public void setLoginPattern(String loginPattern) {
//        this.loginPattern = loginPattern;
//    }
//
//    public String getNamePattern() {
//        return namePattern;
//    }
//
//    public void setNamePattern(String namePattern) {
//        this.namePattern = namePattern;
//    }
//
//    public String getSurnamePattern() {
//        return surnamePattern;
//    }
//
//    public void setSurnamePattern(String surnamePattern) {
//        this.surnamePattern = surnamePattern;
//    }
//
//    public String getEmailPattern() {
//        return emailPattern;
//    }
//
//    public void setEmailPattern(String emailPattern) {
//        this.emailPattern = emailPattern;
//    }
//
    public List getAccountType(){
        return new ArrayList<>(Arrays.asList(Type.values()));
    }
//    
//    public void refresh() {
//        initModel();
//    }

//    public void clear() {
//        loginPattern = "";
//        namePattern = "";
//        surnamePattern = "";
//        emailPattern = "";
//    }
//
//    public void activateAccount() {
//        accountSession.activateAccount(accountDataModel.getRowData());
//        initModel();
//    }
//
//    public void deactivateAccount() {
//        accountSession.deactivateAccount(accountDataModel.getRowData());
//        initModel();
//    }
//
//    public void confirmAccount() {
//        accountSession.confirmAccount(accountDataModel.getRowData());
//        initModel();
//    }
//
//    public String editAccount() {
//        return accountSession.findAccountToEdit(accountDataModel.getRowData());
//    }
//
//
//    public String findAccountToPasswordChange() {
//        return accountSession.findAccountToPasswordChange(accountDataModel.getRowData());
//    }
//    
//    
}
