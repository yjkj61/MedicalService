package com.example.medicalservice;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.medicalservice.dataBaseBean.MedicationBean;
import com.example.medicalservice.dataBaseBean.PhysicalExaminationBean;
import com.example.medicalservice.dataBaseBean.UserBean;
import com.example.medicalservice.dataBaseDao.MedicationDao;
import com.example.medicalservice.dataBaseDao.PhysicalExaminationDao;
import com.example.medicalservice.dataBaseDao.UserDao;

@Database(entities = {MedicationBean.class, PhysicalExaminationBean.class, UserBean.class}, version = 4,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MedicationDao medicationDao();
    public abstract PhysicalExaminationDao physicalExaminationDao();

    public abstract UserDao userDao();
}