package br.com.lucasvicente.contabancaria.dao;


import br.com.lucasvicente.contabancaria.database.DatabaseConnection;
import br.com.lucasvicente.contabancaria.database.DbException;
import br.com.lucasvicente.contabancaria.entites.Account;
import br.com.lucasvicente.contabancaria.entites.PixKey;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PixKeyDao {

    private final Connection connection = DatabaseConnection.getConnection();


    public List<PixKey> findAll() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {

            String sql = "SELECT * FROM pix_keys ORDER BY key_value";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery(sql);

            List<PixKey> pixKeys = new ArrayList<>();

            while (resultSet.next()) {
                PixKey pixKey = new PixKey();
                pixKey.setId(resultSet.getLong("Id"));
                pixKey.setKeyValue(resultSet.getString("key_value"));

                Account account = new Account();
                account.setId(resultSet.getLong("account_id"));
                pixKey.setAccount(account);

                pixKeys.add(pixKey);
            }
            return pixKeys;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(resultSet);
            DatabaseConnection.closeStatement(statement);
        }
    }

    public List<PixKey> findAllByAccountId(Long id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet= null;
        try {

            String sql = "SELECT * FROM pix_keys WHERE account_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            List<PixKey> pixKeys = new ArrayList<>();

            while (resultSet.next()) {
                PixKey pixKey = new PixKey();
                pixKey.setId(resultSet.getLong("Id"));
                pixKey.setKeyValue(resultSet.getString("key_value"));

                Account account = new Account();
                account.setId(resultSet.getLong("account_id"));
                pixKey.setAccount(account);

                pixKeys.add(pixKey);
            }
            return pixKeys;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(resultSet);
            DatabaseConnection.closeStatement(preparedStatement);
        }
    }

    public PixKey findById(Long id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet= null;
        try {

            String sql = "SELECT * FROM pix_keys WHERE id = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                PixKey pixKey = new PixKey();
                pixKey.setId(resultSet.getLong("Id"));
                pixKey.setKeyValue(resultSet.getString("key_value"));

                Account account = new Account();
                account.setId(resultSet.getLong("account_id"));
                pixKey.setAccount(account);

                return pixKey;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(resultSet);
            DatabaseConnection.closeStatement(preparedStatement);
        }
    }

    public PixKey insert(PixKey pixKey) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO pix_keys " +
                            "(key_value, account_id) " +
                            "VALUES " +
                            "(?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, pixKey.getKeyValue());
            preparedStatement.setLong(2, pixKey.getAccount().getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    Long id = resultSet.getLong(1);
                    pixKey.setId(id);
                }
            }
            else {
                throw new DbException("Unexpected error! No rows affected!");
            }

            return pixKey;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DatabaseConnection.closeStatement(preparedStatement);
        }
    }

    public void update(PixKey pixKey) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE pix_keys "
                            + "SET key_value = ?"
                            + "WHERE Id = ?");

            preparedStatement.setString(1, pixKey.getKeyValue());
            preparedStatement.setLong(2, pixKey.getId());

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
            preparedStatement = connection.prepareStatement("DELETE FROM pix_keys WHERE id = ?");

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(preparedStatement);
        }
    }


}
