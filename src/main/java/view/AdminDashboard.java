package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import util.QRGenerator;
import util.QRGenerator;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class AdminDashboard extends JFrame {

    private JTextArea areaOrder;
    private JTextArea areaWO;
    private JTextArea areaNota;
    private JTextField txtNama;
    private JTextField txtNoHp;
    private JTextField txtAlamat;
    private JTextField txtDeskripsi;
    private JTextField txtSearchOrder;
    private JTextField txtSearchWO;

    public AdminDashboard() {

        setTitle("Admin Dashboard");

        setSize(1000, 600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        areaOrder = new JTextArea();
        areaWO = new JTextArea();
        areaNota = new JTextArea();
        txtSearchOrder = new JTextField();
        txtSearchWO = new JTextField();

        areaOrder.setEditable(false);
        areaWO.setEditable(false);
        areaNota.setEditable(false);

        areaOrder.setBorder(
                BorderFactory.createTitledBorder(
                        "Daftar Order"
                )
        );

        areaWO.setBorder(
                BorderFactory.createTitledBorder(
                        "Daftar Work Order"
                )
        );

        areaNota.setBorder(
                BorderFactory.createTitledBorder(
                        "Daftar Nota"
                )
        );

//        JPanel dataPanel
//                = new JPanel(
//                        new GridLayout(1, 3)
//                );
//
//        dataPanel.add(
//                new JScrollPane(areaOrder)
//        );
//
//        dataPanel.add(
//                new JScrollPane(areaWO)
//        );
//
//        dataPanel.add(
//                new JScrollPane(areaNota)
//        );
        JPanel panelOrder = new JPanel(
                new BorderLayout()
        );

        JPanel searchOrderPanel = new JPanel(
                new BorderLayout()
        );

        searchOrderPanel.add(
                new JLabel(" Cari Order ID / Nama / No HP : "),
                BorderLayout.WEST
        );

        searchOrderPanel.add(
                txtSearchOrder,
                BorderLayout.CENTER
        );

        panelOrder.add(
                searchOrderPanel,
                BorderLayout.NORTH
        );

        panelOrder.add(
                new JScrollPane(areaOrder),
                BorderLayout.CENTER
        );

        JPanel panelWO = new JPanel(
                new BorderLayout()
        );

        JPanel searchWOPanel = new JPanel(
                new BorderLayout()
        );

        searchWOPanel.add(
                new JLabel("Cari Work Order ID / Nama / No HP : "),
                BorderLayout.WEST
        );

        searchWOPanel.add(
                txtSearchWO,
                BorderLayout.CENTER
        );

        panelWO.add(
                searchWOPanel,
                BorderLayout.NORTH
        );

        panelWO.add(
                new JScrollPane(areaWO),
                BorderLayout.CENTER
        );
        JPanel dataPanel = new JPanel(
                new GridLayout(1, 3)
        );

        dataPanel.add(panelOrder);
        dataPanel.add(panelWO);
        dataPanel.add(new JScrollPane(areaNota));
        txtNama = new JTextField();
        txtNoHp = new JTextField();
        txtAlamat = new JTextField();
        txtDeskripsi = new JTextField();

        JButton btnSimpanOrder
                = new JButton("Simpan Order");

        JButton btnRefresh
                = new JButton("Refresh");

        JButton btnBuatWO
                = new JButton("Buat Work Order");

        JButton btnSelesai
                = new JButton("Selesaikan Pesanan");

        JButton btnLogout
                = new JButton("Logout");

        JButton btnHapusOrder
                = new JButton("Hapus Order");

        JButton btnHapusWO
                = new JButton("Hapus Work Order");

        JPanel formPanel
                = new JPanel(
                        new GridLayout(5, 2, 10, 10)
                );

        formPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Input Order"
                )
        );

        formPanel.add(
                new JLabel("Nama Customer")
        );
        formPanel.add(txtNama);

        formPanel.add(
                new JLabel("No HP")
        );
        formPanel.add(txtNoHp);

        formPanel.add(
                new JLabel("Alamat")
        );
        formPanel.add(txtAlamat);

        formPanel.add(
                new JLabel("Deskripsi Pesanan")
        );
        formPanel.add(txtDeskripsi);

        formPanel.add(
                new JLabel()
        );
        formPanel.add(btnSimpanOrder);

        JPanel rightPanel
                = new JPanel(
                        new BorderLayout()
                );

        JPanel topPanel
                = new JPanel();

        topPanel.add(btnRefresh);
        topPanel.add(btnBuatWO);
        topPanel.add(btnSelesai);
        topPanel.add(btnHapusOrder);
        topPanel.add(btnHapusWO);
        topPanel.add(btnLogout);

        rightPanel.add(
                topPanel,
                BorderLayout.NORTH
        );

        rightPanel.add(
                dataPanel,
                BorderLayout.CENTER
        );

        JSplitPane splitPane
                = new JSplitPane(
                        JSplitPane.HORIZONTAL_SPLIT,
                        formPanel,
                        rightPanel
                );

        splitPane.setDividerLocation(350);

        add(splitPane);

        btnSimpanOrder.addActionListener(
                e -> simpanOrderAdmin()
        );

        btnRefresh.addActionListener(
                e -> tampilkanData()
        );

        btnBuatWO.addActionListener(
                e -> buatWorkOrder()
        );

        btnSelesai.addActionListener(
                e -> selesaikanPesanan()
        );

        btnHapusOrder.addActionListener(
                e -> hapusOrder()
        );

        btnHapusWO.addActionListener(
                e -> hapusWorkOrder()
        );

        btnLogout.addActionListener(e -> {

            dispose();

            new LoginFrame().setVisible(true);

        });

        txtSearchOrder.addKeyListener(
                new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(
                    java.awt.event.KeyEvent e) {

                tampilkanData();

            }
        });

        txtSearchWO.addKeyListener(
                new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(
                    java.awt.event.KeyEvent e) {

                tampilkanData();

            }
        });

        tampilkanData();
    }

    private void tampilkanData() {

        areaOrder.setText("");
        areaWO.setText("");
        areaNota.setText("");

        String cariOrder
                = txtSearchOrder.getText()
                        .toLowerCase();

        String cariWO
                = txtSearchWO.getText()
                        .toLowerCase();

        for (Order order : DataStore.orders) {

            String dataOrder
                    = order.getOrderId()
                    + " "
                    + order.getCustomer().getNama()
                    + " "
                    + order.getCustomer().getNoHp()
                    + " "
                    + order.getDeskripsi();

            if (!dataOrder.toLowerCase()
                    .contains(cariOrder)) {
                continue;
            }
            areaOrder.append(
                    "ID : " + order.getOrderId() + "\n"
            );

            areaOrder.append(
                    "Nama : "
                    + order.getCustomer().getNama()
                    + "\n"
            );

            areaOrder.append(
                    "No HP : "
                    + order.getCustomer().getNoHp()
                    + "\n"
            );

            areaOrder.append(
                    "Alamat : "
                    + order.getCustomer().getAlamat()
                    + "\n"
            );

            areaOrder.append(
                    "Deskripsi : "
                    + order.getDeskripsi()
                    + "\n"
            );

            areaOrder.append(
                    "Status : "
                    + order.getStatus()
                    + "\n\n"
            );

            if (order.getInvoice() != null) {

                areaNota.append(
                        "Nota ID : "
                        + order.getInvoice().getInvoiceId()
                        + "\n"
                );

                areaNota.append(
                        "Nama Pesanan : "
                        + order.getInvoice().getNamaItem()
                        + "\n"
                );

                areaNota.append(
                        "Harga : Rp "
                        + order.getInvoice().getHarga()
                        + "\n\n"
                );
            }
        }

        for (WorkOrder wo : DataStore.workOrders) {
            String dataWO
                    = wo.getWoId()
                    + " "
                    + wo.getOrder()
                            .getCustomer()
                            .getNama()
                    + " "
                    + wo.getOrder()
                            .getCustomer()
                            .getNoHp();

            if (!dataWO.toLowerCase()
                    .contains(cariWO)) {
                continue;
            }

            areaWO.append(
                    "WO ID : "
                    + wo.getWoId()
                    + "\n"
            );

            areaWO.append(
                    "Customer : "
                    + wo.getOrder()
                            .getCustomer()
                            .getNama()
                    + "\n"
            );

            areaWO.append(
                    "Progress :\n"
            );

            for (String progress : wo.getProgressList()) {
                areaWO.append(
                        "- "
                        + progress
                        + "\n"
                );
            }

            areaWO.append(
                    "QR : "
                    + (wo.getQrCode() != null
                    ? wo.getQrCode().getKode()
                    : "-")
                    + "\n\n"
            );
        }
    }

    private void buatWorkOrder() {

        String input
                = JOptionPane.showInputDialog(
                        this,
                        "Masukkan Order ID"
                );

        if (input == null) {
            return;
        }

        try {

            int orderId
                    = Integer.parseInt(input);

            Order target = null;

            for (Order order
                    : DataStore.orders) {

                if (order.getOrderId()
                        == orderId) {

                    target = order;
                    break;
                }
            }

            if (target == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Order tidak ditemukan"
                );

                return;
            }

            for (WorkOrder wo : DataStore.workOrders) {

                if (wo.getOrder().getOrderId()
                        == target.getOrderId()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Order ini sudah memiliki Work Order!"
                    );

                    return;
                }

            }

            WorkOrder wo
                    = new WorkOrder(
                            DataStore.generateWorkOrderId(),
                            target,
                            "Work Order Dibuat"
                    );

            QRCode qr
                    = new QRCode(
                            "QR-TRACK-"
                            + wo.getWoId()
                    );

            String qrPath
                    = QRGenerator.generateQR(
                            qr.getKode()
                    );

            wo.setQrCode(qr);

            DataStore.workOrders.add(wo);

            target.setStatus(
                    "Work Order Dibuat"
            );

            ImageIcon icon
                    = new ImageIcon(qrPath);

            JLabel label
                    = new JLabel(
                            "Work Order berhasil dibuat\n"
                            + qr.getKode(),
                            icon,
                            JLabel.CENTER
                    );

            JOptionPane.showMessageDialog(
                    this,
                    label,
                    "QR Tracking",
                    JOptionPane.INFORMATION_MESSAGE
            );

            tampilkanData();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Input tidak valid"
            );
        }
    }

    private void selesaikanPesanan() {

        String inputWO
                = JOptionPane.showInputDialog(
                        this,
                        "Masukkan WO ID"
                );

        if (inputWO == null) {
            return;
        }

        try {

            int woId
                    = Integer.parseInt(
                            inputWO
                    );

            for (WorkOrder wo
                    : DataStore.workOrders) {

                if (wo.getWoId() == woId) {

                    if (!wo.getStatus().equalsIgnoreCase("QC")) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Pesanan belum mencapai tahap QC!"
                        );

                        return;
                    }

                    JTextField txtItem
                            = new JTextField();

                    JTextField txtHarga
                            = new JTextField();

                    Object[] fields = {
                        "Nama Item", txtItem,
                        "Harga", txtHarga
                    };

                    int result
                            = JOptionPane.showConfirmDialog(
                                    this,
                                    fields,
                                    "Buat Nota",
                                    JOptionPane.OK_CANCEL_OPTION
                            );

                    if (result
                            != JOptionPane.OK_OPTION) {

                        return;
                    }

                    Invoice invoice
                            = new Invoice(
                                    DataStore.generateInvoiceId(),
                                    txtItem.getText(),
                                    Double.parseDouble(
                                            txtHarga.getText()
                                    )
                            );

                    wo.getOrder()
                            .setInvoice(
                                    invoice
                            );

                    wo.setStatus(
                            "Selesai"
                    );

                    wo.getOrder()
                            .setStatus(
                                    "Selesai"
                            );

                    JOptionPane.showMessageDialog(
                            this,
                            "Pesanan berhasil diselesaikan"
                    );

                    tampilkanData();

                    return;
                }
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Work Order tidak ditemukan"
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Input tidak valid"
            );
        }

    }

    private void hapusOrder() {

        String input = JOptionPane.showInputDialog(
                this,
                "Masukkan Order ID yang akan dihapus"
        );

        if (input == null) {
            return;
        }

        try {

            int orderId = Integer.parseInt(input);

            Order target = null;

            for (Order order : DataStore.orders) {

                if (order.getOrderId() == orderId) {
                    target = order;
                    break;
                }

            }

            if (target == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Order tidak ditemukan"
                );

                return;
            }
            for (WorkOrder wo : DataStore.workOrders) {

                if (wo.getOrder().getOrderId()
                        == target.getOrderId()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Order masih memiliki Work Order!"
                    );

                    return;
                }

            }
            DataStore.orders.remove(target);

            JOptionPane.showMessageDialog(
                    this,
                    "Order berhasil dihapus"
            );

            tampilkanData();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Input tidak valid"
            );

        }

    }

    private void hapusWorkOrder() {

        String input = JOptionPane.showInputDialog(
                this,
                "Masukkan WO ID yang akan dihapus"
        );

        if (input == null) {
            return;
        }

        try {

            int woId = Integer.parseInt(input);

            WorkOrder target = null;

            for (WorkOrder wo : DataStore.workOrders) {

                if (wo.getWoId() == woId) {

                    target = wo;
                    break;

                }

            }

            if (target == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Work Order tidak ditemukan"
                );

                return;

            }

            target.getOrder().setStatus(
                    "Order Dibuat"
            );

            DataStore.workOrders.remove(target);

            JOptionPane.showMessageDialog(
                    this,
                    "Work Order berhasil dihapus"
            );

            tampilkanData();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Input tidak valid"
            );

        }

    }

    private void simpanOrderAdmin() {

        try {

            Customer customer
                    = new Customer(
                            DataStore.generateCustomerId(),
                            txtNama.getText(),
                            txtNoHp.getText(),
                            txtAlamat.getText()
                    );

            DataStore.customers.add(customer);

            Order order
                    = new Order(
                            DataStore.generateOrderId(),
                            customer,
                            txtDeskripsi.getText(),
                            "Order Dibuat"
                    );

            DataStore.orders.add(order);

            JOptionPane.showMessageDialog(
                    this,
                    "Order berhasil dibuat!\nOrder ID : "
                    + order.getOrderId()
            );

            txtNama.setText("");
            txtNoHp.setText("");
            txtAlamat.setText("");
            txtDeskripsi.setText("");

            tampilkanData();

        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );
        }
    }

}
