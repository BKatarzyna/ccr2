/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import endpoint.CarEndpoint;
import endpoint.EmployeeEndpoint;
import endpoint.FormEndpoint;
import exception.AppBaseException;
import facade.AccessibilityFacade;
import facade.EmployeeFacade;
import facade.FormFacade;
import facade.ManagerFacade;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import model.Accessibility;
import model.Car;
import model.Employee;
import model.Form;
import model.Manager;
import model.Status;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import web.car.BookSeatPageBean;

@RunWith(Arquillian.class)
public class BookSeatTest {

    @Deployment
    public static JavaArchive createTestArchive() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "test.jar")
                .addPackage(Form.class.getPackage())
                .addPackage(FormFacade.class.getPackage())
                .addPackage(FormEndpoint.class.getPackage())
                .addClass(BookSeatPageBean.class)
                .addAsManifestResource(new FileAsset(new File("src/test/resources-glassfish-embedded/test-persistence.xml")), "persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        //archive.as(ZipExporter.class).exportTo(new File(archive.getName()), true);
        return archive;
    }

    public BookSeatTest() {
    }

    @Inject
    CarEndpoint carEndpoint;
    @Inject
    EmployeeFacade employeeFacade;
    @Inject
    EmployeeEndpoint employeeEndpoint;
    @Inject
    AccessibilityFacade accessibilityFacade;
    @Inject
    FormFacade formFacade;
    @Inject
    FormEndpoint formEndpoint;
    @Inject
    ManagerFacade managerFacade;

    @Test
    @InSequence(1)
    public void createTest() {
        Manager manager = new Manager("Kadry", "Manager", "manager1", "manager1", "Antonii", "Bakalala", "abakalala@mail.com");
        Car car = new Car("Opel", "wa24680", 4);
        Car car2 = new Car("Opel", "wZ24680", 4);
        Employee employee1 = new Employee("Employee", "employee1", "employee1", "Alicja", "Pakuła", "apakula@mail.com", "pracownik", manager);
        Employee employee2 = new Employee("Employee", "employee2", "employee2", "Jerzy", "Pakuła", "jpakula@mail.com", "pracownik", manager);
        Accessibility accessibility1 = new Accessibility("Warszawa", "Kraków", 3, new Date(2016, 12, 2), new Date(2016, 12, 2, 8, 10), new Date(2016, 12, 12), new Date(2016, 12, 12, 18, 20));
        Accessibility accessibility2 = new Accessibility("Warszawa", "Kraków", 2, new Date(2016, 12, 2), new Date(2016, 12, 2, 8, 10), new Date(2016, 12, 12), new Date(2016, 12, 12, 18, 20));
        Accessibility accessibility3 = new Accessibility("Warszawa", "Kraków", 2, new Date(2016, 12, 4), new Date(2016, 12, 4, 8, 10), new Date(2016, 12, 12), new Date(2016, 12, 12, 18, 20));
        Form form1 = new Form(1, Status.ACCEPTED, employee1, accessibility1);
        Form form2 = new Form(1, Status.ACCEPTED, employee2, accessibility2);
        Form form3 = new Form(1, Status.ACCEPTED, employee2, accessibility3);
        try {
            managerFacade.create(manager);
            employeeFacade.create(employee1);
            employeeFacade.create(employee2);
            formEndpoint.createForm(form1);
            formEndpoint.createForm(form2);
            formEndpoint.createForm(form3);
            car.getAccessibilityIds().add(accessibilityFacade.find(accessibility1.getId()));
            carEndpoint.createCar(car);
            car2.getAccessibilityIds().add(accessibilityFacade.find(accessibility3.getId()));
            carEndpoint.createCar(car2);
        } catch (AppBaseException ex) {
            Logger.getLogger(BookSeatTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    @InSequence(2)
    public void injectionTest() {
        assertNotNull(carEndpoint);
        assertNotNull(employeeFacade);
        assertNotNull(employeeEndpoint);
        assertNotNull(accessibilityFacade);
        assertNotNull(formFacade);
        assertNotNull(formEndpoint);
    }

    @Test
    @InSequence(3)
    public void testFind() {
        assertNotNull(carEndpoint.findAllCars());
        assertNotNull(employeeEndpoint.findAllEmployees());
        assertNotNull(formFacade.findAll());
    }

    @Test
    @InSequence(4)
    public void checkSeatAvailabilityTest() {
        List<Car> allCars = carEndpoint.findAllCars();
        List<Car> filteredCars = new ArrayList<>();
        List<Accessibility> accessibilityToEdit = new ArrayList<>();
        List<Form> form = formFacade.findAll();
        boolean available;
        for (Car car : allCars) {
            available = false;
            for (Accessibility access : car.getAccessibilityIds()) {
                if (form.get(1).getAccessibilityId().getDepartureFrom().equals(access.getDepartureFrom())
                        && form.get(1).getAccessibilityId().getDepartureDate().equals(access.getDepartureDate())
                        && form.get(1).getAccessibilityId().getDestination().equals(access.getDestination())
                        && form.get(1).getAccessibilityId().getReturnDate().equals(access.getReturnDate())
                        && form.get(1).getNumberOfPeople() <= access.getFreeSeatsNumber()) {
                    available = true;
                    accessibilityToEdit.add(access);
                }
                if (available) {
                    filteredCars.add(car);
                }
            }
        }
        
        assertNotNull(filteredCars);
        assertEquals(allCars.size(), 2);
        assertEquals(filteredCars.size(), 1);
        assertEquals(accessibilityToEdit.size(), 1);
    }
}
