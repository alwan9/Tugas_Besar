package model;

public class Marketing extends User {

    public Marketing(int userId,
                     String nama,
                     String username,
                     String password) {

        super(
                userId,
                nama,
                username,
                password,
                "Marketing"
        );
    }
}