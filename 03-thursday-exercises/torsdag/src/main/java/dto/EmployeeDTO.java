package dto;

import entities.Employee;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDTO {

    private long id;
    private String name;
    private String address;

    public EmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.address = employee.getAddress();
    }
    
    public static List<EmployeeDTO> convertEmployees(List<Employee> employeeList){
        List<EmployeeDTO> result = new ArrayList();
        for (Employee employee : employeeList) {
            result.add(new EmployeeDTO(employee));
        }
        return result;
    }
}
