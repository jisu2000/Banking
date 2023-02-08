package org.jisu.Repo.Mysql;

import org.jisu.Repo.CustomerRepo;
import org.jisu.model.*;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepoImpl implements CustomerRepo {


    boolean unchange;
    LocalDate dt = java.time.LocalDate.now();
    String d = dt.toString();
    private Connection connection;
    public CustomerRepoImpl(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url="jdbc:mysql://localhost:3306/bank";
            String username="root";
            String password="Jisu@2000";
            connection= DriverManager.getConnection(url,username,password);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection(){
        try{
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    @Override
    public Customer addCustomer(Customer customer) {

        String sql="INSERT INTO BANKING VALUES(?,?,?,?)";
        int cnt=0;
        try {
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setInt(1,customer.getCustomerid());
            ps.setString(2,customer.getCustomerName());
            ps.setString(3,customer.getMobileNo());
            ps.setInt(4,customer.getBalance());

            cnt=ps.executeUpdate();

            if(cnt>0)
            {
                return customer;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;

    }

    @Override
    public Customer deleteCustomer(Integer cutomerId) {
        String sql="DELETE from banking where customerid=?";
        int cnt=0;
        try {
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setInt(1,cutomerId);
            cnt=ps.executeUpdate();

            if(cnt>0)
            {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Customer findById(Integer customerId) {

        String sql="SELECT * FROM banking WHERE customerid=?";
        try {
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setInt(1,customerId);
            ResultSet resultSet=ps.executeQuery();
            if(resultSet.next())
            {
                return new Customer(resultSet.getInt("customerid"),resultSet.getString("customername"),resultSet.getString("phone"),resultSet.getInt("balance"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public void diposite(Customer customer, Integer val) {
        int bal=customer.getBalance();



        String sql="UPDATE  banking SET balance=? WHERE customerid=?";
        String sql2="INSERT INTO statement VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setInt(1,bal+val);
            ps.setInt(2,customer.getCustomerid());
            ps.executeUpdate();
            PreparedStatement ps1=connection.prepareStatement(sql2);
            ps1.setInt(4,val);
            ps1.setInt(3,0);
            ps1.setInt(7,bal+val);
            ps1.setString(2,d);
            ps1.setInt(1,customer.getCustomerid());
            ps1.setInt(5,0);
            ps1.setString(6,"");
            ps1.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void withdraw(Customer customer, Integer val) {

        unchange=false;
        if(customer.getBalance()<val)
        {
            unchange=true;
        }
        int bal=customer.getBalance();
        String sql="UPDATE  banking SET balance=? WHERE customerid=?";
        String sql2="INSERT INTO statement VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps=connection.prepareStatement(sql);
            if(unchange)
            {
                ps.setInt(1,bal);
                ps.setInt(2,customer.getCustomerid());
            }
            else {
                ps.setInt(1, bal - val);
                ps.setInt(2, customer.getCustomerid());
            }
            ps.executeUpdate();

            if(!unchange) {
                PreparedStatement ps1 = connection.prepareStatement(sql2);
                ps1.setInt(3, val);
                ps1.setInt(4, 0);
                ps1.setInt(7, bal - val);
                ps1.setString(2, d);
                ps1.setInt(1, customer.getCustomerid());
                ps1.setString(6,"Null");
                ps1.setInt(5,0);
                ps1.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    @Override
    public Integer showBalance(Customer customer) {
        return customer.getBalance();
    }

    @Override
    public void Neft(Customer one,Customer two,Integer amount) {
        unchange=false;

        String sql="UPDATE banking SET balance=? WHERE customerid=?";
        String sql2="UPDATE banking SET balance=? WHERE customerid=?";
        if(one.getBalance()<amount)
        {
            unchange=true;
        }

        try{
            PreparedStatement ps=connection.prepareStatement(sql);

            if(unchange)
            {
                ps.setInt(1,two.getBalance());
                ps.setInt(2,two.getCustomerid());
            }
            else{

                ps.setInt(1,two.getBalance()+amount);
                ps.setInt(2,two.getCustomerid());
            }
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        try {
                PreparedStatement ps1=connection.prepareStatement(sql2);

            if(unchange){
                ps1.setInt(1,one.getBalance());
                ps1.setInt(2,one.getBalance());
            }

            else{
                ps1.setInt(1,one.getBalance()-amount);
                ps1.setInt(2,one.getCustomerid());
            }
            ps1.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        String sender="INSERT INTO statement VALUES(?,?,?,?,?,?,?)";
        String receiver="INSERT INTO statement VALUES(?,?,?,?,?,?,?)";
            if (!unchange)
            {
                try {

                    int b=one.getBalance();

                    PreparedStatement ps1 = connection.prepareStatement(sender);
                    ps1.setInt(3, amount);
                    ps1.setInt(4, 0);
                    ps1.setInt(7, b - amount);
                    ps1.setString(2, d);
                    ps1.setInt(1, one.getCustomerid());
                    ps1.setString(6, "Null");
                    ps1.setInt(5, 0);
                    ps1.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        if (!unchange)
        {
            try {



                PreparedStatement ps1 = connection.prepareStatement(receiver);
                ps1.setInt(3, 0);
                ps1.setInt(4, 0);
                ps1.setInt(7, two.getBalance()+amount);
                ps1.setString(2, d);
                ps1.setInt(1, two.getCustomerid());
                ps1.setString(6, one.getCustomerName());
                ps1.setInt(5, amount);
                ps1.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }

    @Override
    public List<Customer> findAll() {

        String sql="SELECT * FROM banking";
        try{
            Statement st=connection.createStatement();
            ResultSet resultSet=st.executeQuery(sql);
            ArrayList<Customer>list=new ArrayList<>();

            while(resultSet.next())
            {
                list.add(new Customer(resultSet.getInt("customerid"),resultSet.getString("customername"),resultSet.getString("phone"),resultSet.getInt("balance")));
            }
        return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Statements> getStatement(Integer customerid) {
        boolean add=false;
        ArrayList<Statements> list=new ArrayList<>();
        String sql="SELECT * FROM statement";
        try {
           Statement ps=connection.createStatement();

            ResultSet resultSet=ps.executeQuery(sql);


            while(resultSet.next())
            {
                Statements statement=new Statements(resultSet.getInt("customerid"),resultSet.getString("dt"),resultSet.getInt("withdrawl"),resultSet.getInt("diposite"),resultSet.getInt("neft"),resultSet.getString("neftperson"),resultSet.getInt("aval"));
                if(resultSet.getInt("customerid")==customerid) {
                    list.add(statement);
                }

            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
