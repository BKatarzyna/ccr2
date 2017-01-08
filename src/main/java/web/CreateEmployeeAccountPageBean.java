package web;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import endpoint.AccountEndpoint;
import endpoint.ManagerEndpoint;
import exception.AppBaseException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Employee;
import model.Manager;
import web.utils.ContextUtils;


/**
 *
 * @author java
 */
@Named("createEmployeeAccountPageBean")
@RequestScoped
public class CreateEmployeeAccountPageBean {
    
    @Inject
    private AccountEndpoint accountEndpoint;
    @Inject
    private ManagerEndpoint managerEndpoint;
    private List<Manager> allManagers = new ArrayList<>();
    private String managerName;
    private Long managerId;

    private Employee accountToEdit =  new Employee();
    
    @PostConstruct
    public void init() {        
        allManagers = managerEndpoint.findAllManagers();
    }

    public CreateEmployeeAccountPageBean() {
    }

    public Employee getAccountToEdit() {
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
        if (!(passwordRepeat.equals(accountToEdit.getPassword()))){
            ContextUtils.emitInternationalizedMessage("createEmployeeAccountForm:passwordRepeat", "passwords.not.matching");
            return null;
        } 
        String password = Hashing.sha256().hashString(passwordRepeat, Charsets.UTF_8).toString();
        accountToEdit.setPassword(password);
        accountToEdit.setActive(true);
        accountToEdit.setManager(managerEndpoint.findManagerById(managerId));
        accountEndpoint.create(accountToEdit);
        accountToEdit = null;
        return "success";
    }

    public List<Manager> getAllManagers() {
        return allManagers;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }
    
}
