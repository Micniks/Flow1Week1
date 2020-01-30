/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.jupiter.api.AfterAll;

/**
 *
 * @author Bruger
 */
public class CustomerFacadeTest {

    private final static CustomerFacade cf = new CustomerFacade();

    private static void cleanUp() {
        try {
            EntityManager em = cf.getManager();
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.createQuery("DELETE FROM Customer").executeUpdate();
            t.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // DOES NOT WORK!
//    @BeforeEach
//    public void setUp() {
//        cleanUp();
//    }
    
    @AfterAll
    public static void tearDown() {
        cleanUp();
    }

    @Test
    public void testGetAllCustomersEmpty() {
        cleanUp();
        List result = cf.getAllCustomers();
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllCustomersNotEmpty() {
        cleanUp();
        Customer c1 = cf.addCustomer("Mr", "Smith");
        Customer c2 = cf.addCustomer("Miss", "Smith");
        Customer c3 = cf.addCustomer("Mr", "John");
        Customer c4 = cf.addCustomer("Miss", "John");
        Customer c5 = cf.addCustomer("Jeff", "Rock");
        List result = cf.getAllCustomers();

        assertFalse(result.isEmpty());
        assertTrue(result.contains(c1));
        assertTrue(result.contains(c2));
        assertTrue(result.contains(c3));
        assertTrue(result.contains(c4));
        assertTrue(result.contains(c5));

    }

    @Test
    public void testGetCustomerOnID() {
        cleanUp();
        Customer c1 = cf.addCustomer("Mr", "Smith");
        Customer c2 = cf.addCustomer("Miss", "Smith");
        long id = c1.getId();

        Customer result = cf.getCustomerOnID(id);

        assertEquals(c1, result);
    }

    @Test
    public void testGetCustomerOnIDNotFound() {
        cleanUp();
        Customer c1 = cf.addCustomer("Mr", "Smith");
        Customer c2 = cf.addCustomer("Miss", "Smith");
        long id = -1;

        Customer result = cf.getCustomerOnID(id);

        assertNull(result);
    }

    @Test
    public void testGetCustomersOnLastName() {
        cleanUp();
        Customer c1 = cf.addCustomer("Mr", "Smith");
        Customer c2 = cf.addCustomer("Miss", "Smith");
        Customer c3 = cf.addCustomer("Mr", "John");
        String lastName = "Smith";

        List<Customer> result = cf.getCustomersOnLastName(lastName);

        assertTrue(result.contains(c1));
        assertTrue(result.contains(c2));
        assertFalse(result.contains(c3));
    }

    @Test
    public void testGetCustomersOnLastNameSingleResult() {
        cleanUp();
        Customer c1 = cf.addCustomer("Mr", "Smith");
        Customer c2 = cf.addCustomer("Miss", "Smith");
        Customer c3 = cf.addCustomer("Mr", "John");
        String lastName = "John";

        List<Customer> result = cf.getCustomersOnLastName(lastName);

        assertFalse(result.contains(c1));
        assertFalse(result.contains(c2));
        assertTrue(result.contains(c3));
    }

    @Test
    public void testGetCustomersOnLastNameNoResult() {
        cleanUp();
        Customer c1 = cf.addCustomer("Mr", "Smith");
        Customer c2 = cf.addCustomer("Miss", "Smith");
        Customer c3 = cf.addCustomer("Mr", "John");
        String lastName = "Peterson";

        List<Customer> result = cf.getCustomersOnLastName(lastName);

        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testGetCustomersOnLastNameEmptyDB() {
        cleanUp();
        String lastName = "Peterson";

        List<Customer> result = cf.getCustomersOnLastName(lastName);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetNumberOfCustomersZero() {
        cleanUp();
        int result = cf.getNumberOfCustomers();
        int expectedResult = 0;
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetNumberOfCustomersOne() {
        cleanUp();
        cf.addCustomer("Mr", "Smith");

        int result = cf.getNumberOfCustomers();
        int expectedResult = 1;
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetNumberOfCustomersThree() {
        cleanUp();
        cf.addCustomer("Mr", "Smith");
        cf.addCustomer("Miss", "Smith");
        cf.addCustomer("Mr", "John");

        int result = cf.getNumberOfCustomers();
        int expectedResult = 3;
        assertEquals(expectedResult, result);
    }

    @Test
    public void testAddCustomer() {
        cleanUp();
        String firstName = "Mr";
        String lastName = "Smith";
        Customer result = cf.addCustomer(firstName, lastName);

        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
    }
}
