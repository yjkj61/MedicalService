package com.example.medicalservice.tools;

import com.example.medicalservice.activity.healthcareChildActivity.physicalExamination.PhysicalExaminationTip;
import com.example.medicalservice.bean.UseMedHistoryBean;

public class DataEdit {


    private static volatile DataEdit instance;


    private UseMedHistoryBean.DataDTO rowsDTO;

    private PhysicalExaminationTip.PhysicalBean.DataDTO dataDTO;

    private DataEdit() {
// 私有构造函数
    }

    public static DataEdit getInstance() {
        if (instance == null) {
            synchronized (DataEdit.class) {
                if (instance == null) {
                    instance = new DataEdit();
                }
            }
        }
        return instance;
    }

    public UseMedHistoryBean.DataDTO getRowsDTO() {
        return rowsDTO;
    }

    public void setRowsDTO(UseMedHistoryBean.DataDTO rowsDTO) {
        this.rowsDTO = rowsDTO;
    }

    public PhysicalExaminationTip.PhysicalBean.DataDTO getDataDTO() {
        return dataDTO;
    }

    public void setDataDTO(PhysicalExaminationTip.PhysicalBean.DataDTO dataDTO) {
        this.dataDTO = dataDTO;
    }
}
