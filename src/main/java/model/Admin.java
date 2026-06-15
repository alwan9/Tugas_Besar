package model;

public class Admin extends User {

    public Admin(int userId,
                 String nama,
                 String username,
                 String password) {

        super(
                userId,
                nama,
                username,
                password,
                "Admin"
        );
    }
}