package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoDB {
    private static final String URL = "jdbc:mysql://localhost:3306/agenda";
    private static final String USUARIO = "root";
    private static final String SENHA = "JaJa@8442";

    public static Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
