package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import model.Admin;
import model.DataStore;
import model.Marketing;
import model.Produksi;
import model.User;

public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginFrame() {

        setTitle("PT Alexander");

        setSize(750, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ================= BACKGROUND =================
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(236, 240, 241));

        // ================= LEFT IMAGE =================
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(320, 420));
        leftPanel.setBackground(new Color(44, 62, 80));

        ImageIcon icon = new ImageIcon("src/assets/login2.png");
        JLabel lblImage = new JLabel();

        if (icon.getIconWidth() > 0) {
            Image img = icon.getImage().getScaledInstance(
                    320,
                    420,
                    Image.SCALE_SMOOTH
            );
            lblImage.setIcon(new ImageIcon(img));
        } else {
            lblImage.setText("PT ALEXANDER");
            lblImage.setForeground(Color.WHITE);
            lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        }

        leftPanel.add(lblImage, BorderLayout.CENTER);

        // ================= RIGHT PANEL =================
        JPanel rightPanel = new JPanel(new BorderLayout(0, 20));
        rightPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        rightPanel.setBackground(Color.WHITE);

        // TITLE
        JLabel lblTitle = new JLabel("LOGIN PT ALEXANDER");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setForeground(new Color(44, 62, 80));

        // ================= FORM =================
        JPanel formPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        formPanel.setBackground(Color.WHITE);

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();

        formPanel.add(new JLabel("Username"));
        formPanel.add(txtUsername);

        formPanel.add(new JLabel("Password"));
        formPanel.add(txtPassword);

        // ================= BUTTON =================
        JButton btnLogin = new JButton("Login");
        JButton btnCustomer = new JButton("Saya adalah Pelanggan");

        btnLogin.setBackground(new Color(41, 128, 185));
        btnLogin.setForeground(Color.WHITE);

        btnCustomer.setBackground(new Color(52, 73, 94));
        btnCustomer.setForeground(Color.WHITE);

        btnLogin.setFocusPainted(false);
        btnCustomer.setFocusPainted(false);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnCustomer);

        // ================= ENTER NAVIGATION =================
        txtUsername.addActionListener(e -> txtPassword.requestFocus());

        // ENTER TERAKHIR = LOGIN (sesuai request)
        txtPassword.addActionListener(e -> btnLogin.doClick());

        // ================= RIGHT LAYOUT =================
        rightPanel.add(lblTitle, BorderLayout.NORTH);
        rightPanel.add(formPanel, BorderLayout.CENTER);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        // ================= MAIN =================
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);

        // ================= LOGIN LOGIC (TIDAK DIUBAH) =================
        btnLogin.addActionListener(e -> {

            String username = txtUsername.getText();
            String password = String.valueOf(txtPassword.getPassword());

            boolean ditemukan = false;

            for (User user : DataStore.users) {

                if (user.login(username, password)) {

                    ditemukan = true;

                    dispose();

                    if (user instanceof Admin) {
                        new AdminDashboard().setVisible(true);
                    } else if (user instanceof Marketing) {
                        new MarketingDashboard().setVisible(true);
                    } else if (user instanceof Produksi) {
                        new ProduksiDashboard().setVisible(true);
                    }

                    break;
                }
            }

            if (!ditemukan) {
                JOptionPane.showMessageDialog(this, "Username atau Password salah!");
            }
        });

        // ================= CUSTOMER =================
        btnCustomer.addActionListener(e -> {
            dispose();
            new CustomerTrackingFrame().setVisible(true);
        });
    }
}