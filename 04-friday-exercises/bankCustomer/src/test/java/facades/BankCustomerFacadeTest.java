/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.CustomerDTO;
import entities.BankCustomer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Bruger
 */
public class BankCustomerFacadeTest {

    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    private final static BankCustomerFacade bcf = BankCustomerFacade.getFacade(emf);

    private static BankCustomer c1;
    private static BankCustomer c2;
    private static BankCustomer c3;
    private static BankCustomer c4;

    @Before
    public void setUp() {
        cleanUp();
    }

    @AfterClass
    public static void tearDown() {
        cleanUp();
    }

    private void setupEntities() {
        c1 = new BankCustomer("Jack", "Peterson", "a0000be1", 500, 7, "Nice person");
        c2 = new BankCustomer("Peter", "Jackson", "a0000be2", 5000, 1, "Nice person");
        c3 = new BankCustomer("Alfred", "Amon", "a0000be3", 1500, 2, "Bad person");
        c4 = new BankCustomer("Maria", "Amon", "a0000be4", 1600, 8, "Nice person");
        EntityManager em = bcf.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(c1);
            em.persist(c2);
            em.persist(c3);
            em.persist(c4);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    private static void cleanUp() {
        c1 = null;
        c2 = null;
        c3 = null;
        c4 = null;
        try {
            EntityManager em = bcf.getEntityManager();
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.createQuery("DELETE FROM BankCustomer").executeUpdate();
            t.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testGetCustomerByID() {
        setupEntities();
        BankCustomer expected = c1;

        CustomerDTO result = bcf.getCustomerByID(expected.getId());

        assertEquals(expected.getId(), result.getCustomerID(), 0);
        assertEquals(expected.getAccountNumber(), result.getAccountNumber());
        assertEquals(expected.getBalance(), result.getBalance(), 0);
        assertTrue(result.getFullName().contains(expected.getFirstName()));
        assertTrue(result.getFullName().contains(expected.getLastName()));
    }

    @Test
    public void testGetCustomerByIDNoResult() {
        setupEntities();

        CustomerDTO result = bcf.getCustomerByID(-1);

        assertNull(result);
    }

    @Test
    public void testGetCustomerByName() {
        setupEntities();
        BankCustomer expected = c2;

        int expectedSize = 1;

        List<CustomerDTO> result = bcf.getCustomerByName(expected.getLastName());

        assertEquals(expectedSize, result.size());
        assertEquals(expected.getId(), result.get(0).getCustomerID());
    }

    @Test
    public void testGetCustomerByNameMultipleResults() {
        setupEntities();
        BankCustomer expected1 = c3;
        BankCustomer expected2 = c4;
        List<BankCustomer> expectedList = new ArrayList(Arrays.asList(new BankCustomer[]{
            expected1, expected2
        }));
        if (!expected1.getLastName().equals(expected2.getLastName())) {
            fail("Expected values did not have the same last name");
        }

        List<CustomerDTO> result = bcf.getCustomerByName(expected1.getLastName());

        assertEquals(expectedList.size(), result.size());
        for (CustomerDTO resultCustomer : result) {
            boolean matchFound = false;
            for (BankCustomer expectedCustomer : expectedList) {
                if (expectedCustomer.getId() == resultCustomer.getCustomerID()) {
                    matchFound = true;
                    break;
                }
            }
            assertTrue(matchFound);
        }
    }

    @Test
    public void testGetCustomerByNameNoResult() {
        setupEntities();
        String invalidName = "123456789";

        List<CustomerDTO> result = bcf.getCustomerByName(invalidName);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllBankCustomers() {
        setupEntities();
        List<BankCustomer> result = bcf.getAllBankCustomers();
        
        int expectedSize = 4;
        
        assertEquals(expectedSize, result.size());
        assertTrue(result.contains(c1));
        assertTrue(result.contains(c2));
        assertTrue(result.contains(c3));
        assertTrue(result.contains(c4));
    }
    
    @Test
    public void testGetAllBankCustomersEmpty() {
        List<BankCustomer> result = bcf.getAllBankCustomers();
        
        int expectedSize = 0;
        
        assertEquals(expectedSize, result.size());
        assertFalse(result.contains(c1));
        assertFalse(result.contains(c2));
        assertFalse(result.contains(c3));
        assertFalse(result.contains(c4));
    }
    
    @Test
    public void testAddCustomer() {
        BankCustomer newEntity = new BankCustomer("Mr", "Man", "My account", 50, 10, "Best new guy");
        BankCustomer result = bcf.addCustomer(newEntity);
        
        assertEquals(newEntity, result);
        
        List<BankCustomer> dbList = bcf.getAllBankCustomers();
        
        assertTrue(dbList.contains(result));
    }
}
