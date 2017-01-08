/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.form;

import endpoint.FormEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import model.Form;
import model.Status;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Lenovo
 */
@Named("showFormManagerPageBean")
@RequestScoped
public class ShowFormManagerPageBean  {
   
    @Inject
    private FormSession formSession;
    
    private List<Form> allForms;
    private List<Form> filteredForms;
    private Form formToEdit;
    
    @PostConstruct
    public void init(){
        allForms = formSession.findManagerForms();
        filteredForms = allForms;
    }

    public List<Form> getAllForms() {
        return allForms;
    }

    public Form getFormToEdit() {
        return formToEdit;
    }

    public void setFormToEdit(Form formToEdit) {
        this.formToEdit = formToEdit;
    }

    public List<Form> getFilteredForms() {
        return filteredForms;
    }

    public void setFilteredForms(List<Form> filteredForms) {
        this.filteredForms = filteredForms;
    }
    
   public void onRowSelect(SelectEvent event) throws IOException {
       formSession.setFormToEdit((Form) event.getObject());       
       //przekierowanie do strony z parametrem
       FacesContext.getCurrentInstance().getExternalContext().redirect("acceptForm.xhtml?id=" +formToEdit.getId());
    }
   public List getStatusValues(){
       return new ArrayList<>(Arrays.asList(Status.values()));
   }
    
}
