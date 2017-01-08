/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import interceptor.LoggingInterceptor;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import model.Location;

/**
 *
 * @author Lenovo
 */
@Stateless
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class LocationFacade extends AbstractFacade<Location> {

    @PersistenceContext(unitName = "ccr2_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LocationFacade() {
        super(Location.class);
    }
    public Location findLocationByDescription(String shortName) {
        TypedQuery<Location> tq = em.createNamedQuery("Location.findByLocationShortName", Location.class);
        tq.setParameter("shortname", shortName);
        return tq.getSingleResult();
    }
}
