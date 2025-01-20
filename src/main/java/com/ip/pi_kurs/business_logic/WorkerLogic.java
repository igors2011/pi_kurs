package com.ip.pi_kurs.business_logic;

import com.ip.pi_kurs.dao.WorkerAccess;
import com.ip.pi_kurs.models.Salary;
import com.ip.pi_kurs.models.Worker;
import com.ip.pi_kurs.models.WorkerByProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Component
public class WorkerLogic {
    @Autowired
    private WorkerAccess workerAccess;

    public List<Worker> getWorkers() throws SQLException, IOException {
        return workerAccess.getWorkers();
    }

    public Worker getWorkerById(int id) throws SQLException, IOException {
        return workerAccess.getWorkerById(id);
    }

    public void createWorker(Worker worker) throws SQLException, IOException {
        workerAccess.createWorker(worker);
    }

    public void updateWorker(Worker worker) throws SQLException, IOException {
        workerAccess.updateWorker(worker);
    }

    public List<Salary> getWorkerSalaries(int workerId) throws SQLException, IOException {
        return workerAccess.getWorkerSalaries(workerId);
    }

    public void createSalary(Salary salary) throws SQLException, IOException {
        workerAccess.createSalary(salary);
    }
}
