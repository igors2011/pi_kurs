package com.ip.pi_kurs.dao;

import com.ip.pi_kurs.DBConnection;
import com.ip.pi_kurs.models.Product;
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

    private PreparedStatement prepareProductForUpdate(Connection connection, Product product) throws SQLException  {
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
}
