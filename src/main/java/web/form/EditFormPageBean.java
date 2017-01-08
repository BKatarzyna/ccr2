/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.form;

import exception.AppBaseException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import model.Account;
import model.Driver;
import model.Form;
import model.Status;
import static model.Status.ACCEPTED;
import static model.Status.CANCELED;
import static model.Status.COMMENTED;
import static model.Status.REALIZED;
import static model.Status.RESERVED;
import static model.Status.SEND;
import web.AccountSession;
import web.utils.ContextUtils;

/**
 *
 * @author Lenovo
 */
@Named("editFormPageBean")
@RequestScoped
public class EditFormPageBean {
    
    @Inject
    private AccountSession accountSession;
   @Inject
   private FormSession formSession;
   
   private Form formToEdit;
   
   @PostConstruct
   public void init(){
       formToEdit = formSession.getFormToEdit();
       //String id =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
   }

    public Form getFormToEdit() {
        return formToEdit;
    }
       
    public String cancel(){
        return formSession.cancel();
    }
    
    public String editForm() throws AppBaseException{
        return formSession.editForm(formToEdit);
    }
    public String deleteForm() throws AppBaseException{
        return formSession.deleteForm(formToEdit);
    }
   public String sendForm() throws AppBaseException{
       formToEdit.setStatus(Status.SEND);
       formSession.setFormManager(formToEdit);
       return editForm();
   }
   public String commentForm() throws AppBaseException{
       formToEdit.setStatus(Status.COMMENTED);
       return editForm();
   }
   public String acceptForm() throws AppBaseException{
       formToEdit.setStatus(Status.ACCEPTED);
       return editForm();
   }
   public String cancelReservation() throws AppBaseException{
       formToEdit.setStatus(CANCELED);
       return editForm();
   }
   public String setAsRealized() throws AppBaseException{
       formToEdit.setStatus(Status.REALIZED);
       return editForm();
   }
   public boolean checkIfCanBeRealized(){
       boolean canBeRealized = false;
//       String userName = ContextUtils.getUserName();
       Account account = new Account();//accountSession.findMyAccount();
       account.setLogin("zkura");
       if ((account instanceof Driver) && 
               (formToEdit.getStatus() == RESERVED) &&
               (formToEdit.getAccessibilityId().getReturnDate().before(new Date()))){
           canBeRealized = true;
       }
       return canBeRealized;
   }
   public boolean checkIfDisabled(){
       return (formToEdit.getStatus() == ACCEPTED ||
               formToEdit.getStatus() == SEND ||
               formToEdit.getStatus() == RESERVED ||
               formToEdit.getStatus() == REALIZED ||
               formToEdit.getStatus() == CANCELED);
   }
   public void bookCar() throws IOException{
       FacesContext.getCurrentInstance().getExternalContext().redirect("../admin/car/bookCar.xhtml?id=" +formToEdit.getId());
   }
   public void bookSeat() throws IOException{
       FacesContext.getCurrentInstance().getExternalContext().redirect("../admin/car/bookSeat.xhtml?id=" +formToEdit.getId());
   }
}
