package br.com.lucasvicente.contabancaria.database;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseConnection {
    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    }

    public static void startDataBase() {
        String sql =
                "CREATE TABLE IF NOT EXISTS people ("
                + "Id BIGINT AUTO_INCREMENT PRIMARY KEY,"
                + "Username VARCHAR(255) NOT NULL,"
                + "Cpf VARCHAR(11) NOT NULL UNIQUE)";

        Statement statement = null;
        try {
            Connection connection = getConnection();
            statement = conn.createStatement();
            statement.execute(sql);
            System.out.println("Banco de dados H2 inicializado com sucesso!");
        } catch (SQLException e) {
            throw new DbException("Erro ao inicializar banco: " + e.getMessage());
        } finally {
            closeStatement(statement);
        }
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }


}
