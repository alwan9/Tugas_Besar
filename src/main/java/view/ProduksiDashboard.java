package view;

import model.*;

import javax.swing.*;
import java.awt.*;

public class ProduksiDashboard extends JFrame {

    private JPanel listPanel;
    private JScrollPane scroll;

    public ProduksiDashboard() {

        setTitle("Produksi Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ================= TOP PANEL =================
        JTextField txtSearch = new JTextField(25);

        JButton btnRefresh = new JButton("Refresh");
        JButton btnTambahProgress = new JButton("Tambah Progress");
        JButton btnEditProgress = new JButton("Edit Progress");
        JButton btnSelesai = new JButton("Selesaikan Pesanan");
        JButton btnLogout = new JButton("Logout");

        styleBtn(btnRefresh, new Color(52, 152, 219));
        styleBtn(btnTambahProgress, new Color(46, 204, 113));
        styleBtn(btnEditProgress, new Color(241, 196, 15));
        styleBtn(btnSelesai, new Color(155, 89, 182));
        styleBtn(btnLogout, new Color(231, 76, 60));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.add(new JLabel("Search WO / Nama / ID: "));
        topPanel.add(txtSearch);
        topPanel.add(btnRefresh);
        topPanel.add(btnTambahProgress);
        topPanel.add(btnEditProgress);
        topPanel.add(btnSelesai);
        topPanel.add(btnLogout);

        // ================= LIST PANEL (3 KOLOM) =================
        listPanel = new JPanel();
        listPanel.setLayout(new GridLayout(0, 3, 15, 15));
        listPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        listPanel.setBackground(new Color(245, 245, 245));

        scroll = new JScrollPane(listPanel);

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // ================= ACTION =================
        btnRefresh.addActionListener(e -> tampilkanData(txtSearch.getText()));
        btnTambahProgress.addActionListener(e -> updateStatus());
        btnEditProgress.addActionListener(e -> editProgress());
        btnSelesai.addActionListener(e -> selesaiProduksi());
        btnLogout.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        txtSearch.addActionListener(e -> tampilkanData(txtSearch.getText()));

        tampilkanData("");
    }

    // ================= UI LIST =================
    private void tampilkanData(String keyword) {

        listPanel.removeAll();

        for (WorkOrder wo : DataStore.workOrders) {

            String data = (
                    wo.getWoId() + " " +
                    wo.getOrder().getCustomer().getNama() + " " +
                    wo.getOrder().getDeskripsi()
            ).toLowerCase();

            if (keyword != null && !keyword.isBlank()) {
                if (!data.contains(keyword.toLowerCase())) continue;
            }

            // ================= CARD =================
            JPanel card = new JPanel();
            card.setLayout(new BorderLayout());
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            card.setPreferredSize(new Dimension(300, 180)); // ✅ TIDAK FULL HEIGHT

            // ================= INFO =================
            JTextArea info = new JTextArea(
                    "WO ID : " + wo.getWoId() + "\n" +
                    "Customer : " + wo.getOrder().getCustomer().getNama() + "\n" +
                    "Deskripsi : " + wo.getOrder().getDeskripsi() + "\n" +
                    "Status : " + wo.getStatus()
            );

            info.setEditable(false);
            info.setLineWrap(true);
            info.setWrapStyleWord(true);
            info.setBackground(Color.WHITE);

            // ================= PROGRESS =================
            StringBuilder sb = new StringBuilder("Progress:\n");
            for (String p : wo.getProgressList()) {
                sb.append("- ").append(p).append("\n");
            }

            JTextArea progressArea = new JTextArea(sb.toString());
            progressArea.setEditable(false);
            progressArea.setLineWrap(true);
            progressArea.setWrapStyleWord(true);
            progressArea.setBackground(Color.WHITE);

            JPanel center = new JPanel(new BorderLayout());
            center.setBackground(Color.WHITE);
            center.add(info, BorderLayout.NORTH);
            center.add(progressArea, BorderLayout.CENTER);

            // ================= BUTTON =================
            JButton btnHapus = new JButton("Hapus");
            JButton btnEdit = new JButton("Edit");

            styleBtn(btnHapus, new Color(231, 76, 60));
            styleBtn(btnEdit, new Color(241, 196, 15));

            JPanel btnPanel = new JPanel(new GridLayout(2, 1, 5, 5));
            btnPanel.setBackground(Color.WHITE);
            btnPanel.add(btnEdit);
            btnPanel.add(btnHapus);

            btnHapus.addActionListener(e -> {
                DataStore.workOrders.remove(wo);
                tampilkanData(keyword);
            });

            btnEdit.addActionListener(e -> editProgressByObject(wo));

            card.add(center, BorderLayout.CENTER);
            card.add(btnPanel, BorderLayout.EAST);

            listPanel.add(card);
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    // ================= STYLE =================
    private void styleBtn(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
    }

    // ================= LOGIC (TIDAK DIUBAH) =================
    private void updateStatus() {

        String inputWO = JOptionPane.showInputDialog(this, "Masukkan WO ID");
        if (inputWO == null) return;

        try {
            int id = Integer.parseInt(inputWO);

            for (WorkOrder wo : DataStore.workOrders) {
                if (wo.getWoId() == id) {

                    String p = JOptionPane.showInputDialog(this, "Progress baru");
                    if (p == null || p.isBlank()) return;

                    wo.tambahProgress(p);
                    wo.getOrder().setStatus(p);

                    tampilkanData("");
                    return;
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Input tidak valid");
        }
    }

    private void editProgress() {
        JOptionPane.showMessageDialog(this, "Gunakan tombol Edit di card");
    }

    private void editProgressByObject(WorkOrder wo) {

        if (wo.getProgressList().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Belum ada progress");
            return;
        }

        String p = (String) JOptionPane.showInputDialog(
                this,
                "Pilih Progress",
                "Edit Progress",
                JOptionPane.PLAIN_MESSAGE,
                null,
                wo.getProgressList().toArray(),
                wo.getProgressList().get(0)
        );

        if (p == null) return;

        String newP = JOptionPane.showInputDialog(this, "Edit progress", p);
        if (newP == null || newP.isBlank()) return;

        int idx = wo.getProgressList().indexOf(p);
        wo.getProgressList().set(idx, newP);

        tampilkanData("");
    }

    private void selesaiProduksi() {

        String inputWO = JOptionPane.showInputDialog(this, "Masukkan WO ID");
        if (inputWO == null) return;

        try {
            int id = Integer.parseInt(inputWO);

            for (WorkOrder wo : DataStore.workOrders) {
                if (wo.getWoId() == id) {

                    wo.tambahProgress("QC");
                    wo.setStatus("QC");
                    wo.getOrder().setStatus("QC");

                    tampilkanData("");
                    return;
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Input tidak valid");
        }
    }
}