package web;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import exception.AppBaseException;
import java.io.IOException;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import model.Manager;
import web.utils.ContextUtils;

/**
 *
 * @author java
 */
@Named("createManagerAccountPageBean")
@RequestScoped
public class CreateManagerAccountPageBean {

    @Inject
    private AccountSession accountSession;

    private Manager accountToEdit = new Manager();

    public CreateManagerAccountPageBean() {
    }

    public Manager getAccountToEdit() {
        return accountToEdit;
    }

    private String passwordRepeat = "";

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public String create() throws AppBaseException {
        if (!(passwordRepeat.equals(accountToEdit.getPassword()))) {
            ContextUtils.emitInternationalizedMessage("createEmployeeAccountForm:passwordRepeat", "passwords.not.matching");
            return null;
        }
        String password = Hashing.sha256().hashString(passwordRepeat, Charsets.UTF_8).toString();
        accountToEdit.setPassword(password);
        accountToEdit.setActive(true);
        accountSession.createManagerAccount(accountToEdit);
        return "success";
    }

    public void addEmployees() throws IOException, AppBaseException {    
        String create = create();
        FacesContext.getCurrentInstance().getExternalContext().redirect("../../manager/addEmployee.xhtml?id=" +accountToEdit.getId());
    }
}
