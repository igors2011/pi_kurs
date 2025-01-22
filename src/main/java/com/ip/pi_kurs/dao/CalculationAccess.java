package com.ip.pi_kurs.dao;

import com.ip.pi_kurs.DBConnection;
import com.ip.pi_kurs.models.FixedCost;
import com.ip.pi_kurs.models.Product;
import com.ip.pi_kurs.models.Production;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CalculationAccess {
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

    public double getTotalFixedCostsForMonth(Timestamp startDate, Timestamp endDate) throws SQLException, IOException {
        double result = 0;
        Connection connection = dbConnection.getConnection();

        String query = "SELECT * FROM fixed_cost WHERE period >= ? AND period <= ? ORDER BY id";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setTimestamp(1, startDate);
        preparedStatement.setTimestamp(2, endDate);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<FixedCost> fixedCosts = new ArrayList<>();
        while (resultSet.next()) {
            FixedCost fixedCost = resultSetToFixedCost(resultSet);
            fixedCosts.add(fixedCost);
        }
        for (FixedCost el : fixedCosts) {
            result += el.getCost();
        }
        return result;
    }

    private Production resultSetToProduction(ResultSet resultSet) throws SQLException {
        Production result = new Production();
        result.setId(resultSet.getInt("id"));
        result.setProductId(resultSet.getInt("product_id"));
        result.setNumber(resultSet.getInt("number"));
        result.setPeriod(resultSet.getTimestamp("period"));
        return result;
    }

    public int getTotalProductionForMonth(Timestamp startDate, Timestamp endDate) throws SQLException, IOException {
        int result = 0;
        Connection connection = dbConnection.getConnection();

        String query = "SELECT * FROM production WHERE period >= ? AND period <= ? ORDER BY id";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setTimestamp(1, startDate);
        preparedStatement.setTimestamp(2, endDate);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Production> productions = new ArrayList<>();
        while (resultSet.next()) {
            Production production = resultSetToProduction(resultSet);
            productions.add(production);
        }
        for (Production el : productions) {
            result += el.getNumber();
        }
        return result;
    }

    public int getProductionForMonthForProduct(Timestamp startDate, Timestamp endDate, int productId) throws SQLException, IOException {
        int result = 0;
        Connection connection = dbConnection.getConnection();

        String query = "SELECT * FROM production WHERE period >= ? AND period <= ? AND product_id = " + productId + " ORDER BY id";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setTimestamp(1, startDate);
        preparedStatement.setTimestamp(2, endDate);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Production> productions = new ArrayList<>();
        while (resultSet.next()) {
            Production production = resultSetToProduction(resultSet);
            productions.add(production);
        }
        for (Production el : productions) {
            result += el.getNumber();
        }
        return result;
    }

}
