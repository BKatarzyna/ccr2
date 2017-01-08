/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.form;

import endpoint.AccessibilityEndpoint;
import endpoint.AccountEndpoint;
import endpoint.FormEndpoint;
import exception.AppBaseException;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Accessibility;
import model.Account;
import model.Employee;
import model.Form;
import model.Status;
import web.utils.ContextUtils;

/**
 *
 * @author Lenovo
 */
@Named("formSession")
@SessionScoped
public class FormSession implements Serializable {
   
    private static final Logger LOG = Logger.getLogger(FormSession.class.getName());
    private static final long serialVersionUID = 1L;
    
    //po dodaniu logowania będzie zmienione na AccountSession
    @Inject
    private AccountEndpoint accountEndpoint;
    @Inject
    private FormEndpoint formEndpoint;
    
    private Form formToEdit;
    private List<Form> allForms;
    
    private Account account =  new Account();
    
     
    @Inject
    private AccessibilityEndpoint accessibilityEndpoint;
    
    
    @PostConstruct
    private void init() {
        account = accountEndpoint.findMyAccount(); 
        LOG.severe("Session started: " + ContextUtils.getSessionID());
    }

    public Account getAccount() {
        return account;
    }
    public Form getFormToEdit() {
        return formToEdit;
    }

    public void setFormToEdit(Form formToEdit) {
        this.formToEdit = formToEdit;
    }

    public FormSession() {
    }
    
    
    
    public String createNewForm(Form newForm, Accessibility newAccessibility) throws AppBaseException{ 
        newForm.setStatus(Status.CREATED);
        createForm(newForm, newAccessibility);
        //zmienić wniosek utworzony
        return "success";
    }
    
    public void createForm(Form newForm, Accessibility newAccessibility) throws AppBaseException {
        //accessibilityEndpoint.create(newAccessibility);
        newForm.setAccessibilityId(newAccessibility);        
        newForm.setEmployeeCreatedId((Employee)account);
        formEndpoint.createForm(newForm);
    }
    public String cancel(){
        //zmienić na stronę główną
        return "success";
    }
    
    public String sendForm(Form newForm, Accessibility newAccessibility) throws AppBaseException{
        newForm.setStatus(Status.SEND);
        setFormManager(newForm);
        createForm(newForm, newAccessibility);
        //dodać managera, do którego wysyłane
        //zmienić wniosek wysłany
        return "success";
    }
    
    public String editForm(Form formToEdit) throws AppBaseException{
        accessibilityEndpoint.edit(formToEdit.getAccessibilityId());
        formEndpoint.editForm(formToEdit);
        return "success";
    }
    public void setFormManager(Form newForm){
        newForm.setManagerAcceptedId(((Employee)account).getManager());
    }
       
    public String getMyLogin() {
        return ContextUtils.getUserName();
    }

    public String deleteForm(Form formToEdit) throws AppBaseException {
        formEndpoint.deleteForm(formToEdit);
        return "success";
    }
    
    public List<Form> findManagerForms() {
        return formEndpoint.findManagerForms(getMyLogin());
    }
    public List<Form> findDriverForms() {
        return formEndpoint.findDriverForms(getMyLogin());
    }
    public List<Form> findEmployeeForms(){
        return formEndpoint.findEmployeeForms(getMyLogin());
    }
}
