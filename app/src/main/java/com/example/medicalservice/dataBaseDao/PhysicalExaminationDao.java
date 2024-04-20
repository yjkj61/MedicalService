package com.example.medicalservice.dataBaseDao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.medicalservice.dataBaseBean.PhysicalExaminationBean;

import java.util.List;

@Dao
public interface PhysicalExaminationDao {

    @Query("SELECT * FROM PhysicalExaminationBean")
    List<PhysicalExaminationBean> PhysicalExaminationBeans();

    @Insert
    void insertAll(PhysicalExaminationBean... physicalExaminationBeans);


    @Delete
    void delete(PhysicalExaminationBean physicalExaminationBean);
}
