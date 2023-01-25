package org.jisu.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Statements {
    private Integer customerid;
    private String date;

    private Integer withdraw;

    private Integer diposite;
    private Integer current;


    public Statements(int customerid, String dt, int withdrawl, int diposite, int aval) {
        this.current=aval;
        this.customerid=customerid;
        this.date=dt;
        this.withdraw=withdrawl;
        this.diposite=diposite;
    }


}
