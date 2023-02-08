package org.jisu.service;

import org.jisu.model.Customer;
import org.jisu.model.Statements;

import java.util.List;

public interface CustomerService {
    public boolean addCustomer(Customer customer);

    public boolean deleteCustomer(Integer customerId);

    public List<Customer> findAll();

    public List<Statements> getSt(Integer customerId);


    public void AddBalance(Customer customer,Integer val);

    public void WithDraw(Customer customer,Integer val);

    public Customer Find(Integer customerId);

    public Integer Showbalanace(Customer customer);

    public void neft(Customer one,Customer two, Integer amount);

}