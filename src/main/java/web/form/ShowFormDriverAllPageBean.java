/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Form;
import model.Status;

/**
 *
 * @author Lenovo
 */
@Named("showFormDriverAllPageBean")
@RequestScoped
public class ShowFormDriverAllPageBean  {
    
    @Inject
    private FormSession formSession;
    
    private List<Form> allForms = new ArrayList<>();
    private List<Form> filteredForms = new ArrayList<>();
    private Form formToEdit;
    
    @PostConstruct
    public void init(){
        allForms = formSession.findDriverForms();
        //filteredForms = allForms;
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

   public List getStatusValues(){
       return new ArrayList<>(Arrays.asList(Status.values()));
   }

}
