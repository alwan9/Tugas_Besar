package model;

public class QRCode {

    private String kode;

    public QRCode(String kode) {
        this.kode = kode;
    }

    public String getKode() {
        return kode;
    }

    @Override
    public String toString() {
        return kode;
    }
}