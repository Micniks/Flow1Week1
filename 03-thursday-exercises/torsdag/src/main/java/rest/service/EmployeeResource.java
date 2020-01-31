/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.service;

import com.google.gson.Gson;
import dto.EmployeeDTO;
import facades.EmployeeFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Bruger
 */
@Path("employee")
public class EmployeeResource {

    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    private final static EmployeeFacade EF = EmployeeFacade.getFacadeExample(emf);
    private final static Gson gson = new Gson();
    private final static boolean setup = setup();
    
    private static boolean setup(){
        EF.createEmployee("Jackson", "address irrelevent", 5000);
        EF.createEmployee("Jackson Jr.", "address irrelevent", 1000);
        EF.createEmployee("Jackson Senior.", "address irrelevent", 10000);
        EF.createEmployee("Peter", "address irrelevent", 4000);
        EF.createEmployee("Peter", "address irrelevent", 1100);
        EF.createEmployee("Frank", "address irrelevent", 0);
        EF.createEmployee("William", "address irrelevent", -500);
        EF.createEmployee("Maria", "address irrelevent", 10000);
        return true;
    }

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of EmployeeResource
     */
    public EmployeeResource() {
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll() {
        List<EmployeeDTO> employees = EmployeeDTO.convertEmployees(EF.getAllEmployees());
        return gson.toJson(employees);
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getEmployeeOnID(@PathParam("id") long id) {
        EmployeeDTO employee = new EmployeeDTO(EF.getEmployeeByID(id));
        return gson.toJson(employee);
    }
    
    @GET
    @Path("/highestpaid")
    @Produces(MediaType.APPLICATION_JSON)
    public String getHighestSalaryEmployee() {
        List<EmployeeDTO> employees = EmployeeDTO.convertEmployees(EF.getEmployeesWithHighestSalary());
        return gson.toJson(employees);
    }

    @GET
    @Path("/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getHighestSalaryEmployee(@PathParam("name") String name) {
        List<EmployeeDTO> employees = EmployeeDTO.convertEmployees(EF.getEmployeesByName(name));
        return gson.toJson(employees);
    }
    
    @GET
    @Path("/name")
    @Produces(MediaType.APPLICATION_JSON)
    public String getHighestSalaryEmployeeWithQ(@QueryParam("name") String name) {
        List<EmployeeDTO> employees = EmployeeDTO.convertEmployees(EF.getEmployeesByName(name));
        return gson.toJson(employees);
    }
    
    /**
     * Retrieves representation of an instance of rest.service.EmployeeResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of EmployeeResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
