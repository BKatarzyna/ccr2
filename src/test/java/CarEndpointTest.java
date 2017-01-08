/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import endpoint.CarEndpoint;
import facade.CarFacade;
import java.io.File;
import java.util.List;
import javax.inject.Inject;
import model.Car;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CarEndpointTest {
    
    @Deployment
    public static JavaArchive createTestArchive() {
        JavaArchive archive =  ShrinkWrap.create(JavaArchive.class, "test.jar")
                .addPackage(Car.class.getPackage())
                .addPackage(CarFacade.class.getPackage())
                .addPackage(CarEndpoint.class.getPackage())
                .addAsManifestResource(new FileAsset(new File("src/test/resources-glassfish-embedded/test-persistence.xml")), "persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        archive.as(ZipExporter.class).exportTo(new File(archive.getName()), true);
        return archive;
    }
    
    @Inject
    CarEndpoint carEndpoint;
    @Inject
    CarFacade carFacade;
    
    public CarEndpointTest() {
    }
   
    @Test
    @InSequence(1)
    public void testCreateCar() throws Exception {
        System.out.println("---- testCreateCar");
        Car car1 = new Car("Mercedes", "WA23456", 5);
        Car car2 = new Car("Opel", "KK09876", 3);
        
        carEndpoint.createCar(car1);
        carEndpoint.createCar(car2);
    }
    @Test
    @InSequence(2)
    public void testFind() throws Exception {
        System.out.println("---- testFind");
        System.out.println(carEndpoint.findCar(1L).toString());
        
    }
    @Test
    @InSequence(3)
    public void testFindCarByRegistrationNumber() throws Exception {
        System.out.println("----testFindCarByRegistrationNumber");
        Car car = carEndpoint.findCarByRegistrationNumber("KK09876");
        assertNotNull(car);
        assertEquals("KK09876", car.getRegistrationNumber());
    }
    
    @Test
    @InSequence(4)
    public void testEditCar() throws Exception {
        System.out.println("----testEditCar");
        Car car = carEndpoint.findCar(1L);
        car.setModel("Skoda");
        carEndpoint.editCar(car);
        Car car2 = carEndpoint.findCar(1L);
        assertEquals("Skoda", car2.getModel());
    }    
    
    @Test
    @InSequence(5)
    public void testFindAllCars() throws Exception {
        System.out.println("----testFindAllCars");
        List<Car> cars = carEndpoint.findAllCars();
        assertEquals(2, cars.size());
    }
}
