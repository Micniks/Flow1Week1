/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Employee;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Bruger
 */
public class EmployeeFacadeTest {
    
    private static final EntityManagerFactory ENF = Persistence.createEntityManagerFactory("pu");
    private static final EmployeeFacade EF = EmployeeFacade.getFacadeExample(ENF);
    private static Employee e1;
    private static Employee e2;
    private static Employee e3;
    private static String e1Name = "Godfrey";
    private static String e2Name = "Jackson";
    private static String e3Name = "Peterson";
    private static Double highestSalary = 10000.0;
    
    private static void cleanUp() {
        e1 = null;
        e2 = null;
        e3 = null;
        try {
            EntityManager em = ENF.createEntityManager();
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.createQuery("DELETE FROM Employee").executeUpdate();
            t.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
        cleanUp();
    }

    @Before
    public void setUp() {
        cleanUp();
    }
    
    public void setupDummyObjects(){
        e1 = EF.createEmployee(e1Name, "Home", highestSalary);
        e2 = EF.createEmployee(e2Name, "Work", 1500);
        e3 = EF.createEmployee(e3Name, "Parent's Home", 5);
    }
    
    @Test
    public void createEmployee() {
        Employee result = EF.createEmployee("Godfrey", "Home", 4500);
        
        assertNotNull(result);
        assertTrue(result.getId() > 0);
    }
    
    @Test
    public void getEmployeeById() {
        setupDummyObjects();
        
        Employee result = EF.getEmployeeByID(e2.getId());
        
        assertNotNull(result);
        assertNotEquals(e1, result);
        assertEquals(e2, result);
        assertNotEquals(e3, result);
    }
    
    @Test
    public void getEmployeeByIdNoResult() {
        setupDummyObjects();
        long searchID = -1;
        
        Employee result = EF.getEmployeeByID(searchID);
        
        assertNull(result);
    }
    
    @Test
    public void getEmployeesByNameTwoResults() {
        String name = e1Name;
        
        setupDummyObjects();
        Employee e4 = EF.createEmployee(name, name, -50);
        
        List<Employee> result = EF.getEmployeesByName(name);
        
        assertFalse(result.isEmpty());
        assertTrue(result.contains(e1));
        assertFalse(result.contains(e2));
        assertFalse(result.contains(e3));
        assertTrue(result.contains(e4));
    }
    
    @Test
    public void getEmployeesByNameOneResult() {
        String name = e1Name;
        setupDummyObjects();
        
        List<Employee> result = EF.getEmployeesByName(name);
        
        assertFalse(result.isEmpty());
        assertTrue(result.contains(e1));
        assertFalse(result.contains(e2));
        assertFalse(result.contains(e3));
    }
    
    @Test
    public void getEmployeesByNameNoResult() {
        String name = "Stone";
        setupDummyObjects();
        
        List<Employee> result = EF.getEmployeesByName(name);
        
        assertTrue(result.isEmpty());
        assertFalse(result.contains(e1));
        assertFalse(result.contains(e2));
        assertFalse(result.contains(e3));
    }
    
    @Test
    public void getAllEmployeesNotEmpty() {
        setupDummyObjects();
        
        List<Employee> result = EF.getAllEmployees();
        
        assertFalse(result.isEmpty());
        assertTrue(result.contains(e1));
        assertTrue(result.contains(e2));
        assertTrue(result.contains(e3));
    }
    
    @Test
    public void getAllEmployeesEmpty() {
        List<Employee> result = EF.getAllEmployees();
        
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void getEmployeesWithHighestSalary() {
        setupDummyObjects();
        
        List<Employee> result = EF.getEmployeesWithHighestSalary();
        
        int expectedResultSize = 1;
        assertEquals(expectedResultSize, result.size());
        assertTrue(result.contains(e1));
        assertFalse(result.contains(e2));
        assertFalse(result.contains(e3));
    }
    
    @Test
    public void getEmployeesWithHighestSalaryTwoEqual() {
        setupDummyObjects();
        Employee extra = EF.createEmployee("Someone", "Somewhere", highestSalary);
        
        List<Employee> result = EF.getEmployeesWithHighestSalary();
        
        int expectedResultSize = 2;
        assertEquals(expectedResultSize, result.size());
        assertTrue(result.contains(extra));
        assertTrue(result.contains(e1));
        assertFalse(result.contains(e2));
        assertFalse(result.contains(e3));
    }
    
    @Test
    public void getEmployeesWithHighestSalaryNoResult() {
        List<Employee> result = EF.getEmployeesWithHighestSalary();
        
        assertTrue(result.isEmpty());
    }
}
