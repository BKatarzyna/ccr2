package web;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import endpoint.AccountEndpoint;
import exception.AppBaseException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import model.Driver;
import model.Location;
import web.location.LocationSession;
import web.utils.ContextUtils;


/**
 *
 * @author java
 */
@Named("createDriverAccountPageBean")
@RequestScoped
//@ManagedBean(name = "createDriverAccountPageBean")
//@ViewScoped
public class CreateDriverAccountPageBean {
    
    @Inject
    private AccountEndpoint accountEndpoint;
    @Inject
    private LocationSession locationsession;

    private Driver accountToEdit =  new Driver();
    private List<Location> allLocations;
    private String locationName;
    
    @PostConstruct
    public void init(){
        allLocations = locationsession.getAllLocations();  
    }
    
    public CreateDriverAccountPageBean() {
    }

    public Driver getAccountToEdit() {
        return accountToEdit;
    }

         

    private String passwordRepeat = "";

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }
    
    public void create() throws IOException, AppBaseException {
        if (!(passwordRepeat.equals(accountToEdit.getPassword()))){
            ContextUtils.emitInternationalizedMessage("createDriverAccountForm:passwordRepeat", "passwords.not.matching");
        }  
        String password = Hashing.sha256().hashString(passwordRepeat, Charsets.UTF_8).toString();
        accountToEdit.setPassword(password);
        accountToEdit.setActive(true);
        accountToEdit.setLocationId(locationsession.findLocationByDescription(locationName));
        accountEndpoint.create(accountToEdit);
        FacesContext.getCurrentInstance().getExternalContext().redirect("../car/addCarToDriver.xhtml?id=" + accountToEdit.getId());
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


    
}
