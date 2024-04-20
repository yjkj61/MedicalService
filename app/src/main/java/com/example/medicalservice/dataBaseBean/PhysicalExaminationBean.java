package com.example.medicalservice.dataBaseBean;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PhysicalExaminationBean {
    @NonNull
    @PrimaryKey
    private String name = "";
    @ColumnInfo(name = "physicalExaminationType")
    private String physicalExaminationType;

    @ColumnInfo(name = "physicalExaminationSpacer")
    private String physicalExaminationSpacer;
    @ColumnInfo(name = "physicalExaminationStartTime")
    private String physicalExaminationStartTime;
    @ColumnInfo(name = "physicalExaminationTipTime")
    private String physicalExaminationTipTime;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhysicalExaminationType() {
        return physicalExaminationType;
    }

    public void setPhysicalExaminationType(String physicalExaminationType) {
        this.physicalExaminationType = physicalExaminationType;
    }

    public String getPhysicalExaminationSpacer() {
        return physicalExaminationSpacer;
    }

    public void setPhysicalExaminationSpacer(String physicalExaminationSpacer) {
        this.physicalExaminationSpacer = physicalExaminationSpacer;
    }

    public String getPhysicalExaminationStartTime() {
        return physicalExaminationStartTime;
    }

    public void setPhysicalExaminationStartTime(String physicalExaminationStartTime) {
        this.physicalExaminationStartTime = physicalExaminationStartTime;
    }

    public String getPhysicalExaminationTipTime() {
        return physicalExaminationTipTime;
    }

    public void setPhysicalExaminationTipTime(String physicalExaminationTipTime) {
        this.physicalExaminationTipTime = physicalExaminationTipTime;
    }
}
