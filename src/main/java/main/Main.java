package main;

import javax.swing.SwingUtilities;
import model.DataStore;
import view.LoginFrame;

public class Main {

    public static void main(String[] args) {

        DataStore.initData();

        SwingUtilities.invokeLater(() -> {

            new LoginFrame().setVisible(true);

        });
    }
}