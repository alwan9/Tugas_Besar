package model;

public class Customer {

    private int customerId;
    private String nama;
    private String noHp;
    private String alamat;

    public Customer(int customerId, String nama,
                    String noHp, String alamat) {

        this.customerId = customerId;
        this.nama = nama;
        this.noHp = noHp;
        this.alamat = alamat;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getNama() {
        return nama;
    }

    public String getNoHp() {
        return noHp;
    }

    public String getAlamat() {
        return alamat;
    }

    @Override
    public String toString() {
        return customerId + " - " + nama;
    }
}