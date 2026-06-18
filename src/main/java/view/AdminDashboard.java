package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Customer;
import model.DataStore;
import model.Invoice;
import model.Order;
import model.QRCode;
import model.WorkOrder;
import util.QRGenerator;

public class AdminDashboard extends JFrame {

    private JPanel panelOrderList;
    private JPanel panelWOList;
    private JTextArea areaNota;
    private JTextField txtNama;
    private JTextField txtNoHp;
    private JTextField txtAlamat;
    private JTextField txtDeskripsi;
    private JTextField txtSearchOrder;
    private JTextField txtSearchWO;

    public AdminDashboard() {

        setTitle("Admin Dashboard");

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelOrderList = new JPanel();
        panelOrderList.setLayout(
                new BoxLayout(
                        panelOrderList,
                        BoxLayout.Y_AXIS));

        panelWOList = new JPanel();
        panelWOList.setLayout(
                new BoxLayout(
                        panelWOList,
                        BoxLayout.Y_AXIS));
        areaNota = new JTextArea();
        txtSearchOrder = new JTextField();
        txtSearchWO = new JTextField();

        areaNota.setEditable(false);

        areaNota.setBorder(
                BorderFactory.createTitledBorder(
                        "Daftar Nota"));

        JPanel panelOrder = new JPanel(
                new BorderLayout());

        JPanel searchOrderPanel = new JPanel(
                new BorderLayout());

        searchOrderPanel.add(
                new JLabel(" Cari Order ID / Nama / No HP : "),
                BorderLayout.WEST);

        searchOrderPanel.add(
                txtSearchOrder,
                BorderLayout.CENTER);

        panelOrder.add(
                searchOrderPanel,
                BorderLayout.NORTH);

        panelOrder.add(
                new JScrollPane(panelOrderList),
                BorderLayout.CENTER);

        // panelOrder.add(
        // panelOrder.add(
        // btnHapusOrder,
        // BorderLayout.EAST);
        JPanel panelWO = new JPanel(
                new BorderLayout());

        JPanel searchWOPanel = new JPanel(
                new BorderLayout());

        searchWOPanel.add(
                new JLabel("Cari Work Order ID / Nama / No HP : "),
                BorderLayout.WEST);

        searchWOPanel.add(
                txtSearchWO,
                BorderLayout.CENTER);

        panelWO.add(
                searchWOPanel,
                BorderLayout.NORTH);

        panelWO.add(
                new JScrollPane(panelWOList),
                BorderLayout.CENTER);

        // panelWO.add(
        // btnHapusWO,
        // BorderLayout.EAST);
        JPanel dataPanel = new JPanel(
                new GridLayout(1, 3));

        dataPanel.add(panelOrder);
        dataPanel.add(panelWO);
        dataPanel.add(new JScrollPane(areaNota));
        txtNama = new JTextField(15);
        txtNoHp = new JTextField(15);
        txtAlamat = new JTextField(25);
        txtDeskripsi = new JTextField(35);

        txtNama.addActionListener(e -> txtNoHp.requestFocus());
        txtNoHp.addActionListener(e -> txtAlamat.requestFocus());
        txtAlamat.addActionListener(e -> txtDeskripsi.requestFocus());

        JButton btnSimpanOrder = new JButton("Simpan Order");

        JButton btnRefresh = new JButton("Refresh");

        JButton btnBuatWO = new JButton("Buat Work Order");

        JButton btnSelesai = new JButton("Selesaikan Pesanan");

        JButton btnLogout = new JButton("Logout");

        JPanel formPanel = new JPanel(
                new GridLayout(5, 2, 10, 10));

        formPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Input Order"));

        formPanel.add(
                new JLabel("Nama Customer"));
        formPanel.add(txtNama);

        formPanel.add(
                new JLabel("No HP"));
        formPanel.add(txtNoHp);

        formPanel.add(
                new JLabel("Alamat"));
        formPanel.add(txtAlamat);

        formPanel.add(
                new JLabel("Deskripsi Pesanan"));
        formPanel.add(txtDeskripsi);

        formPanel.add(
                new JLabel());
        formPanel.add(btnSimpanOrder);

        JPanel rightPanel = new JPanel(
                new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);

        styleButton(btnRefresh, new Color(52, 152, 219), Color.WHITE);
        styleButton(btnBuatWO, new Color(46, 204, 113), Color.WHITE);
        styleButton(btnSelesai, new Color(241, 196, 15), Color.BLACK);
        styleButton(btnLogout, new Color(231, 76, 60), Color.WHITE); // 🔴 LOGOUT MERAH

        topPanel.add(btnRefresh);
        topPanel.add(btnBuatWO);
        topPanel.add(btnSelesai);
        topPanel.add(btnLogout);

        rightPanel.add(
                topPanel,
                BorderLayout.NORTH);

        rightPanel.add(
                dataPanel,
                BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                formPanel,
                rightPanel);

        splitPane.setDividerLocation(350);

        add(splitPane);

        btnSimpanOrder.addActionListener(
                e -> simpanOrderAdmin());

        btnRefresh.addActionListener(
                e -> tampilkanData());

        btnBuatWO.addActionListener(
                e -> buatWorkOrder());

        btnSelesai.addActionListener(
                e -> selesaikanPesanan());

        // btnHapusOrder.addActionListener(
        // e -> hapusOrder());
        //
        // btnHapusWO.addActionListener(
        // e -> hapusWorkOrder());
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

        panelOrderList.removeAll();
        panelWOList.removeAll();
        areaNota.setText("");

        String cariOrder = txtSearchOrder.getText().toLowerCase();
        String cariWO = txtSearchWO.getText().toLowerCase();

        // ================= ORDER =================
        for (int i = 0; i < DataStore.orders.size(); i++) {

            Order order = DataStore.orders.get(i);

            String dataOrder = order.getOrderId() + " "
                    + order.getCustomer().getNama() + " "
                    + order.getCustomer().getNoHp() + " "
                    + order.getDeskripsi();

            if (!dataOrder.toLowerCase().contains(cariOrder)) {
                continue;
            }

            JPanel card = new JPanel(new BorderLayout());

            card.setBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)));

            JTextArea info = new JTextArea(
                    "ID : " + order.getOrderId()
                    + "\nNama : " + order.getCustomer().getNama()
                    + "\nNo HP : " + order.getCustomer().getNoHp()
                    + "\nAlamat : " + order.getCustomer().getAlamat()
                    + "\nDeskripsi : " + order.getDeskripsi()
                    + "\nStatus : " + order.getStatus());

            info.setEditable(false);
            info.setBackground(card.getBackground());

            JButton btnEdit = new JButton("Edit");
            styleButton(btnEdit,
                    new Color(241, 196, 15),
                    Color.BLACK);
            JButton btnHapus = new JButton("Hapus");

            styleButton(btnHapus,
                    new Color(231, 76, 60),
                    Color.WHITE);
            btnEdit.addActionListener(e -> {

                JTextField txtNamaEdit = new JTextField(order.getCustomer().getNama());

                JTextField txtHpEdit = new JTextField(order.getCustomer().getNoHp());

                JTextField txtAlamatEdit = new JTextField(order.getCustomer().getAlamat());

                JTextField txtDeskripsiEdit = new JTextField(order.getDeskripsi());

                txtDeskripsiEdit.addActionListener(
                        ev -> {
                            order.getCustomer().setNama(
                                    txtNamaEdit.getText());

                            order.getCustomer().setNoHp(
                                    txtHpEdit.getText());

                            order.getCustomer().setAlamat(
                                    txtAlamatEdit.getText());

                            order.setDeskripsi(
                                    txtDeskripsiEdit.getText());

                            tampilkanData();
                        });

                Object[] fields = {
                    "Nama", txtNamaEdit,
                    "No HP", txtHpEdit,
                    "Alamat", txtAlamatEdit,
                    "Deskripsi", txtDeskripsiEdit
                };

                int result = JOptionPane.showConfirmDialog(
                        this,
                        fields,
                        "Edit Order",
                        JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {

                    order.getCustomer().setNama(
                            txtNamaEdit.getText());

                    order.getCustomer().setNoHp(
                            txtHpEdit.getText());

                    order.getCustomer().setAlamat(
                            txtAlamatEdit.getText());

                    order.setDeskripsi(
                            txtDeskripsiEdit.getText());

                    tampilkanData();
                }
            });

            btnHapus.addActionListener(e -> {

                for (WorkOrder wo : DataStore.workOrders) {

                    if (wo.getOrder().getOrderId() == order.getOrderId()) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Order masih memiliki Work Order!");

                        return;
                    }
                }

                DataStore.orders.remove(order);

                tampilkanData();
            });

            JPanel panelButton = new JPanel();

            panelButton.setLayout(
                    new BoxLayout(
                            panelButton,
                            BoxLayout.Y_AXIS));

            panelButton.add(btnEdit);
            panelButton.add(Box.createVerticalStrut(5));
            panelButton.add(btnHapus);

            card.add(info, BorderLayout.CENTER);
            card.add(panelButton, BorderLayout.EAST);

            panelOrderList.add(card);

            if (order.getInvoice() != null) {

                areaNota.append(
                        "Nota ID : "
                        + order.getInvoice().getInvoiceId()
                        + "\n");

                areaNota.append(
                        "Nama Pesanan : "
                        + order.getInvoice().getNamaItem()
                        + "\n");

                areaNota.append(
                        "Harga : Rp "
                        + order.getInvoice().getHarga()
                        + "\n\n");
            }
        }

        // ================= WORK ORDER =================
        for (WorkOrder wo : DataStore.workOrders) {

            String dataWO = wo.getWoId() + " "
                    + wo.getOrder().getCustomer().getNama() + " "
                    + wo.getOrder().getCustomer().getNoHp();

            if (!dataWO.toLowerCase().contains(cariWO)) {
                continue;
            }

            JPanel card = new JPanel(new BorderLayout());

            card.setBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)));

            JTextArea info = new JTextArea(
                    "WO ID : " + wo.getWoId()
                    + "\nCustomer : "
                    + wo.getOrder().getCustomer().getNama()
                    + "\nStatus : "
                    + wo.getStatus());

            info.setEditable(false);
            info.setBackground(card.getBackground());

            JButton btnHapus = new JButton("Hapus");

            styleButton(btnHapus, new Color(231, 76, 60), Color.WHITE);

            btnHapus.addActionListener(e -> {

                wo.getOrder().setStatus("Order Dibuat");

                DataStore.workOrders.remove(wo);

                tampilkanData();
            });

            JPanel panelButton = new JPanel();

            panelButton.setLayout(
                    new BoxLayout(
                            panelButton,
                            BoxLayout.Y_AXIS));

            panelButton.add(btnHapus);

            card.add(info, BorderLayout.CENTER);
            card.add(panelButton, BorderLayout.EAST);

            panelWOList.add(card);
        }

        panelOrderList.revalidate();
        panelOrderList.repaint();

        panelWOList.revalidate();
        panelWOList.repaint();
    }

    private void buatWorkOrder() {

        String input = JOptionPane.showInputDialog(
                this,
                "Masukkan Order ID");

        try {

            if (input == null || input.isBlank()) {
                return;
            }

            if (!input.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Order ID harus angka");
                return;
            }

            int orderId = Integer.parseInt(input);

            Order target = null;

            for (int i = 0; i < DataStore.orders.size(); i++) {

                Order order = DataStore.orders.get(i);

                if (order.getOrderId() == orderId) {

                    target = order;
                    break;
                }
            }

            if (target == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Order tidak ditemukan");

                return;
            }

            System.out.println("Target ditemukan = " + target.getOrderId());
            for (WorkOrder wo : DataStore.workOrders) {

                if (wo.getOrder().getOrderId() == target.getOrderId()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Order ini sudah memiliki Work Order!");

                    return;
                }

            }

            WorkOrder wo = new WorkOrder(
                    DataStore.generateWorkOrderId(),
                    target,
                    "Work Order Dibuat");

            QRCode qr = new QRCode(
                    "QR-TRACK-"
                    + wo.getWoId());

            String qrPath = QRGenerator.generateQR(
                    qr.getKode());

            wo.setQrCode(qr);

            DataStore.workOrders.add(wo);

            target.setStatus(
                    "Work Order Dibuat");

            ImageIcon icon = new ImageIcon(qrPath);

            JLabel label = new JLabel();

            label.setIcon(icon);
            label.setText("<html><div style='text-align:center;'>"
                    + "Work Order berhasil dibuat<br>"
                    + qr.getKode()
                    + "</div></html>");
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.BOTTOM);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            JOptionPane.showMessageDialog(
                    this,
                    label,
                    "QR Tracking",
                    JOptionPane.INFORMATION_MESSAGE);

            tampilkanData();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Input tidak valid");
        }
    }

    private void selesaikanPesanan() {

        String inputWO = JOptionPane.showInputDialog(this, "Masukkan WO ID");

        if (inputWO == null || inputWO.isBlank()) {
            return;
        }

        if (!inputWO.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "WO ID harus angka");
            return;
        }

        try {

            int woId = Integer.parseInt(inputWO);

            for (WorkOrder wo : DataStore.workOrders) {

                if (wo.getWoId() == woId) {

                    if (!wo.getStatus().equalsIgnoreCase("QC")) {
                        JOptionPane.showMessageDialog(this,
                                "Pesanan belum mencapai tahap QC!");
                        return;
                    }

                    JTextField txtItem = new JTextField();
                    JTextField txtHarga = new JTextField();

                    Object[] fields = {
                        "Nama Item", txtItem,
                        "Harga", txtHarga
                    };

                    int result = JOptionPane.showConfirmDialog(
                            this,
                            fields,
                            "Buat Nota",
                            JOptionPane.OK_CANCEL_OPTION);

                    if (result != JOptionPane.OK_OPTION) {
                        return;
                    }

                    // VALIDASI ITEM
                    if (txtItem.getText().isBlank()) {
                        JOptionPane.showMessageDialog(this, "Nama item wajib diisi");
                        return;
                    }

                    // VALIDASI HARGA
                    String hargaStr = txtHarga.getText();

                    if (hargaStr == null || hargaStr.isBlank()) {
                        JOptionPane.showMessageDialog(this, "Harga wajib diisi");
                        return;
                    }

                    if (!hargaStr.matches("\\d+(\\.\\d+)?")) {
                        JOptionPane.showMessageDialog(this, "Harga harus angka valid");
                        return;
                    }

                    double harga = Double.parseDouble(hargaStr);

                    Invoice invoice = new Invoice(
                            DataStore.generateInvoiceId(),
                            txtItem.getText(),
                            harga);

                    wo.getOrder().setInvoice(invoice);
                    wo.setStatus("Selesai");
                    wo.getOrder().setStatus("Selesai");

                    JOptionPane.showMessageDialog(this,
                            "Pesanan berhasil diselesaikan");

                    tampilkanData();
                    return;
                }
            }

            JOptionPane.showMessageDialog(this,
                    "Work Order tidak ditemukan");

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this,
                    "Terjadi kesalahan sistem");
        }
    }

    private void styleButton(JButton button, Color bgColor, Color textColor) {

        button.setBackground(bgColor);
        button.setForeground(textColor);

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setFont(new Font("Segoe UI", Font.BOLD, 12));

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(bgColor.darker()),
                        BorderFactory.createEmptyBorder(8, 15, 8, 15)
                )
        );
    }

    private void simpanOrderAdmin() {

        try {

            // 1. VALIDASI HARUS DI AWAL
            if (txtNama.getText().isBlank()
                    || txtNoHp.getText().isBlank()
                    || txtAlamat.getText().isBlank()
                    || txtDeskripsi.getText().isBlank()) {

                JOptionPane.showMessageDialog(this, "Semua field wajib diisi");
                return;
            }

            // 2. VALIDASI NO HP (kalau mau lebih aman)
            if (!txtNoHp.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "No HP hanya boleh angka");
                return;
            }

            // 3. BARU BUAT OBJECT
            Customer customer = new Customer(
                    DataStore.generateCustomerId(),
                    txtNama.getText(),
                    txtNoHp.getText(),
                    txtAlamat.getText());

            DataStore.customers.add(customer);

            DataStore.customerMap.put(
                    customer.getCustomerId(),
                    customer
            );

            Order order = new Order(
                    DataStore.generateOrderId(),
                    customer,
                    txtDeskripsi.getText(),
                    "Order Dibuat");

            DataStore.orders.add(order);

            JOptionPane.showMessageDialog(
                    this,
                    "Order berhasil dibuat!\nOrder ID : " + order.getOrderId());

            txtNama.setText("");
            txtNoHp.setText("");
            txtAlamat.setText("");
            txtDeskripsi.setText("");

            tampilkanData();

        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}
