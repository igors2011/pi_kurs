package com.ip.pi_kurs.dao;

import com.ip.pi_kurs.DBConnection;
import com.ip.pi_kurs.models.MaterialByProduct;
import com.ip.pi_kurs.models.Product;
import com.ip.pi_kurs.models.Production;
import com.ip.pi_kurs.models.WorkerByProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductAccess {
    @Autowired
    private DBConnection dbConnection;

    private Product resultSetToProduct(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        Product result = new Product();
        result.setId(id);
        result.setName(name);
        return result;
    }

    public List<Product> getProducts() throws SQLException, IOException {
        List<Product> result = new ArrayList<>();
        Connection connection = dbConnection.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM product ORDER BY id";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            Product product = resultSetToProduct(resultSet);
            result.add(product);
        }
        return result;
    }

    public Product getProductById(int id) throws SQLException, IOException {
        Connection connection = dbConnection.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM product WHERE id = " + id + ";";
        ResultSet resultSet = statement.executeQuery(query);
        Product result = new Product();
        while (resultSet.next()) {
            result = resultSetToProduct(resultSet);
        }
        connection.close();
        return result;
    }

    private PreparedStatement prepareProductForCreate(Connection connection, Product product) throws SQLException {
        String query = "INSERT INTO product (name) VALUES (?);";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, product.getName());
        return statement;
    }

    public void createProduct(Product product) throws SQLException, IOException {
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedWorker = prepareProductForCreate(connection, product);
        preparedWorker.executeUpdate();
        connection.close();
    }

    private PreparedStatement prepareProductForUpdate(Connection connection, Product product) throws SQLException {
        String query = "UPDATE product SET name = ? WHERE id = " + product.getId() + ";";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, product.getName());
        return statement;
    }

    public void updateProduct(Product product) throws SQLException, IOException {
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedWorker = prepareProductForUpdate(connection, product);
        preparedWorker.executeUpdate();
        connection.close();
    }

    private WorkerByProduct resultSetToWorkerByProduct(ResultSet resultSet) throws SQLException {
        WorkerByProduct result = new WorkerByProduct();
        result.setId(resultSet.getInt("id"));
        result.setWorkerId(resultSet.getInt("worker_id"));
        result.setWorkerName(resultSet.getString("worker_name"));
        result.setPeriod(resultSet.getTimestamp("period"));
        return result;
    }

    public List<WorkerByProduct> getWorkersByProductId(int productId) throws SQLException, IOException {
        Connection connection = dbConnection.getConnection();
        Statement statement = connection.createStatement();
        String query =
                "SELECT worker_product.id, worker_product.worker_id, worker.name AS worker_name, worker_product.period " +
                        "FROM worker_product JOIN worker " +
                        "ON worker_product.worker_id = worker.id " +
                        "WHERE product_id = " + productId + " " +
                        "ORDER BY worker_product.worker_id";
        List<WorkerByProduct> result = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            WorkerByProduct workerByProduct = resultSetToWorkerByProduct(resultSet);
            workerByProduct.convertPeriodToPeriodString();
            result.add(workerByProduct);
        }
        return result;
    }

    private PreparedStatement prepareWorkerByProductForCreate(Connection connection, WorkerByProduct workerByProduct) throws SQLException {
        String query = "INSERT INTO worker_product (worker_id, product_id, period) VALUES (?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, workerByProduct.getWorkerId());
        statement.setInt(2, workerByProduct.getProductId());
        statement.setTimestamp(3, workerByProduct.getPeriod());
        return statement;
    }

    public void createWorkerByProduct(WorkerByProduct workerByProduct) throws SQLException, IOException {
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedWorkerByProduct = prepareWorkerByProductForCreate(connection, workerByProduct);
        preparedWorkerByProduct.executeUpdate();
        connection.close();
    }

    private PreparedStatement prepareWorkerByProductForDelete(Connection connection, int id) throws SQLException {
        return connection.prepareStatement("DELETE FROM worker_product where id = " + id + ";");
    }

    public void deleteWorkerByProductById(int id) throws SQLException, IOException {
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedWorkerByProductForDelete = prepareWorkerByProductForDelete(connection, id);
        preparedWorkerByProductForDelete.executeUpdate();
    }

    private MaterialByProduct resultSetToMaterialByProduct(ResultSet resultSet) throws SQLException {
        MaterialByProduct result = new MaterialByProduct();
        result.setId(resultSet.getInt("id"));
        result.setMaterialId(resultSet.getInt("material_id"));
        result.setMaterialName(resultSet.getString("material_name"));
        result.setNumber(resultSet.getInt("number"));
        result.setPeriod(resultSet.getTimestamp("period"));
        return result;
    }

    public List<MaterialByProduct> getMaterialsByProductId(int productId) throws SQLException, IOException {
        Connection connection = dbConnection.getConnection();
        Statement statement = connection.createStatement();
        String query =
                "SELECT product_material.id, product_material.material_id, material.name AS material_name, product_material.number, product_material.period " +
                        "FROM product_material JOIN material " +
                        "ON product_material.material_id = material.id " +
                        "WHERE product_id = " + productId + " " +
                        "ORDER BY product_material.material_id";
        List<MaterialByProduct> result = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            MaterialByProduct materialByProduct = resultSetToMaterialByProduct(resultSet);
            materialByProduct.convertPeriodToPeriodString();
            result.add(materialByProduct);
        }
        return result;
    }

    private PreparedStatement prepareMaterialByProductForCreate(Connection connection, MaterialByProduct materialByProduct) throws SQLException {
        String query = "INSERT INTO product_material (product_id, material_id, number, period) VALUES (?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, materialByProduct.getProductId());
        statement.setInt(2, materialByProduct.getMaterialId());
        statement.setInt(3, materialByProduct.getNumber());
        statement.setTimestamp(4, materialByProduct.getPeriod());
        return statement;
    }

    public void createMaterialByProduct(MaterialByProduct materialByProduct) throws SQLException, IOException {
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedMaterialByProduct = prepareMaterialByProductForCreate(connection, materialByProduct);
        preparedMaterialByProduct.executeUpdate();
        connection.close();
    }

    private PreparedStatement prepareMaterialByProductForDelete(Connection connection, int id) throws SQLException {
        return connection.prepareStatement("DELETE FROM product_material where id = " + id + ";");
    }

    public void deleteMaterialByProductById(int id) throws SQLException, IOException {
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedMaterialByProductForDelete = prepareMaterialByProductForDelete(connection, id);
        preparedMaterialByProductForDelete.executeUpdate();
    }

    private Production resultSetToProduction(ResultSet resultSet) throws SQLException {
        Production result = new Production();
        result.setId(resultSet.getInt("id"));
        result.setProductId(resultSet.getInt("product_id"));
        result.setProductName(resultSet.getString("product_name"));
        result.setNumber(resultSet.getInt("number"));
        result.setPeriod(resultSet.getTimestamp("period"));
        return result;
    }

    public List<Production> getProduction() throws SQLException, IOException {
        Connection connection = dbConnection.getConnection();
        Statement statement = connection.createStatement();
        String query =
                "SELECT production.id, production.product_id, production.number, production.period, product.name AS product_name " +
                        "FROM production JOIN product " +
                        "ON production.product_id = product.id " +
                        "ORDER BY production.id";
        List<Production> result = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            Production production = resultSetToProduction(resultSet);
            production.convertPeriodToPeriodString();
            result.add(production);
        }
        return result;
    }

    public List<Production> getProductionByProduct(int productId) throws SQLException, IOException {
        Connection connection = dbConnection.getConnection();
        Statement statement = connection.createStatement();
        String query =
                "SELECT production.id, production.product_id, production.number, production.period, product.name AS product_name " +
                        "FROM production JOIN product " +
                        "ON production.product_id = product.id " +
                        "WHERE product_id = " + productId + " " +
                        "ORDER BY production.id";
        List<Production> result = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            Production production = resultSetToProduction(resultSet);
            production.convertPeriodToPeriodString();
            result.add(production);
        }
        return result;
    }

    private PreparedStatement prepareProductionForCreate(Connection connection, Production production) throws SQLException {
        String query = "INSERT INTO production (product_id, number, period) VALUES (?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, production.getProductId());
        statement.setInt(2, production.getNumber());
        statement.setTimestamp(3, production.getPeriod());
        return statement;
    }

    public void createProduction(Production production) throws SQLException, IOException {
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedProductionForCreate = prepareProductionForCreate(connection, production);
        preparedProductionForCreate.executeUpdate();
        connection.close();
    }

    private PreparedStatement prepareProductionForDelete(Connection connection, int id) throws SQLException {
        return connection.prepareStatement("DELETE FROM production where id = " + id + ";");
    }

    public void deleteProductionById(int id) throws  SQLException, IOException {
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedProductionForDelete = prepareProductionForDelete(connection, id);
        preparedProductionForDelete.executeUpdate();
    }
}