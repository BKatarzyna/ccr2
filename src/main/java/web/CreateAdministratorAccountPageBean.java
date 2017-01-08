package web;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import endpoint.AccountEndpoint;
import exception.AppBaseException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Administrator;
import web.utils.ContextUtils;


/**
 *
 * @author java
 */
@Named("createAdministratorAccountPageBean")
@RequestScoped
public class CreateAdministratorAccountPageBean {
    
    @Inject
    private AccountEndpoint accountEndpoint;

    private Administrator accountToEdit =  new Administrator();

    public CreateAdministratorAccountPageBean() {
    }

    public Administrator getAccountToEdit() {
        return accountToEdit;
    }
    
    private String passwordRepeat = "";

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }
    public String createAdministratorAccount() throws AppBaseException{
        if (!(passwordRepeat.equals(accountToEdit.getPassword()))){
            ContextUtils.emitInternationalizedMessage("createAdministratorAccountForm:passwordRepeat", "passwords.not.matching");
            return null;
        } 
        String password = Hashing.sha256().hashString(passwordRepeat, Charsets.UTF_8).toString();
        accountToEdit.setPassword(password);
        accountToEdit.setActive(true);
        accountEndpoint.create(accountToEdit);
        accountToEdit = null;
        return "success";
    }


}
