package org.jisu;

import org.jisu.model.Customer;
import org.jisu.service.CustomerService;
import org.jisu.service.CustomerServiceImpl;

import java.util.Scanner;
public class App 
{
    public static void main( String[] args ) {
        CustomerService service = new CustomerServiceImpl();
        Scanner sc = new Scanner(System.in);
        int choose;
        do {
            System.out.println("Enter [1] for do Admin Work || Enter [2] for do Customer Work || [-1] for exit");
            choose = sc.nextInt();

            switch (choose) {
                case 1:
                    Admin();
                    break;

                case 2:
                    User();
                    break;
            }
        } while(choose!=-1);
    }

     public static void Admin() {
        int id;
        int choice;
        Scanner scan=new Scanner(System.in);
        CustomerService service = new CustomerServiceImpl();
        Customer temp;

        do {
            System.out.println("Enter |1|-> (Add Customer) |2|-> (Close Account) |3|-> (Display a customer)  |4|-> (Display all customer) |-1|=> to exit");
            choice =scan.nextInt();
            switch (choice) {
                case 1:
                    temp = new Customer();
                    System.out.println("Enter Customer Details");
                    System.out.println("Enter Customer Id");
                    temp.setCustomerid(scan.nextInt());
                    if (service.Find(temp.getCustomerid()) != null) {
                        System.out.println("Customer Id is already present");
                        break;
                    }
                    System.out.println("Enter Customer name");
                    temp.setCustomerName(scan.next());
                    System.out.println("Enter Mobile No");
                    temp.setMobileNo(scan.next());
                    System.out.println("Enter Opening Balance");
                    temp.setBalance(scan.nextInt());


                    service.addCustomer(temp);
                    System.out.println("Congrats !! " + temp.getCustomerName() + " Welcome to Our bank");
                    break;

                case 2:
                    System.out.println("Enter CustomerId");
                    id = scan.nextInt();
                    if (service.Find(id) == null) {
                        System.out.println("Customer is not present");
                    } else {
                        temp = service.Find(id);
                        String t = temp.getCustomerName();
                        service.deleteCustomer(id);
                        System.out.println(" Hi! " + t + " we have closed Your account You were our valuable customer");


                    }
                    break;

                case 3:
                    System.out.println("Enter Customer id");
                    id = scan.nextInt();
                    temp = service.Find(id);
                {
                    if (temp == null) {
                        System.out.println("Customer not Found!");
                    } else {
                        System.out.println(temp);
                    }

                }

                break;

                case 4:

                    service.findAll().forEach(System.out::println);
                    break;


            }

        }while (choice!=-1);
    }


    public static void User()
    {
        int choice;
        int id;
        Scanner sc=new Scanner(System.in);
        CustomerService service = new CustomerServiceImpl();
        Customer temp;
        Customer temp2;
        do {
            System.out.println("|5|->(Deposit) |6|-> (Withdraw) |7|->(Balance Inquiry) |8|-> (Get Statement) |9-> (Neft) |0|->(Exit)");
            choice = sc.nextInt();
            switch (choice) {
                case 5:
                    System.out.println("Enter the Customerid ");
                    id = sc.nextInt();
                    if (service.Find(id) == null) {
                        System.out.println("Invalid CustomerID");
                        break;
                    } else {
                        temp = service.Find(id);
                        System.out.println("Enter the amount to deposit");
                        int prev = temp.getBalance();
                        int bal = sc.nextInt();
                        service.AddBalance(temp, bal);
                        System.out.println("Account Credited with Rs " + bal + "/- Available amount Rs " + (prev + bal) + "/-");

                    }
                    break;

                case 6:
                    System.out.println("Enter Customer id");
                    id = sc.nextInt();
                    if (service.Find(id) == null) {
                        System.out.println("Customer not found!!");
                        break;
                    }
                    System.out.println("Enter Amount to Withdrawal");
                    int amount = sc.nextInt();
                    temp = service.Find(id);
                    if (amount > temp.getBalance()) {
                        System.out.println("Insufficient Balance !!");
                    } else {
                        int bal = temp.getBalance() - amount;
                        service.WithDraw(temp, amount);

                        System.out.println("Account has been debited with Rs " + amount + "/- Available amount Rs " + bal + "/-");
                    }
                    break;

                case 7:
                    System.out.println("Enter Customer Id");
                    id = sc.nextInt();
                    if (service.Find(id) == null) {
                        System.out.println("Customer not found !!");
                    } else {
                        temp = service.Find(id);
                        System.out.println("Available amount Rs " + temp.getBalance() + "/-");
                    }
                    break;

                case 8:
                    System.out.println("Enter Customer id");
                    id = sc.nextInt();
                    service.getSt(id).forEach(System.out::println);
                    break;

                case 9:
                    System.out.println("Enter Sender CustomerId");
                    id = sc.nextInt();
                    if (service.Find(id) == null) {
                        System.out.println("CustomerId not found!");
                        break;
                    }

                    System.out.println("Enter Receiver CustomerId");
                    int id2 = sc.nextInt();
                    if (service.Find(id2) == null) {
                        System.out.println("Customer Not found");
                        break;
                    }

                    System.out.println("Enter the Amount for Transfer");
                    int n = sc.nextInt();
                    temp = service.Find(id);
                    temp2 = service.Find(id2);

                    if (temp.getBalance() < n) {
                        System.out.println("Insufficient Balance!!");
                        break;
                    }
                    service.neft(temp, temp2, n);

                    System.out.println("Successfully Transferred " + n);

                    System.out.println(" Your Available Balance is " + (temp.getBalance() - n) + "/-");
                    break;

            }
        }while (choice!=0);
    }
}

