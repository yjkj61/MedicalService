package com.example.medicalservice.dataBaseBean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.medicalservice.tools.StringUtils;

import java.util.List;

@Entity
public class UserBean {

    @PrimaryKey
    private int userId;

    @ColumnInfo (name = "ownerId")
    private String ownerId;
    @ColumnInfo(name = "userName")
    private String userName;



    @ColumnInfo(name = "nickName")
    private String nickName;

    @ColumnInfo(name="ownerSex")
    private String ownerSex;

    @ColumnInfo(name="ownerAge")
    private String ownerAge;


    @ColumnInfo(name="ownerBedNum")
    private String ownerBedNum;

    @ColumnInfo(name = "ownerRoomNum")
    private String ownerRoomNum;

    @ColumnInfo(name = "phonenumber")
    private String phonenumber;

    @ColumnInfo(name = "avatar")
    private String avatar;
    @ColumnInfo(name = "loginStatus")
    private boolean loginStatus;


    @ColumnInfo(name = "password")
    private String password;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public boolean isLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOwnerSex() {
        return ownerSex;
    }

    public void setOwnerSex(String ownerSex) {
        this.ownerSex = ownerSex;
    }

    public String getOwnerAge() {
        return ownerAge;
    }

    public void setOwnerAge(String ownerAge) {
        this.ownerAge = ownerAge;
    }

    public String getOwnerBedNum() {
        return ownerBedNum;
    }

    public void setOwnerBedNum(String ownerBedNum) {
        this.ownerBedNum = ownerBedNum;
    }

    public String getOwnerRoomNum() {
        return ownerRoomNum;
    }

    public void setOwnerRoomNum(String ownerRoomNum) {
        this.ownerRoomNum = ownerRoomNum;
    }
}
