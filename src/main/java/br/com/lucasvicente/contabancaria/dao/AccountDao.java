package br.com.lucasvicente.contabancaria.dao;

import br.com.lucasvicente.contabancaria.database.DatabaseConnection;
import br.com.lucasvicente.contabancaria.database.DbException;
import br.com.lucasvicente.contabancaria.entites.Account;
import br.com.lucasvicente.contabancaria.entites.Bank;
import br.com.lucasvicente.contabancaria.entites.Person;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    private final Connection connection = DatabaseConnection.getConnection();


    public List<Account> findAll() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {

            String sql = "SELECT * FROM accounts ORDER BY person_id";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            List<Account> accounts = new ArrayList<>();

            while (resultSet.next()) {
                Account account = new Account();

                account.setId(resultSet.getLong("Id"));
                account.setAccountNumber(resultSet.getInt("accountNumber"));
                account.setAgency(resultSet.getString("agency"));
                account.setPassword(resultSet.getString("password"));
                account.setBalance(resultSet.getBigDecimal("balance"));

                Bank bank = new Bank();
                bank.setId(resultSet.getLong("bank_id"));
                account.setBank(bank);

                Person person = new Person();
                person.setId(resultSet.getLong("person_id"));
                account.setPerson(person);

                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(resultSet);
            DatabaseConnection.closeStatement(statement);
        }
    }

    public Account findById(Long id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet= null;
        try {

            String sql = "SELECT accounts.*," +
                    " banks.bankname as bank_name,"+
                    " people.username as person_name," +
                    " people.cpf as person_cpf," +
                    " FROM accounts" +
                    " INNER JOIN banks ON accounts.bank_id = banks.id" +
                    " INNER JOIN people ON accounts.person_id = people.id" +
                    " WHERE accounts.id = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getLong("Id"));
                account.setAccountNumber(resultSet.getInt("accountNumber"));
                account.setAgency(resultSet.getString("agency"));
                account.setPassword(resultSet.getString("password"));
                account.setBalance(resultSet.getBigDecimal("balance"));

                Bank bank = new Bank();
                bank.setId(resultSet.getLong("bank_id"));
                bank.setName(resultSet.getString("bankname"));
                account.setBank(bank);

                Person person = new Person();
                person.setId(resultSet.getLong("person_id"));
                person.setCpf(resultSet.getString("cpf"));
                person.setFullName(resultSet.getString("username"));
                account.setPerson(person);

                return account;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(resultSet);
            DatabaseConnection.closeStatement(preparedStatement);
        }
    }

    public Account insert(Account account) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO accounts " +
                            "(bank_id, person_id, password, balance, accountNumber, agency) " +
                            "VALUES " +
                            "(?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setLong(1, account.getBank().getId());
            preparedStatement.setLong(2, account.getPerson().getId());
            preparedStatement.setString(3, account.getPassword());
            preparedStatement.setBigDecimal(4, account.getBalance());
            preparedStatement.setInt(5, account.getAccountNumber());
            preparedStatement.setString(6, account.getAgency());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    Long id = resultSet.getLong(1);
                    account.setId(id);
                }
            }
            else {
                throw new DbException("Unexpected error! No rows affected!");
            }

            return account;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DatabaseConnection.closeStatement(preparedStatement);
        }
    }

    public void update(Account account) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE accounts "
                            + "SET password = ?"
                            + "WHERE Id = ?");

            preparedStatement.setString(1, account.getPassword());
            preparedStatement.setLong(2, account.getId());

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
            preparedStatement = connection.prepareStatement("DELETE FROM accounts WHERE Id = ?");

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(preparedStatement);
        }
    }

    public void deposit(Long accountId, BigDecimal value) {
        PreparedStatement preparedStatement = null;
        try {
            String sql = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(
                    "UPDATE accounts SET balance = balance + ? "
                        + "WHERE id = ?");

            preparedStatement.setBigDecimal(1, value);
            preparedStatement.setLong(2, accountId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(preparedStatement);
        }
    }

    public void withdraw(Long accountId, BigDecimal value) {
        PreparedStatement preparedStatement = null;
        try {
            String sql = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(
                    "UPDATE accounts SET balance = balance - ? "
                            + "WHERE id = ?");

            preparedStatement.setBigDecimal(1, value);
            preparedStatement.setLong(2, accountId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(preparedStatement);
        }
    }
}
