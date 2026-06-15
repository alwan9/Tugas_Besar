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
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel utama
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));

        // =========================
        // BAGIAN KIRI (GAMBAR)
        // =========================
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(300, 450));

        ImageIcon icon = new ImageIcon("src/assets/login2.png");
        JLabel lblImage = new JLabel();

        if (icon.getIconWidth() > 0) {
            Image img = icon.getImage().getScaledInstance(
                    300,
                    450,
                    Image.SCALE_SMOOTH
            );
            lblImage.setIcon(new ImageIcon(img));
        } else {
            lblImage.setText("LOGO PT ALEXANDER");
            lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        }

        leftPanel.add(lblImage);

        // =========================
        // BAGIAN KANAN (FORM)
        // =========================
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        rightPanel.setBackground(Color.WHITE);

        JLabel lblTitle =
                new JLabel("LOGIN PT ALEXANDER");

        lblTitle.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        lblTitle.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        JPanel formPanel = new JPanel(
                new GridLayout(6, 1, 10, 10)
        );

        formPanel.setBackground(Color.WHITE);

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();

        JButton btnLogin =
                new JButton("Login");

        JButton btnCustomer =
                new JButton("Saya adalah Pelanggan");

        btnLogin.setBackground(
                new Color(52, 152, 219)
        );

        btnLogin.setForeground(Color.WHITE);

        formPanel.add(new JLabel("Username"));
        formPanel.add(txtUsername);

        formPanel.add(new JLabel("Password"));
        formPanel.add(txtPassword);

        formPanel.add(btnLogin);
        formPanel.add(btnCustomer);

        rightPanel.add(lblTitle, BorderLayout.NORTH);
        rightPanel.add(formPanel, BorderLayout.CENTER);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);

        // =========================
        // LOGIN
        // =========================
        btnLogin.addActionListener(e -> {

            String username =
                    txtUsername.getText();

            String password =
                    String.valueOf(
                            txtPassword.getPassword()
                    );

            boolean ditemukan = false;

            for (User user : DataStore.users) {

                if (user.login(username, password)) {

                    ditemukan = true;

                    dispose();

                    if (user instanceof Admin) {

                        new AdminDashboard()
                                .setVisible(true);

                    } else if (user instanceof Marketing) {

                        new MarketingDashboard()
                                .setVisible(true);

                    } else if (user instanceof Produksi) {

                        new ProduksiDashboard()
                                .setVisible(true);

                    }

                    break;
                }
            }

            if (!ditemukan) {

                JOptionPane.showMessageDialog(
                        this,
                        "Username atau Password salah!"
                );
            }
        });

        // =========================
        // CUSTOMER
        // =========================
        btnCustomer.addActionListener(e -> {

            dispose();

            new CustomerTrackingFrame()
                    .setVisible(true);
        });
    }
}