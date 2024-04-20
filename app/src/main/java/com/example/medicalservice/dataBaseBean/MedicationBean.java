package com.example.medicalservice.dataBaseBean;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.medicalservice.tools.StringListConverter;

import java.util.List;

@Entity
@TypeConverters(StringListConverter.class)
public class MedicationBean {

    //药名称
    @NonNull
    @PrimaryKey
    private String name ="";
    @ColumnInfo(name = "imageBase64")
    private String imageBase64;
    //用药数量
    @ColumnInfo(name = "medicationNumber")
    private int medicationNumber;

    @ColumnInfo(name = "medicationUnit")
    private String medicationUnit;
    @ColumnInfo(name = "medicationType")
    private String medicationType;
    @ColumnInfo(name = "timeSpacer")

    private String timeSpacer;
    @ColumnInfo(name = "medicationTime")
    private List<String> medicationTime;
    @ColumnInfo(name = "startTime")
    private String startTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public int getMedicationNumber() {
        return medicationNumber;
    }

    public void setMedicationNumber(int medicationNumber) {
        this.medicationNumber = medicationNumber;
    }

    public String getMedicationUnit() {
        return medicationUnit;
    }

    public void setMedicationUnit(String medicationUnit) {
        this.medicationUnit = medicationUnit;
    }

    public String getMedicationType() {
        return medicationType;
    }

    public void setMedicationType(String medicationType) {
        this.medicationType = medicationType;
    }

    public String getTimeSpacer() {
        return timeSpacer;
    }

    public void setTimeSpacer(String timeSpacer) {
        this.timeSpacer = timeSpacer;
    }

    public List<String> getMedicationTime() {
        return medicationTime;
    }

    public void setMedicationTime(List<String> medicationTime) {
        this.medicationTime = medicationTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
