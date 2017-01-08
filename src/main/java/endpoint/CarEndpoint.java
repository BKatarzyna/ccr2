/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoint;

import exception.AppBaseException;
import facade.CarFacade;
import interceptor.LoggingInterceptor;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import model.Car;

/**
 *
 * @author Lenovo
 */
@Stateful
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CarEndpoint extends AbstractEndpoint implements SessionSynchronization{

    @Inject
    private CarFacade carFacade;
    
    public List findAllCars(){
        return carFacade.findAll();
    }
    public void createCar(Car newCar) throws AppBaseException{
        try {
            carFacade.create(newCar);
        } catch (AppBaseException ex) {
            Logger.getLogger(CarEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public Car findCar(long carId) {
        return carFacade.find(carId);
    }
    
    public void editCar(Car carToEdit) throws AppBaseException  {        
        try {
            carFacade.edit(carToEdit);
        } catch (AppBaseException ex) {
            Logger.getLogger(CarEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public void deleteCar(Car carToEdit) throws AppBaseException {
        try {
            carFacade.remove(carToEdit);
        } catch (AppBaseException ex) {
            Logger.getLogger(CarEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public Car findCarByRegistrationNumber(String registrationNumber) {
        return carFacade.findCarByRegistrationNumber(registrationNumber);
    }
    
}
