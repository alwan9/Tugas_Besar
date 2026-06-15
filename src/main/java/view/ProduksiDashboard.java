package view;

import model.*;

import javax.swing.*;
import java.awt.*;

public class ProduksiDashboard extends JFrame {

    private JTextArea area;

    public ProduksiDashboard() {

        setTitle("Produksi Dashboard");

        setSize(800, 600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        area = new JTextArea();

        area.setEditable(false);

        JButton btnRefresh
                = new JButton("Refresh");

        JButton btnTambahProgress
                = new JButton("Tambah Progress");

        JButton btnEditProgress
                = new JButton("Edit Progress");

        JButton btnSelesai
                = new JButton("Selesaikan Pesanan");

        JButton btnLogout
                = new JButton("Logout");

        JPanel topPanel
                = new JPanel();

        topPanel.add(btnRefresh);
        topPanel.add(btnTambahProgress);
        topPanel.add(btnEditProgress);
        topPanel.add(btnSelesai);
        topPanel.add(btnLogout);

        add(topPanel,
                BorderLayout.NORTH);

        add(new JScrollPane(area),
                BorderLayout.CENTER);

        btnRefresh.addActionListener(
                e -> tampilkanData()
        );

        btnTambahProgress.addActionListener(
                e -> updateStatus()
        );

        btnEditProgress.addActionListener(
                e -> editProgress()
        );

        btnSelesai.addActionListener(
                e -> selesaiProduksi()
        );

        btnLogout.addActionListener(e -> {

            dispose();

            new LoginFrame().setVisible(true);

        });

        tampilkanData();
    }

    private void tampilkanData() {

        area.setText("");

        area.append(
                "===== WORK ORDER =====\n\n"
        );

        for (WorkOrder wo
                : DataStore.workOrders) {

            area.append(
                    "WO ID : "
                    + wo.getWoId()
                    + "\n"
            );

            area.append(
                    "Customer : "
                    + wo.getOrder()
                            .getCustomer()
                            .getNama()
                    + "\n"
            );

            area.append(
                    "Deskripsi : "
                    + wo.getOrder()
                            .getDeskripsi()
                    + "\n"
            );

            area.append("Progress : \n");

            for (String progress
                    : wo.getProgressList()) {

                area.append(
                        "- "
                        + progress
                        + "\n"
                );

            }

            if (wo.getQrCode() != null) {

                area.append(
                        "QR : "
                        + wo.getQrCode()
                                .getKode()
                        + "\n"
                );
            }

            area.append("\n");
        }
        
        
    }

    private void updateStatus() {

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
                    = Integer.parseInt(inputWO);

            for (WorkOrder wo
                    : DataStore.workOrders) {

                if (wo.getWoId() == woId) {

                    String progres
                            = JOptionPane.showInputDialog(
                                    this,
                                    "Masukkan progres produksi"
                            );

                    if (progres == null
                            || progres.trim().isEmpty()) {

                        return;
                    }

                    wo.tambahProgress(progres);

                    wo.getOrder()
                            .setStatus(progres);

                    JOptionPane.showMessageDialog(
                            this,
                            "Progres berhasil diperbarui"
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

    private void editProgress() {

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
                    = Integer.parseInt(inputWO);

            WorkOrder target = null;

            for (WorkOrder wo
                    : DataStore.workOrders) {

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

            if (target.getProgressList().isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Belum ada progress"
                );

                return;
            }

            String progressDipilih
                    = (String) JOptionPane.showInputDialog(
                            this,
                            "Pilih Progress",
                            "Edit Progress",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            target.getProgressList().toArray(),
                            target.getProgressList().get(0)
                    );

            if (progressDipilih == null) {
                return;
            }

            String[] aksi = {
                "Ubah",
                "Hapus"
            };

            String pilihan
                    = (String) JOptionPane.showInputDialog(
                            this,
                            "Pilih Aksi",
                            "Edit Progress",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            aksi,
                            aksi[0]
                    );

            if (pilihan == null) {
                return;
            }

            int index
                    = target.getProgressList()
                            .indexOf(progressDipilih);

            if (pilihan.equals("Ubah")) {

                String progressBaru
                        = JOptionPane.showInputDialog(
                                this,
                                "Progress Baru",
                                progressDipilih
                        );

                if (progressBaru == null
                        || progressBaru.trim().isEmpty()) {

                    return;
                }

                target.getProgressList()
                        .set(index,
                                progressBaru);

                target.setStatus(
                        progressBaru
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Progress berhasil diubah"
                );
            } else {

                target.getProgressList()
                        .remove(index);

                if (!target.getProgressList().isEmpty()) {

                    target.setStatus(
                            target.getProgressList()
                                    .get(
                                            target.getProgressList().size() - 1
                                    )
                    );
                }

                JOptionPane.showMessageDialog(
                        this,
                        "Progress berhasil dihapus"
                );
            }

            tampilkanData();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Input tidak valid"
            );
        }

    }

    private void selesaiProduksi() {

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
                    = Integer.parseInt(inputWO);

            for (WorkOrder wo
                    : DataStore.workOrders) {

                if (wo.getWoId() == woId) {

                    wo.tambahProgress("QC");

                    wo.setStatus("QC");

                    wo.getOrder()
                            .setStatus("QC");

                    JOptionPane.showMessageDialog(
                            this,
                            "Pesanan berhasil diselesaikan dan dikirim ke QC"
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

}
