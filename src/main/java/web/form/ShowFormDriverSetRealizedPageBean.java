/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.form;

import exception.AppBaseException;
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
@Named("showFormDriverSetRealizedPageBean")
@RequestScoped
public class ShowFormDriverSetRealizedPageBean  {
    
    @Inject
    private FormSession formSession;
    
    private List<Form> allForms = new ArrayList<>();
    private List<Form> filteredForms = new ArrayList<>();
    private Form formToEdit;
    
    @PostConstruct
    public void init(){
        filteredForms = formSession.findDriverForms();
        for (Form form : filteredForms) {
            if(form.getStatus() == Status.RESERVED){
                allForms.add(form);
            }
        }
        System.out.println(filteredForms);
        System.out.println(allForms);
        //filteredForms = null;
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
    
//   public void onRowSelect(SelectEvent event) throws IOException {
//       formSession.setFormToEdit((Form) event.getObject());       
//       //przekierowanie do strony z parametrem
//       FacesContext.getCurrentInstance().getExternalContext().redirect("acceptForm.xhtml?id=" +formToEdit.getId());
//    }
   public List getStatusValues(){
       return new ArrayList<>(Arrays.asList(Status.values()));
   }
    
   public String confirmAsRealized() throws AppBaseException{
       for (Form form : filteredForms) {
           form.setStatus(Status.REALIZED);
           formSession.editForm(form);
       }
       return "success";
   }
}
