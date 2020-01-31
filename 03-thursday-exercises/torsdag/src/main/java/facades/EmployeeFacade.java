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
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author Bruger
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

    public Employee getEmployeeByID(Long id) {
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
        } finally {
            em.close();
        }
    }

    public List<Employee> getAllEmployees() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery tq = em.createQuery("SELECT e FROM Employee e", Employee.class);
            return tq.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Employee> getEmployeesWithHighestSalary() {
        EntityManager em = getEntityManager();
        try {
            Double salary = (Double) em.createQuery("SELECT MAX(e.salary) FROM Employee e", Double.class).getSingleResult();
            TypedQuery tq = em.createQuery("SELECT e FROM Employee e WHERE e.salary = :salary", Employee.class);
            tq.setParameter("salary", salary);
            return tq.getResultList();
        } finally {
            em.close();
        }
    }

    public Employee createEmployee(String name, String address, double salary) {
        Employee employee = new Employee(name, address, salary);
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(employee);
            em.getTransaction().commit();
            return employee;
        } finally {
            em.close();
        }
    }
}
