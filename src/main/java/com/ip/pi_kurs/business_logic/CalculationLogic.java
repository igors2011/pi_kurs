package com.ip.pi_kurs.business_logic;

import com.ip.pi_kurs.dao.CalculationAccess;
import com.ip.pi_kurs.models.Material;
import com.ip.pi_kurs.models.MaterialByProduct;
import com.ip.pi_kurs.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class CalculationLogic {
    @Autowired
    private ProductLogic productLogic;
    @Autowired
    private CalculationAccess calculationAccess;
    @Autowired
    private MaterialLogic materialLogic;

    private Timestamp getFirstDateOfMonth(String periodString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(periodString, formatter);
        LocalDate localDate = localDateTime.toLocalDate();
        LocalDate startOfMonth = localDate.withDayOfMonth(1);
        return Timestamp.valueOf(startOfMonth.atStartOfDay());
    }

    private Timestamp getLastDateOfMonth(String periodString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(periodString, formatter);
        LocalDate localDate = localDateTime.toLocalDate();
        LocalDate endOfMonth = localDate.withDayOfMonth(localDate.lengthOfMonth());
        return Timestamp.valueOf(endOfMonth.atTime(LocalTime.MAX));
    }

    private double getTotalFixedCostsForMonth(Timestamp firstDate, Timestamp lastDate) throws SQLException, IOException {
        return calculationAccess.getTotalFixedCostsForMonth(firstDate, lastDate);
    }

    private int getTotalProductionForMonth(Timestamp startDate, Timestamp endDate) throws SQLException, IOException {
        return calculationAccess.getTotalProductionForMonth(startDate, endDate);
    }

    private int getProductionForMonthForProduct(Timestamp startDate, Timestamp endDate, int productId) throws SQLException, IOException {
        return calculationAccess.getProductionForMonthForProduct(startDate, endDate, productId);
    }



    public List<String> calculate(String periodString, int productId) throws SQLException, IOException {
        List<String> result = new ArrayList<>();
        Timestamp firstDate = getFirstDateOfMonth(periodString);
        Timestamp lastDate = getLastDateOfMonth(periodString);
        result.add("Период расчёта: с " + firstDate + " по " + lastDate);
        Product product = productLogic.getProductById(productId);
        result.add("Продукт: " + product.getName());
        double totalFixedCostsForMonth = getTotalFixedCostsForMonth(firstDate, lastDate);
        result.add("Общие постоянные издержки за месяц: " + totalFixedCostsForMonth);
        int totalProductionForMonth = getTotalProductionForMonth(firstDate, lastDate);
        int productionForMonthForProduct = getProductionForMonthForProduct(firstDate, lastDate, productId);
        result.add("Произведено выбранного продукта: " + productionForMonthForProduct);
        result.add("Всего произведено продуктов: " + totalProductionForMonth);
        double fixedCostsCoefficient = (double) productionForMonthForProduct / totalProductionForMonth;
        result.add("Коэффициент постоянных издержек для выбранного продукта составляет " + fixedCostsCoefficient);
        double fixedCostsPerProduct = totalFixedCostsForMonth * fixedCostsCoefficient / productionForMonthForProduct;
        result.add("Таким образом, постоянные издержки на единицу выбранного продукта составляют " + totalFixedCostsForMonth + " * " + fixedCostsCoefficient + " / " + productionForMonthForProduct + " = " + fixedCostsPerProduct);
        List<MaterialByProduct> materialsByProduct = productLogic.getMaterialsByProductId(productId);
        double materialCostsByProduct = 0;
        for (MaterialByProduct el : materialsByProduct) {
            int numberOfMaterials = el.getNumber();
            double lastMaterialCost = materialLogic.getLastMaterialCost(el.getMaterialId());
            materialCostsByProduct += numberOfMaterials * lastMaterialCost;
        }
        result.add("Переменные издержки на производство выбранной продукции составили " + materialCostsByProduct);
        double totalCostsByProduct = fixedCostsPerProduct + materialCostsByProduct;
        result.add("Таким образом себестоимость продукции составила " + fixedCostsPerProduct + " + " + materialCostsByProduct + " = " + totalCostsByProduct);
        return result;
    }
}
