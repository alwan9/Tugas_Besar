package model;

public class Invoice {

    private int invoiceId;
    private String namaItem;
    private double harga;

    public Invoice(int invoiceId,
            String namaItem,
            double harga) {

        this.invoiceId = invoiceId;
        this.namaItem = namaItem;
        this.harga = harga;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public String getNamaItem() {
        return namaItem;
    }

    public double getHarga() {
        return harga;
    }

    @Override
    public String toString() {

        return "Invoice #" + invoiceId
                + "\nItem : " + namaItem
                + "\nHarga : Rp "
                + harga;
    }

}
