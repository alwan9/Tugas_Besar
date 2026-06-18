package model;

public class Produksi extends User {

        @Override
        public String dashboardName() {
                return "Produksi Dashboard";
        }

        public Produksi(int userId,
                        String nama,
                        String username,
                        String password) {

                super(
                                userId,
                                nama,
                                username,
                                password,
                                "Produksi");
        }

}
