package br.com.lucasvicente.contabancaria.dao;


import br.com.lucasvicente.contabancaria.database.DatabaseConnection;
import br.com.lucasvicente.contabancaria.database.DbException;
import br.com.lucasvicente.contabancaria.entites.Account;
import br.com.lucasvicente.contabancaria.entites.Person;
import br.com.lucasvicente.contabancaria.entites.PixKey;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PixKeyDao {

    private final Connection connection = DatabaseConnection.getConnection();


    public List<PixKey> findAll() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {

            String sql = "SELECT pix_keys.id AS pix_key_id, key_value, account_id, people.id AS person_id, username " +
                    "FROM pix_keys " +
                    "INNER JOIN accounts ON pix_keys.account_id = accounts.id " +
                    "INNER JOIN people ON accounts.person_id = people.id " +
                    "ORDER BY key_value";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            List<PixKey> pixKeys = new ArrayList<>();

            while (resultSet.next()) {
                PixKey pixKey = new PixKey();
                pixKey.setId(resultSet.getLong("pix_key_id"));
                pixKey.setKeyValue(resultSet.getString("key_value"));

                Account account = new Account();
                account.setId(resultSet.getLong("account_id"));

                Person person = new Person();
                person.setId(resultSet.getLong("person_id"));
                person.setFullName(resultSet.getString("username"));

                account.setPerson(person);

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

            String sql = "SELECT pix_keys.id AS pix_key_id, key_value, account_id, people.id AS person_id, username " +
                    "FROM pix_keys " +
                    "INNER JOIN accounts ON pix_keys.account_id = accounts.id " +
                    "INNER JOIN people ON accounts.person_id = people.id " +
                    "WHERE account_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            List<PixKey> pixKeys = new ArrayList<>();

            while (resultSet.next()) {
                PixKey pixKey = new PixKey();
                pixKey.setId(resultSet.getLong("pix_key_id"));
                pixKey.setKeyValue(resultSet.getString("key_value"));

                Account account = new Account();
                account.setId(resultSet.getLong("account_id"));

                Person person = new Person();
                person.setId(resultSet.getLong("person_id"));
                person.setFullName(resultSet.getString("username"));

                account.setPerson(person);

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

            String sql = "SELECT pix_keys.id AS pix_key_id, key_value, account_id, people.id AS person_id, username " +
                    "FROM pix_keys " +
                    "INNER JOIN accounts ON pix_keys.account_id = accounts.id " +
                    "INNER JOIN people ON accounts.person_id = people.id " +
                    "WHERE pix_keys.id = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                PixKey pixKey = new PixKey();
                pixKey.setId(resultSet.getLong("pix_key_id"));
                pixKey.setKeyValue(resultSet.getString("key_value"));

                Account account = new Account();
                account.setId(resultSet.getLong("account_id"));

                Person person = new Person();
                person.setId(resultSet.getLong("person_id"));
                person.setFullName(resultSet.getString("username"));

                account.setPerson(person);

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

    public PixKey update(PixKey pixKey) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE pix_keys "
                            + "SET key_value = ? "
                            + "WHERE Id = ?");

            preparedStatement.setString(1, pixKey.getKeyValue());
            preparedStatement.setLong(2, pixKey.getId());

            preparedStatement.executeUpdate();

            return pixKey;

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
