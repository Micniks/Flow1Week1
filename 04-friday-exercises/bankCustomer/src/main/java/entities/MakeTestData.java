/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Bruger
 */
public class MakeTestData {

    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    private final static MakeTestData instance = new MakeTestData();

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        instance.cleanUp(em);
        List<BankCustomer> entities = instance.createEntities();
        instance.persistEntities(em, entities);
    }

    private void cleanUp(EntityManager em) {
        em.getTransaction().begin();
        em.createQuery("DELETE FROM BankCustomer").executeUpdate();
        em.getTransaction().commit();
    }

    private List<BankCustomer> createEntities() {
        List<BankCustomer> entities = new ArrayList(Arrays.asList(new BankCustomer[]{
            new BankCustomer("Jack", "Peterson", "a0000be1", 500, 7, "Nice person"),
            new BankCustomer("Peter", "Jackson", "a0000be2", 5000, 1, "Nice person"),
            new BankCustomer("Alfred", "Amon", "a0000be3", 1500, 2, "Nice person"),
            new BankCustomer("Maria", "Amon", "a0000be4", 1600, 8, "Nice person"),
            new BankCustomer("Jenny", "Amon", "a0000be5", -500, 6, "Nice person"),
            new BankCustomer("Andreas", "Group", "a0000be6", 0, 5, "Nice person"),
            new BankCustomer("Marcus", "Group", "a0000be7", 15000, 3, "Nice person"),
            new BankCustomer("Cahit", "Group", "a0000be8", 5, 4, "Nice person"),}));
        return entities;
    }

    private void persistEntities(EntityManager em, List<BankCustomer> entities) {
        em.getTransaction().begin();
        for (BankCustomer entity : entities) {
            em.persist(entity);
        }
        em.getTransaction().commit();
    }

}
