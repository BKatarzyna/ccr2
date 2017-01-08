
package web;

import exception.AppBaseException;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Account;

@Named("changePasswordPageBean")
@RequestScoped
public class ChangePasswordPageBean {
    @Inject
    private AccountSession accountSession;
    private Account account;
    private String oldPassword;
    private String newPassword;
    
    @PostConstruct
    public void init(){
        account = accountSession.findMyAccount();
    }
    
    public String changePassword() throws AppBaseException{
        accountSession.checkAndChangePassword(oldPassword, newPassword);        
        return "success";
    }

    public Account getAccount() {
        return account;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
    
}
