package com.example.medicalservice.dataBaseDao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.medicalservice.dataBaseBean.UserBean;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;

@Dao
public interface UserDao {

    @Query("SELECT * FROM UserBean")
    Flowable<UserBean> getAll();

    @Query("SELECT * FROM UserBean")
    Maybe<UserBean> getAll2();

    @Query("SELECT * FROM UserBean")
    List<UserBean> getAllUser();

    @Query("SELECT * FROM UserBean where userId = :userId")
    UserBean getUserById(int userId);

    @Update
    void updateUser(UserBean userBean);

    @Update
    void updateUsers(UserBean... userBeans);

    @Query("SELECT * FROM UserBean WHERE loginStatus = :status ")
    UserBean getLoginStatusTrue(boolean status);

    //List<UserBean> getAll();

    @Insert
    void insertAll(UserBean... userBeans);

    @Insert
    void insert(UserBean userBean);


    @Delete
    void delete(UserBean userBean);
}
