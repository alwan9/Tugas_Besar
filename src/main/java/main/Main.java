package main;

import javax.swing.SwingUtilities;
import model.DataStore;
import view.LoginFrame;
import model.User;
import model.Admin;

public class Main {

    public static void main(String[] args) {

        DataStore.initData();

        User user = new Admin(
                1,
                "Admin",
                "admin",
                "123"
        );

        Admin admin = (Admin) user;

        System.out.println(admin.dashboardName());

        SwingUtilities.invokeLater(() -> {

            new LoginFrame().setVisible(true);

        });

    }
}
