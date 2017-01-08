/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoint;


import exception.AppBaseException;
import facade.AccountFacade;
import facade.AdministratorFacade;
import facade.DriverFacade;
import facade.EmployeeFacade;
import facade.ManagerFacade;
import interceptor.LoggingInterceptor;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import model.Account;
import web.utils.AccountUtils;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

/**
 *
 * @author Lenovo
 */
@Stateful
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AccountEndpoint extends AbstractEndpoint implements SessionSynchronization{
    
    @Inject
    private AccountFacade accountFacade;    
    @Inject
    private EmployeeFacade employeeFacade;
    @Inject
    private ManagerFacade managerFacade;
    @Inject
    private AdministratorFacade administratorFacade;
    @Inject
    private DriverFacade driverFacade;
    
    private Account accountEdit;
    
    public Account findMyAccount() {
        return findLogin(myLogin());
    }

    public String myLogin() throws IllegalStateException {
        return sctx.getCallerPrincipal().getName();
    }
    
    public void create(Account account) throws AppBaseException {
        accountEdit = account;
        try {
        accountFacade.create(accountEdit);
        } catch (AppBaseException ex) {
            Logger.getLogger(AccountEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
  
    public void activateAccount(Account account){
        Account acc = accountFacade.find(account.getId());
        acc.setActive(true);
    }
    
    public void deactivateAccount(Account account){
        Account acc = accountFacade.find(account.getId());
        acc.setActive(false);
    }
    
    public List<Account> findAllAccounts() {
        return accountFacade.findAll();
    }
    
    //sprawdzic zastosowanie
    public List<Account> matchAccounts(String loginPattern, String namePattern, String surnamePattern, String emailPattern) {
        return accountFacade.matchAccounts(loginPattern, namePattern, surnamePattern, emailPattern);
    }
    
    public Account findLogin(String login) {
        return accountFacade.findLogin(login);
    }
    
    public Account findAccountToEdit(Account account) {
        accountEdit = findLogin(account.getLogin());
        return accountEdit;
    }
    
    public void saveEdittedAccount(Account account) throws AppBaseException  {
        if (null == account) {
            throw new IllegalArgumentException("Brak wczytanego konta do modyfikacji");
        }        
        try {
            //wykonej merge() na encji Account, aby weszła ona w stan zarządzany przez obecny kontekst trwalości
            accountFacade.edit(account);
        } catch (AppBaseException ex) {
            Logger.getLogger(AccountEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        //usuń stan konta po zakończonej operacji - unika błędnego wielokrotnego wykonania
        accountEdit=null;
    }
    
    public void checkAndChangePassword(String oldPassword, String newPassword) throws AppBaseException{
        Account myAccount = findMyAccount();
        String sha256PasswordOld = Hashing.sha256().hashString(oldPassword, Charsets.UTF_8).toString();
        if(!sha256PasswordOld.equals(myAccount.getPassword()))
            throw new IllegalArgumentException("Podane hasło jest nieprawidłowe");
        String sha256PasswordNew = Hashing.sha256().hashString(newPassword, Charsets.UTF_8).toString(); 
        changePassword(myAccount, sha256PasswordNew);
    }

    public void changePassword(Account account, String password) throws AppBaseException{        
        Account acc = accountFacade.find(account.getId());
        acc.setPassword(password);
        accountFacade.edit(acc);
    }
  
    public List<Account> findAccountByType(String type) {        
        return accountFacade.findAccountByType(type);
    }

    public Account findAccountByID(Long accountId) {
        return accountFacade.find(accountId);
    }
}
