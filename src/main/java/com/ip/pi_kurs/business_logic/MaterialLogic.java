package com.ip.pi_kurs.business_logic;

import com.ip.pi_kurs.dao.MaterialAccess;
import com.ip.pi_kurs.models.Material;
import com.ip.pi_kurs.models.MaterialCost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Component
public class MaterialLogic {
    @Autowired
    private MaterialAccess materialAccess;

    public List<Material> getMaterials() throws SQLException, IOException {
        return materialAccess.getMaterials();
    }

    public Material getMaterialById(int id) throws SQLException, IOException {
        return materialAccess.getMaterialById(id);
    }

    public void createMaterial(Material material) throws SQLException, IOException {
        materialAccess.createMaterial(material);
    }

    public void updateMaterial(Material material) throws SQLException, IOException {
        materialAccess.updateMaterial(material);
    }

    public List<MaterialCost> getMaterialCosts(int materialId) throws SQLException, IOException {
        return materialAccess.getMaterialCosts(materialId);
    }

    public void createMaterialCost(MaterialCost materialCost) throws SQLException, IOException {
        materialAccess.createMaterialCost(materialCost);
    }

    public double getLastMaterialCost(int materialId, Timestamp endDate) throws SQLException, IOException {
        return materialAccess.getLastMaterialCost(materialId, endDate);
    }
}
