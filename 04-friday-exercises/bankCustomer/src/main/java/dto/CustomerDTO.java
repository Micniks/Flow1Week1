/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.BankCustomer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Bruger
 */
public class CustomerDTO {

    private Long customerID;
    private String fullName;
    private String accountNumber;
    private double balance;

    public CustomerDTO(BankCustomer customer) {
        this.customerID = customer.getId();
        this.fullName = customer.getFirstName() + " " + customer.getLastName();
        this.accountNumber = customer.getAccountNumber();
        this.balance = customer.getBalance();
    }

    public static List<CustomerDTO> convertCustomers(List<BankCustomer> entities) {
        List<CustomerDTO> result = new ArrayList();
        for (BankCustomer entity : entities) {
            result.add(new CustomerDTO(entity));
        }
        return result;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
