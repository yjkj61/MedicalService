package com.example.medicalservice.bean;

import java.io.Serializable;
import java.util.List;

public class MessageMindEntity {

    private int total;
    private int code;
    private String msg;
    private List<RowsDTO> rows;

    @Override
    public String toString() {
        return "MessageMindEntity{" +
                "total=" + total +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", rows=" + rows +
                '}';
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<RowsDTO> getRows() {
        return rows;
    }

    public void setRows(List<RowsDTO> rows) {
        this.rows = rows;
    }

    public static class RowsDTO implements Serializable {
        private Object createBy;
        private Object createTime;
        private Object updateBy;
        private Object updateTime;
        private Object remark;
        private int id;
        private String title;
        private Object ownerId;
        private Object distributedCommunity;
        private String publishTime;
        private String endTime;
        private int receiverCount;
        private int readCount;
        private String status;
        private String content;
        private Object userId;
        private String userType;
        private String indexOne;
        private Object noticeImage;
        private Object noticeContent;
        private Object noticeCreatorId;
        private String noticeCreatorName;
        private Object noticeTypeId;
        private Object noticeTypeName;
        private Long markerId;
        private Object markerName;
        private Object readsNumber;

        public Object getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Object createBy) {
            this.createBy = createBy;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Object updateBy) {
            this.updateBy = updateBy;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(Object ownerId) {
            this.ownerId = ownerId;
        }

        public Object getDistributedCommunity() {
            return distributedCommunity;
        }

        public void setDistributedCommunity(Object distributedCommunity) {
            this.distributedCommunity = distributedCommunity;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getReceiverCount() {
            return receiverCount;
        }

        public void setReceiverCount(int receiverCount) {
            this.receiverCount = receiverCount;
        }

        public int getReadCount() {
            return readCount;
        }

        public void setReadCount(int readCount) {
            this.readCount = readCount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getIndexOne() {
            return indexOne;
        }

        public void setIndexOne(String indexOne) {
            this.indexOne = indexOne;
        }

        public Object getNoticeImage() {
            return noticeImage;
        }

        public void setNoticeImage(Object noticeImage) {
            this.noticeImage = noticeImage;
        }

        public Object getNoticeContent() {
            return noticeContent;
        }

        public void setNoticeContent(Object noticeContent) {
            this.noticeContent = noticeContent;
        }

        public Object getNoticeCreatorId() {
            return noticeCreatorId;
        }

        public void setNoticeCreatorId(Object noticeCreatorId) {
            this.noticeCreatorId = noticeCreatorId;
        }

        public String getNoticeCreatorName() {
            return noticeCreatorName;
        }

        public void setNoticeCreatorName(String noticeCreatorName) {
            this.noticeCreatorName = noticeCreatorName;
        }

        public Object getNoticeTypeId() {
            return noticeTypeId;
        }

        public void setNoticeTypeId(Object noticeTypeId) {
            this.noticeTypeId = noticeTypeId;
        }

        public Object getNoticeTypeName() {
            return noticeTypeName;
        }

        public void setNoticeTypeName(Object noticeTypeName) {
            this.noticeTypeName = noticeTypeName;
        }

        public Long getMarkerId() {
            return markerId;
        }

        public void setMarkerId(Long markerId) {
            this.markerId = markerId;
        }

        public Object getMarkerName() {
            return markerName;
        }

        public void setMarkerName(Object markerName) {
            this.markerName = markerName;
        }

        public Object getReadsNumber() {
            return readsNumber;
        }

        public void setReadsNumber(Object readsNumber) {
            this.readsNumber = readsNumber;
        }

        @Override
        public String toString() {
            return "RowsDTO{" +
                    "createBy=" + createBy +
                    ", createTime=" + createTime +
                    ", updateBy=" + updateBy +
                    ", updateTime=" + updateTime +
                    ", remark=" + remark +
                    ", id=" + id +
                    ", title='" + title + '\'' +
                    ", ownerId=" + ownerId +
                    ", distributedCommunity=" + distributedCommunity +
                    ", publishTime='" + publishTime + '\'' +
                    ", endTime='" + endTime + '\'' +
                    ", receiverCount=" + receiverCount +
                    ", readCount=" + readCount +
                    ", status='" + status + '\'' +
                    ", content='" + content + '\'' +
                    ", userId=" + userId +
                    ", userType='" + userType + '\'' +
                    ", indexOne='" + indexOne + '\'' +
                    ", noticeImage=" + noticeImage +
                    ", noticeContent=" + noticeContent +
                    ", noticeCreatorId=" + noticeCreatorId +
                    ", noticeCreatorName='" + noticeCreatorName + '\'' +
                    ", noticeTypeId=" + noticeTypeId +
                    ", noticeTypeName=" + noticeTypeName +
                    ", markerId=" + markerId +
                    ", markerName=" + markerName +
                    ", readsNumber=" + readsNumber +
                    '}';
        }
    }
}
