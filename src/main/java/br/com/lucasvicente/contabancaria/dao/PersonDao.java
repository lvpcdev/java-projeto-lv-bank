package br.com.lucasvicente.contabancaria.dao;

import br.com.lucasvicente.contabancaria.database.DatabaseConnection;
import br.com.lucasvicente.contabancaria.database.DbException;
import br.com.lucasvicente.contabancaria.entites.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDao {

    private final Connection connection = DatabaseConnection.getConnection();


    public List<Person> findAll() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {

            String sql = "SELECT * FROM people ORDER BY username";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            List<Person> people = new ArrayList<>();

            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getLong("Id"));
                person.setFullName(resultSet.getString("username"));
                person.setCpf(resultSet.getString("Cpf"));
                people.add(person);
            }
            return people;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(resultSet);
            DatabaseConnection.closeStatement(statement);
        }
    }

    public Person findById(Long id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet= null;
        try {

            String sql = "SELECT * FROM people WHERE id = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getLong("Id"));
                person.setFullName(resultSet.getString("username"));
                person.setCpf(resultSet.getString("Cpf"));
                return person;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(resultSet);
            DatabaseConnection.closeStatement(preparedStatement);
        }
    }

    public Person insert(Person person) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO people " +
                            "(username, cpf) " +
                            "VALUES " +
                            "(?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, person.getFullName());
            preparedStatement.setString(2, person.getCpf());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    Long id = resultSet.getLong(1);
                    person.setId(id);
                }
            }
            else {
                throw new DbException("Unexpected error! No rows affected!");
            }

            return person;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DatabaseConnection.closeStatement(preparedStatement);
        }
    }

    public Person update(Person person) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE people "
                            + "SET username = ?, cpf = ? "
                            + "WHERE Id = ?");

            preparedStatement.setString(1, person.getFullName());
            preparedStatement.setString(2, person.getCpf());
            preparedStatement.setLong(3, person.getId());

            preparedStatement.executeUpdate();

            return person;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(preparedStatement);
        }
    }

    public void deleteById(Long id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM people WHERE id = ?");

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(preparedStatement);
        }
    }


}
