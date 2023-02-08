package org.jisu.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Statements {
    private Integer customerid;
    private String date;

    private Integer withdraw;

    private Integer diposite;
    private Integer current;

    private Integer neft;

    private String sender;




    public Statements(Integer customerid, String date, Integer withdraw, Integer diposite, Integer current) {
        this.customerid = customerid;
        this.date = date;
        this.withdraw = withdraw;
        this.diposite = diposite;
        this.current = current;
    }

    public Statements(Integer customerid, String date, Integer withdraw, Integer diposite, Integer neft, String sender,Integer current) {
        this.customerid = customerid;
        this.date = date;
        this.withdraw = withdraw;
        this.diposite = diposite;
        this.current = current;
        this.neft = neft;
        this.sender = sender;
    }


}
