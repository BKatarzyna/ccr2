/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoint;

import exception.AppBaseException;
import facade.CarImageFacade;
import interceptor.LoggingInterceptor;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import model.CarImage;

/**
 *
 * @author Lenovo
 */
@Stateful
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CarImageEndpoint extends AbstractEndpoint implements SessionSynchronization {

    @Inject
    private CarImageFacade carImageFacade;

    public void createImage(CarImage carImage) throws AppBaseException {
        try {
            carImageFacade.create(carImage);
        } catch (AppBaseException ex) {
            Logger.getLogger(CarImageEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public void editImage(CarImage carImage) throws AppBaseException {
        try {
            carImageFacade.edit(carImage);
        } catch (AppBaseException ex) {
            Logger.getLogger(CarImageEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public void deleteImage(CarImage carImage) throws AppBaseException {
        try {
            carImageFacade.remove(carImage);
        } catch (AppBaseException ex) {
            Logger.getLogger(CarImageEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }


    public byte[] findImage() {
        ArrayList<CarImage> carImageList = (ArrayList<CarImage>) carImageFacade.findAll();
              
            CarImage newCarImage = carImageList.get(0);
            return newCarImage.getImage();
        
    }

}
