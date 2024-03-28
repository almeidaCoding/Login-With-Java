import Interfaces.FrameHomePage;
import database.GestionDB;

import javax.swing.*;
import java.sql.Connection;

public class MainUserRegister {
    public static void main(String[] args) {
        //Conexión con la BD
        Connection connection = GestionDB.getConnection();

        //Instancia del Frame
        FrameHomePage frameHomePage = new FrameHomePage();
        frameHomePage.setVisible(true);
        frameHomePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}