package com.ip.pi_kurs.dao;

import com.ip.pi_kurs.DBConnection;
import com.ip.pi_kurs.models.Salary;
import com.ip.pi_kurs.models.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class WorkerAccess {
    @Autowired
    private DBConnection dbConnection;

    private Worker resultSetToWorker(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        Worker result = new Worker();
        result.setId(id);
        result.setName(name);
        return result;
    }

    public List<Worker> getWorkers() throws SQLException, IOException {
        List<Worker> result = new ArrayList<>();
        Connection connection = dbConnection.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM worker ORDER BY id";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            Worker worker = resultSetToWorker(resultSet);
            result.add(worker);
        }
        return result;
    }

    public Worker getWorkerById(int id) throws SQLException, IOException {
        Connection connection = dbConnection.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM worker WHERE id = " + id + ";";
        ResultSet resultSet = statement.executeQuery(query);
        Worker result = new Worker();
        while (resultSet.next()) {
            result = resultSetToWorker(resultSet);
        }
        connection.close();
        return result;
    }

    private PreparedStatement prepareWorkerForCreate(Connection connection, Worker worker) throws SQLException {
        String query = "INSERT INTO worker (name) VALUES (?);";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, worker.getName());
        return statement;
    }

    public void createWorker(Worker worker) throws SQLException, IOException {
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedWorker = prepareWorkerForCreate(connection, worker);
        preparedWorker.executeUpdate();
        connection.close();
    }

    private PreparedStatement prepareWorkerForUpdate(Connection connection, Worker worker) throws SQLException  {
        String query = "UPDATE worker SET name = ? WHERE id = " + worker.getId() + ";";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, worker.getName());
        return statement;
    }

    public void updateWorker(Worker worker) throws SQLException, IOException {
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedWorker = prepareWorkerForUpdate(connection, worker);
        preparedWorker.executeUpdate();
        connection.close();
    }

    private Salary resultSetToSalary(ResultSet resultSet) throws SQLException {
        Salary result = new Salary();
        result.setId(resultSet.getInt("id"));
        result.setWorkerId(resultSet.getInt("worker_id"));
        result.setSalary(resultSet.getDouble("salary"));
        result.setPeriod(resultSet.getTimestamp("period"));
        return result;
    }

    public List<Salary> getWorkerSalaries(int workerId) throws SQLException, IOException {
        Connection connection = dbConnection.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM worker_salary WHERE worker_id = " + workerId + ";";
        ResultSet resultSet = statement.executeQuery(query);
        List<Salary> result = new ArrayList<>();
        while (resultSet.next()) {
            Salary salary = resultSetToSalary(resultSet);
            salary.convertPeriodToPeriodString();
            result.add(salary);
        }
        connection.close();
        return result;
    }

    private PreparedStatement prepareSalaryForCreate(Connection connection, Salary salary) throws SQLException {
        String query = "INSERT INTO worker_salary (worker_id, salary, period) VALUES (?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, salary.getWorkerId());
        statement.setDouble(2, salary.getSalary());
        statement.setTimestamp(3, salary.getPeriod());
        return statement;
    }

    public void createSalary(Salary salary) throws SQLException, IOException {
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedSalary = prepareSalaryForCreate(connection, salary);
        preparedSalary.executeUpdate();
        connection.close();
    }
}
