package model;

import java.util.ArrayList;

public class WorkOrder implements Trackable {

    private int woId;
    private Order order;
    private String status;
    private QRCode qrCode;

    private ArrayList<String> progressList;

    public WorkOrder(int woId,
            Order order,
            String status) {

        this.woId = woId;
        this.order = order;
        this.status = status;

        progressList = new ArrayList<>();

        progressList.add(status);
    }

    public int getWoId() {
        return woId;
    }

    public Order getOrder() {
        return order;
    }

    public String getStatus() {
        return status;
    }

    public QRCode getQrCode() {
        return qrCode;
    }

    public ArrayList<String> getProgressList() {
        return progressList;
    }

    public void tambahProgress(String progress) {

        progressList.add(progress);

        status = progress;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setQrCode(QRCode qrCode) {
        this.qrCode = qrCode;
    }

    @Override
    public String toString() {

        return "WO-" + woId
                + " | "
                + order.getDeskripsi();
    }

    @Override
    public void tampilkanTracking() {
        System.out.println("Tracking WO : " + woId);
    }
}
