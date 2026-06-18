package view;

import model.*;

import javax.swing.*;
import java.awt.*;

public class CustomerTrackingFrame extends JFrame {

    private JTextField txtQR;
    private JTextArea area;

    public CustomerTrackingFrame() {

        setTitle("Tracking Pesanan");

                setExtendedState(JFrame.MAXIMIZED_BOTH); // FULLSCREEN
                setLocationRelativeTo(null); // center (opsional tapi aman)
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        txtQR = new JTextField(20);

        JButton btnCari
                = new JButton("Cari");

        JButton btnKembali
                = new JButton("Kembali");

        JPanel topPanel
                = new JPanel();

        topPanel.add(
                new JLabel("Kode QR")
        );

        topPanel.add(txtQR);

        topPanel.add(btnCari);

        topPanel.add(btnKembali);

        area = new JTextArea();

        area.setEditable(false);

        add(topPanel,
                BorderLayout.NORTH);

        add(new JScrollPane(area),
                BorderLayout.CENTER);

        btnCari.addActionListener(
                e -> cariPesanan()
        );

        btnKembali.addActionListener(e -> {

            dispose();

            new LoginFrame()
                    .setVisible(true);

        });
    }

    private void cariPesanan() {

        String kodeQR
                = txtQR.getText();

        area.setText("");

        for (WorkOrder wo
                : DataStore.workOrders) {

            if (wo.getQrCode() != null
                    && wo.getQrCode()
                            .getKode()
                            .equalsIgnoreCase(
                                    kodeQR
                            )) {

                area.append(
                        "===== TRACKING PESANAN =====\n\n"
                );

                area.append(
                        "Customer : "
                        + wo.getOrder()
                                .getCustomer()
                                .getNama()
                        + "\n"
                );

                area.append(
                        "No HP : "
                        + wo.getOrder()
                                .getCustomer()
                                .getNoHp()
                        + "\n"
                );

                area.append(
                        "Alamat : "
                        + wo.getOrder()
                                .getCustomer()
                                .getAlamat()
                        + "\n"
                );

                area.append(
                        "Deskripsi : "
                        + wo.getOrder()
                                .getDeskripsi()
                        + "\n"
                );

                area.append(
                        "QR : "
                        + wo.getQrCode()
                                .getKode()
                        + "\n\n"
                );

                area.append(
                        "===== PROGRESS =====\n"
                );

                for (String progress
                        : wo.getProgressList()) {

                    area.append(
                            "- "
                            + progress
                            + "\n"
                    );
                }

                area.append("\n");

                if (wo.getOrder()
                        .getInvoice()
                        != null) {

                    Invoice invoice
                            = wo.getOrder()
                                    .getInvoice();

                    area.append(
                            "===== NOTA =====\n\n"
                    );

                    area.append(
                            "Item : "
                            + invoice.getNamaItem()
                            + "\n"
                    );

                    area.append(
                            "Harga : Rp "
                            + invoice.getHarga()
                            + "\n"
                    );
                }

                return;
            }
        }

        area.setText(
                "QR Code tidak ditemukan."
        );
    }

}
