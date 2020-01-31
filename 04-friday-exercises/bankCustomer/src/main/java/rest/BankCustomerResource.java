package rest;

import com.google.gson.Gson;
import dto.CustomerDTO;
import entities.BankCustomer;
import facades.BankCustomerFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Path("bankCustomer")
public class BankCustomerResource {
    
    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    private final static BankCustomerFacade bcf = BankCustomerFacade.getFacade(emf);
    private final static Gson gson = new Gson();

    @Context
    private UriInfo context;

    public BankCustomerResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        return "";
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getID(@PathParam("id") Long id) {
        CustomerDTO result = bcf.getCustomerByID(id);
        return gson.toJson(result);
    }
    
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll() {
        List<BankCustomer> result = bcf.getAllBankCustomers();
        return gson.toJson(result);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
