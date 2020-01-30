/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import entities.Customer;
import entities.CustomerFacade;
import java.util.List;
import java.util.Random;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Bruger
 */
@Path("customer")
public class CustomerResource {
    
    private static final Gson gson = new Gson();
    private static final CustomerFacade cf = new CustomerFacade();
    private static final Random random = new Random();
    
    /*
    Setup for Test
    */
    private static final boolean setup = setup();
    private static boolean setup(){
            cf.addCustomer("Jeff", "Peterson");
            cf.addCustomer("Mr", "Peterson");
            cf.addCustomer("Mr", "Jackson");
            cf.addCustomer("Miss", "Jackson");
            cf.addCustomer("Peter", "Pan");
            cf.addCustomer("Daniel", "Rat");
            cf.addCustomer("Rey", "Trash");
            cf.addCustomer("X", "Y");
            return true;
    }

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CustomerResource
     */
    public CustomerResource() {
    }

    /**
     * Retrieves representation of an instance of rest.CustomerResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        return "Hello";
    }
    
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllCustomers() {
        return gson.toJson(cf.getAllCustomers());
    }
    
    @GET
    @Path("/random")
    @Produces(MediaType.APPLICATION_JSON)
    public String getRandomCustomer() {
        List<Customer> list = cf.getAllCustomers();
        int r = random.nextInt(list.size());
        return gson.toJson(list.get(r));
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCustomerOnID(@PathParam("id") int id) {
        return gson.toJson(cf.getCustomerOnID(id));
    }

    /**
     * PUT method for updating or creating an instance of CustomerResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
