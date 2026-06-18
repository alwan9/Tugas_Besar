package model;

public class Marketing extends User {
    @Override
    public String dashboardName() {
        return "Marketing Dashboard";
    }

    public Marketing(int userId,
            String nama,
            String username,
            String password) {

        super(
                userId,
                nama,
                username,
                password,
                "Marketing");
    }
}