package main;

import model.Admin;
import model.User;

public class OOPDemo {

    public static void main(String[] args) {

        User user = new Admin(
                1,
                "Admin",
                "admin",
                "123"
        );

        Admin admin = (Admin) user;

        System.out.println(
                admin.dashboardName()
        );
    }
}