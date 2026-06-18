package view;

import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MarketingDashboard extends JFrame {

    public MarketingDashboard() {

        setTitle("Marketing Dashboard");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ================= MAIN WRAPPER =================
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        // ================= CARD =================
        JPanel card = new JPanel(new BorderLayout(0, 15));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(20, 20, 20, 20));
        card.setPreferredSize(new Dimension(420, 300));

        // ================= FORM =================
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBackground(Color.WHITE);

        JTextField txtNama = new JTextField();
        JTextField txtNoHp = new JTextField();
        JTextField txtAlamat = new JTextField();
        JTextField txtDeskripsi = new JTextField();

        panel.add(new JLabel("Nama Customer"));
        panel.add(txtNama);

        panel.add(new JLabel("No HP"));
        panel.add(txtNoHp);

        panel.add(new JLabel("Alamat"));
        panel.add(txtAlamat);

        panel.add(new JLabel("Deskripsi Pesanan"));
        panel.add(txtDeskripsi);

        // ================= BUTTONS =================
        JButton btnSimpan = new JButton("Simpan Order");
        JButton btnLogout = new JButton("Logout");

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnSimpan);
        buttonPanel.add(btnLogout);

        // ================= ENTER NAVIGATION =================
        txtNama.addActionListener(e -> txtNoHp.requestFocus());
        txtNoHp.addActionListener(e -> txtAlamat.requestFocus());
        txtAlamat.addActionListener(e -> txtDeskripsi.requestFocus());
        txtDeskripsi.addActionListener(e -> btnSimpan.doClick()); // ENTER = submit

        // ================= STYLE =================
        btnSimpan.setBackground(new Color(52, 152, 219));
        btnSimpan.setForeground(Color.WHITE);
        btnSimpan.setFocusPainted(false);

        btnLogout.setBackground(new Color(231, 76, 60));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);

        // ================= ADD =================
        card.add(panel, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(card);
        add(mainPanel);

        // ================= LOGIC + VALIDATION =================
        btnSimpan.addActionListener(e -> {

            String nama = txtNama.getText().trim();
            String noHp = txtNoHp.getText().trim();
            String alamat = txtAlamat.getText().trim();
            String deskripsi = txtDeskripsi.getText().trim();

            // 1. kosong check
            if (nama.isBlank() || noHp.isBlank() || alamat.isBlank() || deskripsi.isBlank()) {
                JOptionPane.showMessageDialog(this, "Semua field wajib diisi!");
                return;
            }

            // 2. validasi nama (huruf + spasi)
            if (!nama.matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(this, "Nama hanya boleh huruf!");
                return;
            }

            // 3. validasi no hp (angka)
            if (!noHp.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "No HP hanya boleh angka!");
                return;
            }

            // 4. validasi alamat (huruf + angka + spasi)
            if (!alamat.matches("[a-zA-Z0-9\\s,.\\-]+")) {
                JOptionPane.showMessageDialog(this, "Alamat mengandung karakter tidak valid!");
                return;
            }

            // 5. validasi deskripsi (huruf + angka + spasi)
            if (!deskripsi.matches("[a-zA-Z0-9\\s,.\\-]+")) {
                JOptionPane.showMessageDialog(this, "Deskripsi mengandung karakter tidak valid!");
                return;
            }

            try {

                Customer customer = new Customer(
                        DataStore.generateCustomerId(),
                        nama,
                        noHp,
                        alamat
                );

                DataStore.customers.add(customer);

                Order order = new Order(
                        DataStore.generateOrderId(),
                        customer,
                        deskripsi,
                        "Order Dibuat"
                );

                DataStore.orders.add(order);

                JOptionPane.showMessageDialog(
                        this,
                        "Order berhasil dibuat!\nOrder ID : " + order.getOrderId()
                );

                txtNama.setText("");
                txtNoHp.setText("");
                txtAlamat.setText("");
                txtDeskripsi.setText("");
                txtNama.requestFocus();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan sistem!");
            }
        });

        btnLogout.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
    }
}