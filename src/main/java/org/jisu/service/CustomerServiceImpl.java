package org.jisu.service;

import org.jisu.Repo.CustomerRepo;
import org.jisu.Repo.Mysql.CustomerRepoImpl;
import org.jisu.model.Customer;
import org.jisu.model.Statements;

import java.util.List;

public class CustomerServiceImpl implements CustomerService{


    public CustomerRepo service=new CustomerRepoImpl();
    @Override
    public boolean addCustomer(Customer customer) {

        return service.addCustomer(customer)!=null;

    }

    @Override
    public boolean deleteCustomer(Integer customerId) {
        return service.deleteCustomer(customerId)!=null;
    }

    @Override
    public List<Customer> findAll() {
        return service.findAll();
    }

    @Override
    public List<Statements> getSt(Integer customerId) {
       return service.getStatement(customerId);
    }

    @Override
    public void AddBalance(Customer customer, Integer val) {
        service.diposite(customer,val);
    }

    @Override
    public void WithDraw(Customer customer, Integer val) {
        service.withdraw(customer,val);
    }

    @Override
    public Customer Find(Integer customerId) {
        Customer temp;
        if(service.findById(customerId)==null)
        {
            temp=null;
        }
        else {
            temp=service.findById(customerId);
        }
        return temp;
    }

    @Override
    public Integer Showbalanace(Customer customer) {
        return  customer.getBalance();
    }

}