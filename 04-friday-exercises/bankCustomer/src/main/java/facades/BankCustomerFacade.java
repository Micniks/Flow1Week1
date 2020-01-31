package facades;

import dto.CustomerDTO;
import entities.BankCustomer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class BankCustomerFacade {

    private static BankCustomerFacade instance;
    private static EntityManagerFactory emf;

    public static BankCustomerFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BankCustomerFacade();
        }
        return instance;
    }

    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    private BankCustomerFacade() {
    }

    public CustomerDTO getCustomerByID(long id) {
        EntityManager em = instance.getEntityManager();
        try {
            TypedQuery tq = em.createQuery("SELECT b FROM BankCustomer b WHERE b.id = :id", BankCustomer.class);
            tq.setParameter("id", id);
            BankCustomer bankCustomer = (BankCustomer) tq.getSingleResult();
            CustomerDTO result = new CustomerDTO(bankCustomer);
            return result;
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
    }

    /*
    NOTE: Because of lack of information about this method, it will focus on lastname only
    */
    public List<CustomerDTO> getCustomerByName(String name) {
        EntityManager em = instance.getEntityManager();
        try {
            TypedQuery tq = em.createQuery("SELECT b FROM BankCustomer b WHERE b.lastName= :name", BankCustomer.class);
            tq.setParameter("name", name);
            List<BankCustomer> bankCustomers = tq.getResultList();
            List<CustomerDTO> result = CustomerDTO.convertCustomers(bankCustomers);
            return result;
        } finally {
            em.close();
        }
    }

    public BankCustomer addCustomer(BankCustomer cust) {
        EntityManager em = instance.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cust);
            em.getTransaction().commit();
            return cust;
        } finally {
            em.close();
        }
    }

    public List<BankCustomer> getAllBankCustomers() {
        EntityManager em = instance.getEntityManager();
        try {
            TypedQuery tq = em.createQuery("SELECT b FROM BankCustomer b", BankCustomer.class);
            List<BankCustomer> result = tq.getResultList();
            return result;
        } finally {
            em.close();
        }
    }

}
