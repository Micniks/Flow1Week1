/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Bruger
 */
public class CustomerFacade {

    /*
    This class was the EntityTested class, but was refactored with the assignment.
     */
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");

    protected EntityManager getManager() {
        return emf.createEntityManager();
    }

    public List<Customer> getAllCustomers() {
        EntityManager em = getManager();
        try {
            TypedQuery tq = em.createQuery("SELECT p FROM Customer p", Customer.class);
            return tq.getResultList();
        } finally {
            em.close();
        }
    }

    public Customer getCustomerOnID(long id) {
        EntityManager em = getManager();
        try {
            TypedQuery tq = em.createQuery("SELECT c FROM Customer c WHERE c.id = :id", Customer.class);
            tq.setParameter("id", id);
            Customer result = (Customer) tq.getSingleResult();
            return result;
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Customer> getCustomersOnLastName(String lastName) {
        EntityManager em = getManager();
        try {
            TypedQuery tq = em.createQuery("SELECT c FROM Customer c WHERE c.lastName = :lastName", Customer.class);
            tq.setParameter("lastName", lastName);
            return tq.getResultList();
        } finally {
            em.close();
        }
    }

    public int getNumberOfCustomers() {
        return getAllCustomers().size();
    }

    public Customer addCustomer(String firstName, String lastName) {
        EntityManager em = getManager();
        try {
            Customer newCustomer = new Customer(firstName, lastName);
            em.getTransaction().begin();
            em.persist(newCustomer);
            em.getTransaction().commit();
            return newCustomer;
        } finally {
            em.close();
        }
    }

//    public static void main(String[] args) {
//        CustomerFacade cf = new CustomerFacade();
//
//        cf.addCustomer("Jeff", "Dunhamm");
//        cf.addCustomer("Michael", "Jackson");
//
//        cf.addCustomer("Rock", "The");
//
//        cf.getAllCustomers().forEach(customer -> {
//            System.out.println(customer + ", Created by:  " + customer.getCreated());
//        });
//
//        System.out.println("The one with ID = 1 is ----->     " + cf.getCustomerOnID(1));
//        System.out.println("The ones with lastName = 'The' is ----->     " + cf.getCustomerOnLastName("The"));
//        System.out.println("The number of customers is ----->     " + cf.getNumberOfCustomers());
//    }
}
