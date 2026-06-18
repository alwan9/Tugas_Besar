package model;

import java.util.HashMap;

import java.util.ArrayList;

public class DataStore {

    public static ArrayList<User> users
            = new ArrayList<>();

    public static ArrayList<Customer> customers
            = new ArrayList<>();

    public static HashMap<Integer, Customer> customerMap
            = new HashMap<>();

    public static ArrayList<Order> orders
            = new ArrayList<>();

    public static ArrayList<WorkOrder> workOrders
            = new ArrayList<>();

    private static int customerCounter = 1;
    private static int orderCounter = 1;
    private static int invoiceCounter = 1;
    private static int workOrderCounter = 1;

    public static int generateCustomerId() {
        return customerCounter++;
    }

    public static int generateOrderId() {
        return orderCounter++;
    }

    public static int generateInvoiceId() {
        return invoiceCounter++;
    }

    public static int generateWorkOrderId() {
        return workOrderCounter++;
    }

    public static Customer cariCustomer(int id) {
        return customerMap.get(id);
    }

    public static void initData() {

        users.add(
                new Admin(
                        1,
                        "Administrator",
                        "admin",
                        "admin123"
                )
        );

        users.add(
                new Marketing(
                        2,
                        "Marketing",
                        "marketing",
                        "marketing123"
                )
        );

        users.add(
                new Produksi(
                        3,
                        "Produksi",
                        "produksi",
                        "produksi123"
                )
        );
    }
}
