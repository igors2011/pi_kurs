package com.ip.pi_kurs.business_logic;

import com.ip.pi_kurs.dao.FixedCostsAccess;
import com.ip.pi_kurs.models.FixedCost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Component
public class FixedCostsLogic {
    @Autowired
    private FixedCostsAccess fixedCostsAccess;

    public List<FixedCost> getFixedCosts() throws SQLException, IOException {
        return fixedCostsAccess.getFixedCosts();
    }
    public void createFixedCost(FixedCost fixedCost) throws SQLException, IOException {
        fixedCostsAccess.createFixedCost(fixedCost);
    }
}
