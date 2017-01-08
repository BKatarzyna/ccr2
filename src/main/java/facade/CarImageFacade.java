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
import model.CarImage;
//import model.CarImage_;

/**
 *
 * @author Lenovo
 */
@Stateless
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class CarImageFacade extends AbstractFacade<CarImage> {

    @PersistenceContext(unitName = "ccr2_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CarImageFacade() {
        super(CarImage.class);
    }

    public CarImage findCarImage(Long carImageId) {
        TypedQuery<CarImage> tq = em.createNamedQuery("CarImage.findByCarImageId", CarImage.class);
        tq.setParameter("carimageid", carImageId);
        return tq.getSingleResult();
    }
}
