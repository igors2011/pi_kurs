package com.ip.pi_kurs.dao;

import com.ip.pi_kurs.DBConnection;
import com.ip.pi_kurs.models.Material;
import com.ip.pi_kurs.models.MaterialCost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MaterialAccess {
    @Autowired
    private DBConnection dbConnection;

    private Material resultSetToMaterial(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        Material result = new Material();
        result.setId(id);
        result.setName(name);
        return result;
    }

    public List<Material> getMaterials() throws SQLException, IOException {
        List<Material> result = new ArrayList<>();
        Connection connection = dbConnection.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM material ORDER BY id";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            Material material = resultSetToMaterial(resultSet);
            result.add(material);
        }
        return result;
    }

    public Material getMaterialById(int id) throws SQLException, IOException {
        Connection connection = dbConnection.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM material WHERE id = " + id + ";";
        ResultSet resultSet = statement.executeQuery(query);
        Material result = new Material();
        while (resultSet.next()) {
            result = resultSetToMaterial(resultSet);
        }
        connection.close();
        return result;
    }

    private PreparedStatement prepareMaterialForCreate(Connection connection, Material material) throws SQLException {
        String query = "INSERT INTO material (name) VALUES (?);";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, material.getName());
        return statement;
    }

    public void createMaterial(Material material) throws SQLException, IOException {
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedMaterial = prepareMaterialForCreate(connection, material);
        preparedMaterial.executeUpdate();
        connection.close();
    }

    private PreparedStatement prepareMaterielForUpdate(Connection connection, Material material) throws SQLException  {
        String query = "UPDATE material SET name = ? WHERE id = " + material.getId() + ";";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, material.getName());
        return statement;
    }

    public void updateMaterial(Material material) throws SQLException, IOException {
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedMaterial = prepareMaterielForUpdate(connection, material);
        preparedMaterial.executeUpdate();
        connection.close();
    }

    private MaterialCost resultSetToMaterialCost(ResultSet resultSet) throws SQLException {
        MaterialCost result = new MaterialCost();
        result.setId(resultSet.getInt("id"));
        result.setMaterialId(resultSet.getInt("material_id"));
        result.setCost(resultSet.getDouble("cost"));
        result.setPeriod(resultSet.getTimestamp("period"));
        return result;
    }

    public List<MaterialCost> getMaterialCosts(int materialId) throws SQLException, IOException {
        Connection connection = dbConnection.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM material_cost WHERE material_id = " + materialId + ";";
        ResultSet resultSet = statement.executeQuery(query);
        List<MaterialCost> result = new ArrayList<>();
        while (resultSet.next()) {
            MaterialCost materialCost = resultSetToMaterialCost(resultSet);
            materialCost.convertPeriodToPeriodString();
            result.add(materialCost);
        }
        connection.close();
        return result;
    }

    private PreparedStatement prepareMaterialCostForCreate(Connection connection, MaterialCost materialCost) throws SQLException {
        String query = "INSERT INTO material_cost (material_id, cost, period) VALUES (?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, materialCost.getMaterialId());
        statement.setDouble(2, materialCost.getCost());
        statement.setTimestamp(3, materialCost.getPeriod());
        return statement;
    }

    public void createMaterialCost(MaterialCost materialCost) throws SQLException, IOException {
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedMaterialCost = prepareMaterialCostForCreate(connection, materialCost);
        preparedMaterialCost.executeUpdate();
        connection.close();
    }
}
