/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
//import static org.junit.Assert.*;

/**
 *
 * @author thomas
 */
public class EmployeeFacadeTest {

    private static final EntityManagerFactory ENF = Persistence.createEntityManagerFactory("pu");
    private static final EmployeeFacade EF = EmployeeFacade.getFacadeExample(ENF);

    private static void cleanUp() {
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

    @AfterAll
    public static void tearDownClass() {
        cleanUp();
    }

    // DOES NOT WORK!
//    @BeforeEach
//    public void setUp() {
//        cleanUp();
//    }
    
    @Test
    public void createEmployee() {
        cleanUp();
//        Employee e = new Employee();
//        Employee result = EF.createEmployee("Godfrey", "Home", 4500);
    }

    @Test
    public void getEmployeeById() {
        cleanUp();
    }
    
    @Test
    public void getEmployeeByIdNoResult() {
        cleanUp();
    }
    
    @Test
    public void getEmployeesByNameTwoResults() {
        cleanUp();
    }
    
    @Test
    public void getEmployeesByNameOneResult() {
        cleanUp();
    }
    
    @Test
    public void getEmployeesByNameNoResult() {
        cleanUp();
    }
    
    @Test
    public void getAllEmployeesNotEmpty() {
        cleanUp();
    }
    
    @Test
    public void getAllEmployeesEmpty() {
        cleanUp();
    }
    
    @Test
    public void getEmployeesWithHighestSalary() {
        cleanUp();
    }
    
    @Test
    public void getEmployeesWithHighestSalaryTwoEqual() {
        cleanUp();
    }
    
    @Test
    public void getEmployeesWithHighestSalaryNoResult() {
        cleanUp();
    }
    
}
