package org.jisu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {
    private Integer customerid;
    private String customerName;

    private String mobileNo;


    private Integer balance;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return customerid.equals(customer.customerid);
    }


    @Override
    public int hashCode() {
        return Objects.hash(customerid);
    }
}
