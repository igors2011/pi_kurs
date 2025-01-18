package com.ip.pi_kurs.dao;

import com.ip.pi_kurs.DBConnection;
import com.ip.pi_kurs.models.FixedCost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class FixedCostsAccess {
    @Autowired
    private DBConnection dbConnection;

    private FixedCost resultSetToFixedCost(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        double cost = resultSet.getDouble("cost");
        Timestamp period = resultSet.getTimestamp("period");
        FixedCost result = new FixedCost();
        result.setId(id);
        result.setName(name);
        result.setCost(cost);
        result.setPeriod(period);
        return result;
    }

    public List<FixedCost> getFixedCosts() throws SQLException, IOException {
        List<FixedCost> result = new ArrayList<>();
        Connection connection = dbConnection.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM fixed_cost ORDER BY id";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            FixedCost fixedCost = resultSetToFixedCost(resultSet);
            result.add(fixedCost);
        }
        return result;
    }

    private PreparedStatement prepareFixedCostForCreate(Connection connection, FixedCost fixedCost) throws SQLException {
        String query = "INSERT INTO fixed_cost (name, cost, period) VALUES (?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, fixedCost.getName());
        statement.setDouble(2, fixedCost.getCost());
        statement.setTimestamp(3, fixedCost.getPeriod());
        return statement;
    }

    public void createFixedCost(FixedCost fixedCost) throws SQLException, IOException {
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedFixedCost = prepareFixedCostForCreate(connection, fixedCost);
        preparedFixedCost.executeUpdate();
        connection.close();
    }
}
