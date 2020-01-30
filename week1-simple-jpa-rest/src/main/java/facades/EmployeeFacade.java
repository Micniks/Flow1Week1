package facades;

import entities.Employee;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class EmployeeFacade {

    private static EmployeeFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private EmployeeFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static EmployeeFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new EmployeeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Employee getEmployeeByID(int id) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery tq = em.createQuery("SELECT e FROM Employee e WHERE e.id = :id", Employee.class);
            tq.setParameter("id", id);
            Employee employee = (Employee) tq.getSingleResult();
            return employee;
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Employee> getEmployeesByName(String name) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery tq = em.createQuery("SELECT e FROM Employee e WHERE e.name = :name", Employee.class);
            tq.setParameter("name", name);
            return tq.getResultList();
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Employee> getAllEmployees() {
        return null;
    }

    public Employee getEmployeesWithHighestSalary() {
        return null;
    }

    public Employee createEmployee(String name, String address, double salary) {
        return null;
    }

}
