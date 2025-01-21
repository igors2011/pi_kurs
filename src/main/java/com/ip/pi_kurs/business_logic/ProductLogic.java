package com.ip.pi_kurs.business_logic;

import com.ip.pi_kurs.dao.ProductAccess;
import com.ip.pi_kurs.models.MaterialByProduct;
import com.ip.pi_kurs.models.Product;
import com.ip.pi_kurs.models.WorkerByProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Component
public class ProductLogic {
    @Autowired
    private ProductAccess productAccess;

    public List<Product> getProducts() throws SQLException, IOException {
        return productAccess.getProducts();
    }

    public Product getProductById(int id) throws SQLException, IOException {
        return productAccess.getProductById(id);
    }

    public void createProduct(Product product) throws SQLException, IOException {
        productAccess.createProduct(product);
    }

    public void updateProduct(Product product) throws SQLException, IOException {
        productAccess.updateProduct(product);
    }
    public List<WorkerByProduct> getWorkersByProductId(int productId) throws SQLException, IOException {
        return productAccess.getWorkersByProductId(productId);
    }

    public void createWorkerByProductId(WorkerByProduct workerByProduct) throws SQLException, IOException {
        productAccess.createWorkerByProduct(workerByProduct);
    }

    public void deleteWorkerByProductById(int id) throws SQLException, IOException {
        productAccess.deleteWorkerByProductById(id);
    }

    public List<MaterialByProduct> getMaterialsByProductId(int productId) throws SQLException, IOException {
        return productAccess.getMaterialsByProductId(productId);
    }

    public void createMaterialByProductId(MaterialByProduct materialByProduct) throws SQLException, IOException {
        productAccess.createMaterialByProduct(materialByProduct);
    }

    public void deleteMaterialByProductById(int id) throws SQLException, IOException {
        productAccess.deleteMaterialByProductById(id);
    }
}
