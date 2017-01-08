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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import model.Account;
import model.Account_;
import model.Employee;
import model.Manager;

/**
 *
 * @author Lenovo
 */
@Stateless
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class ManagerFacade extends AbstractFacade<Manager> {

    @PersistenceContext(unitName = "ccr2_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ManagerFacade() {
        super(Manager.class);
    }

    public List<Employee> findEmployees() {
        TypedQuery<Employee> tq = em.createNamedQuery("Employee.findAll", Employee.class);
        return tq.getResultList();
    }
    
    public void addEmployees(List<Employee> managerEmployees) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
//    public Manager findManagerByName(String name) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Manager> query = cb.createQuery(Manager.class);
//        Root<Manager> from = query.from(Manager.class);
//        query = query.select(from);
//        Predicate criteria = cb.conjunction();
//        criteria = cb.and(restrictions)
//        query = query.where(cb.equal(from.get(Manager_.login), login));
//        TypedQuery<Manager> tq = em.createQuery(query);
//
//        return tq.getSingleResult();
//    }
}
