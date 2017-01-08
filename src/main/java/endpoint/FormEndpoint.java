/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoint;

import exception.AppBaseException;
import facade.FormFacade;
import interceptor.LoggingInterceptor;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import model.Form;

/**
 *
 * @author Lenovo
 */
@Stateful
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class FormEndpoint extends AbstractEndpoint implements SessionSynchronization {
    
    @Inject
    private FormFacade formFacade;
    
    private Form formToEdit;

    public FormEndpoint() {
    }
    
    
    public List<Form> findEmployeeForms(String login){
        return formFacade.findByEmployeeCreated(login);
    }

    public void createForm(Form newForm) throws AppBaseException{
        try {
            formFacade.create(newForm);
        } catch (AppBaseException ex) {
            Logger.getLogger(FormEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    public void editForm(Form formToEdit) throws AppBaseException{
        try {
            formFacade.edit(formToEdit);
        } catch (AppBaseException ex) {
            Logger.getLogger(FormEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    public void deleteForm(Form formToEdit) throws AppBaseException{
        try {
            formFacade.remove(formToEdit);
        } catch (AppBaseException ex) {
            Logger.getLogger(FormEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public List<Form> findManagerForms(String login) {
        return formFacade.findByManagerAccepted(login);
    }
    public List<Form> findDriverForms(String login) {
        return formFacade.findByDriver(login);
    }

    public Form findForm(long formId) {
        return formFacade.find(formId);
    }
}
