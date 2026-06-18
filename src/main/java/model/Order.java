package model;

public class Order {

    private int orderId;
    private Customer customer;
    private String deskripsi;
    private String status;
    private Invoice invoice;

    public Order(int orderId,
            Customer customer,
            String deskripsi,
            String status) {

        this.orderId = orderId;
        this.customer = customer;
        this.deskripsi = deskripsi;
        this.status = status;
        this.invoice = null;
    }
    
    public int getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getStatus() {
        return status;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
