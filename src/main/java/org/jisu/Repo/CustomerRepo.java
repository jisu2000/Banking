package org.jisu.Repo;

import org.jisu.model.Customer;
import org.jisu.model.Statements;

import java.util.List;

public interface CustomerRepo {
    public Customer addCustomer(Customer customer);

    public Customer deleteCustomer(Integer cutomerId);

    public Customer findById(Integer customerId);

    public void diposite(Customer customer,Integer val);


    public void withdraw(Customer customer,Integer val);

    public Integer showBalance(Customer customer);

    public void Neft(Customer one,Customer two,Integer amount);

    public List<Customer> findAll();

    public List<Statements> getStatement(Integer customerid);
}
