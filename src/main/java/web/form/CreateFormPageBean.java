/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.form;

import exception.AppBaseException;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Accessibility;
import model.Account;
import model.Form;
import org.primefaces.event.SelectEvent;
import web.AccountSession;

/**
 *
 * @author Lenovo
 */
@Named("createFormPageBean")
@RequestScoped
public class CreateFormPageBean {

    @ Inject
    private AccountSession accountSession;
    private Account account = new Account();
    private Date now;
    private Date later;
    private Date date;

    @Inject
    private FormSession formSession;

    private Accessibility newAccessibility = new Accessibility();
    private Form newForm = new Form();
    
    
    @PostConstruct
    private void init() {
        account = accountSession.findMyAccount();

        Calendar c = Calendar.getInstance();
        now = c.getTime();
        c.add(Calendar.YEAR, 2);
        later = c.getTime();
    }

    public CreateFormPageBean() {
    }

    
    public Account getAccount() {
        return account;
    }
    

    public void onDateSelect(SelectEvent selectEvent) {
        date = (Date) selectEvent.getObject();
    }

    public Accessibility getNewAccessibility() {
        return newAccessibility;
    }

    public Form getNewForm() {
        return newForm;
    }

    public String createNewForm() throws AppBaseException {
        return formSession.createNewForm(newForm, newAccessibility);
    }

    public String sendForm() throws AppBaseException {
        return formSession.sendForm(newForm, newAccessibility);
    }

    public String cancel() {
        return formSession.cancel();
    }

    public Date getNow() {
        return now;
    }

    public Date getLater() {
        return later;
    }

    public Date getDate() {
        return date;
    }

}
