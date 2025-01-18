package com.ip.pi_kurs.dao;

import com.ip.pi_kurs.DBConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAccess {
    @Autowired
    private DBConnection connection;

}
