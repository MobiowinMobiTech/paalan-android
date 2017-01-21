package com.phyder.paalan.payload.response.organization;

import com.phyder.paalan.social.Social;

import java.util.Arrays;

/**
 * Created on 5/1/17.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class OrgResSyncEvent {
    private String message;

    private String status;

    private Data[] data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OrgResSyncEvent{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }

    public class Data {
        private Orglist[] orglist;

        public Orglist[] getOrglist() {
            return orglist;
        }

        public void setOrglist(Orglist[] orglist) {
            this.orglist = orglist;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "orglist=" + Arrays.toString(orglist) +
                    '}';
        }
    }

    public class Orglist {
        private String others;

        private String endDt;

        private String location;

        private String modifiedBy;

        private String discription;

        private String id;

        private String orgId;

        private String subTitle;

        private String title;

        private String category;

        private String eventId;

        private String createdBy;

        private String startDt;

        private String deleteFlag;

        private String modifyDt;

        private String createDt;

        public String getOthers() {
            return others;
        }

        public void setOthers(String others) {
            this.others = others;
        }

        public String getEndDt() {
            return endDt;
        }

        public void setEndDt(String endDt) {
            this.endDt = endDt;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        public String getDiscription() {
            return discription;
        }

        public void setDiscription(String discription) {
            this.discription = discription;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getStartDt() {
            return startDt;
        }

        public void setStartDt(String startDt) {
            this.startDt = startDt;
        }

        public String getDeleteFlag() {
            return deleteFlag;
        }

        public void setDeleteFlag(String deleteFlag) {
            this.deleteFlag = deleteFlag;
        }

        public String getModifyDt() {
            return modifyDt;
        }

        public void setModifyDt(String modifyDt) {
            this.modifyDt = modifyDt;
        }

        public String getCreateDt() {
            return createDt;
        }

        public void setCreateDt(String createDt) {
            this.createDt = createDt;
        }

        public Orglist() {
            super();
        }

        @Override
        public String toString() {
            return "ClassPojo [others = " + others + ", endDt = " + endDt + ", location = " + location + ", modifiedBy = " + modifiedBy + ", discription = " + discription + ", id = " + id + ", orgId = " + orgId + ", subTitle = " + subTitle + ", title = " + title + ", category = " + category + ", eventId = " + eventId + ", createdBy = " + createdBy + ", startDt = " + startDt + ", deleteFlag = " + deleteFlag + ", modifyDt = " + modifyDt + ", createDt = " + createDt + "]";
        }
    }
}
