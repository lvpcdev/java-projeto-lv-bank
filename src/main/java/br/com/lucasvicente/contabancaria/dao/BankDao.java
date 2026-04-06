package br.com.lucasvicente.contabancaria.dao;

import br.com.lucasvicente.contabancaria.database.DatabaseConnection;
import br.com.lucasvicente.contabancaria.database.DbException;
import br.com.lucasvicente.contabancaria.entites.Bank;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankDao {
    private final Connection connection = DatabaseConnection.getConnection();


    public List<Bank> findAll() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {

            String sql = "SELECT * FROM banks ORDER BY bankName";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery(sql);

            List<Bank> banks = new ArrayList<>();

            while (resultSet.next()) {
                Bank bank = new Bank();
                bank.setId(resultSet.getLong("name"));
                bank.setName(resultSet.getString("bankName"));
                banks.add(bank);
            }
            return banks;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(resultSet);
            DatabaseConnection.closeStatement(statement);
        }
    }

    public Bank findById(Long id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet= null;
        try {

            String sql = "SELECT * FROM banks WHERE id = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Bank bank = new Bank();
                bank.setId(resultSet.getLong("id"));
                bank.setName(resultSet.getString("bankName"));
                return bank;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(resultSet);
            DatabaseConnection.closeStatement(preparedStatement);
        }
    }

    public void insert(Bank bank) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO banks " +
                            "(bankName) " +
                            "VALUES " +
                            "(?)",
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, bank.getName());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    Long id = resultSet.getLong(1);
                    bank.setId(id);
                }
            }
            else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DatabaseConnection.closeStatement(preparedStatement);
        }
    }

    public void update(Bank bank) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE banks "
                            + "SET bankName = ?"
                            + "WHERE Id = ?");

            preparedStatement.setString(1, bank.getName());
            preparedStatement.setLong(2, bank.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(preparedStatement);
        }
    }

    public void deleteById(Long id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM banks WHERE Id = ?");

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(preparedStatement);
        }
    }
}
