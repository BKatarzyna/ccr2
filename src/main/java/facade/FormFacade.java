/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import interceptor.LoggingInterceptor;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import model.Form;

/**
 *
 * @author Lenovo
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
public class FormFacade extends AbstractFacade<Form> {

    @PersistenceContext(unitName = "ccr2_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FormFacade() {
        super(Form.class);
    }
    
    public List<Form> findByEmployeeCreated(String login) {
        TypedQuery<Form> tq = em.createNamedQuery("Form.findByEmployeeCreated", Form.class);
        tq.setParameter("login", login);
        return tq.getResultList();
    }

    public List<Form> findByManagerAccepted(String login) {
        TypedQuery<Form> tq = em.createNamedQuery("Form.findByManagerAccepted", Form.class);
        tq.setParameter("login", login);
        return tq.getResultList();
    }
    public List<Form> findByDriver(String login) {
        TypedQuery<Form> tq = em.createNamedQuery("Form.findByDriver", Form.class);
        tq.setParameter("login", login);
        return tq.getResultList();
    }
}
