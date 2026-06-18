package model;

public class Customer {

    private int customerId;
    private String nama;
    private String noHp;
    private String alamat;

    public Customer(
            int customerId,
            String nama,
            String noHp,
            String alamat) {

        this.customerId = customerId;
        this.nama = nama;
        this.noHp = noHp;
        this.alamat = alamat;
    }

    public Customer(String nama) {
        this.nama = nama;
    }

    public Customer(String nama, String noHp) {
        this.nama = nama;
        this.noHp = noHp;
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

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Customer)) {
            return false;
        }

        Customer other = (Customer) obj;

        return this.customerId == other.customerId;
    }

    @Override
    public String toString() {
        return customerId + " - " + nama;
    }

}
