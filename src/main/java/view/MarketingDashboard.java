package view;

import model.*;

import javax.swing.*;
import java.awt.*;

public class MarketingDashboard extends JFrame {

    public MarketingDashboard() {

        setTitle("Marketing Dashboard");

        setSize(700, 400);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        JPanel panel = new JPanel(
                new GridLayout(5, 2, 10, 10)
        );

        JTextField txtNama
                = new JTextField();

        JTextField txtNoHp
                = new JTextField();

        JTextField txtAlamat
                = new JTextField();

        JTextField txtDeskripsi
                = new JTextField();

        JButton btnSimpan
                = new JButton("Simpan Order");

        JButton btnLogout
                = new JButton("Logout");

        panel.add(new JLabel("Nama Customer"));
        panel.add(txtNama);

        panel.add(new JLabel("No HP"));
        panel.add(txtNoHp);

        panel.add(new JLabel("Alamat"));
        panel.add(txtAlamat);

        panel.add(new JLabel("Deskripsi Pesanan"));
        panel.add(txtDeskripsi);

        panel.add(btnSimpan);
        panel.add(btnLogout);

        add(panel);

        btnSimpan.addActionListener(e -> {

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
                        "Order berhasil dibuat!\n"
                        + "Order ID : "
                        + order.getOrderId()
                );

                txtNama.setText("");
                txtNoHp.setText("");
                txtAlamat.setText("");
                txtDeskripsi.setText("");

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Data tidak valid!"
                );
            }
        });

        btnLogout.addActionListener(e -> {

            dispose();

            new LoginFrame().setVisible(true);

        });
    }

}
