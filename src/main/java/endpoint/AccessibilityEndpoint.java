/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoint;

import exception.AppBaseException;
import facade.AccessibilityFacade;
import interceptor.LoggingInterceptor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import model.Accessibility;

/**
 *
 * @author Lenovo
 */
@Stateful
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AccessibilityEndpoint extends AbstractEndpoint implements SessionSynchronization{

    @Inject
    private AccessibilityFacade accessibilityFacade;
    
    public void edit(Accessibility accessibilityId) throws AppBaseException {
        try {
            accessibilityFacade.edit(accessibilityId);
        } catch (AppBaseException ex) {
            Logger.getLogger(AccessibilityEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public void create(Accessibility newAccessibility) throws AppBaseException  {
        try {
            accessibilityFacade.create(newAccessibility);
        } catch (AppBaseException ex) {
            Logger.getLogger(AccessibilityEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    
}
