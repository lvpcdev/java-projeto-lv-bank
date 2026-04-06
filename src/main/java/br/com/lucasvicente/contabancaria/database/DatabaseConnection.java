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
        String people =
                "CREATE TABLE IF NOT EXISTS people ("
                + "id BIGINT AUTO_INCREMENT PRIMARY KEY,"
                + "username VARCHAR(255) NOT NULL,"
                + "cpf VARCHAR(11) NOT NULL UNIQUE);";
        String bank =
                "CREATE TABLE IF NOT EXISTS banks ("
                + "id BIGINT AUTO_INCREMENT PRIMARY KEY,"
                + "bankName VARCHAR(255) NOT NULL);";
        String account =
                "CREATE TABLE IF NOT EXISTS accounts ("
                + "id BIGINT AUTO_INCREMENT PRIMARY KEY,"
                + "bank_id BIGINT NOT NULL,"
                + "person_id BIGINT NOT NULL,"
                + "password VARCHAR(50) NOT NULL,"
                + "accountNumber BIGINT NOT NULL UNIQUE,"
                + "agency VARCHAR(10) NOT NULL,"
                + "balance DECIMAL(15, 2) NOT NULL DEFAULT 0.00,"

                + "FOREIGN KEY (bank_id) REFERENCES banks(id),"
                + "FOREIGN KEY (person_id) REFERENCES people(id));";

        Statement statement = null;
        try {
            Connection connection = getConnection();
            statement = conn.createStatement();
            statement.execute(people);
            statement.execute(bank);
            statement.execute(account);
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
