package com.phyder.paalan.payload.response;

import java.util.Arrays;

/**
 * Created on 23/1/17.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class ResponseInitialData {

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
        return "ResponseInitialData{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }


    public class Data {
        private Bannerlist[] bannerlist;

        private String lastsyncdate;

        public Bannerlist[] getBannerlist() {
            return bannerlist;
        }

        public void setBannerlist(Bannerlist[] bannerlist) {
            this.bannerlist = bannerlist;
        }

        public String getLastsyncdate() {
            return lastsyncdate;
        }

        public void setLastsyncdate(String lastsyncdate) {
            this.lastsyncdate = lastsyncdate;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "bannerlist=" + Arrays.toString(bannerlist) +
                    ", lastsyncdate='" + lastsyncdate + '\'' +
                    '}';
        }
    }

    public class Bannerlist {
        private String id;

        private String others;

        private String createdBy;

        private String location;

        private String modifiedBy;

        private String bannerLink;

        private String bannerName;

        private String bannerId;

        private String discription;

        private String deleteFlag;

        private String modifyDt;

        private String createDt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOthers() {
            return others;
        }

        public void setOthers(String others) {
            this.others = others;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
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

        public String getBannerLink() {
            return bannerLink;
        }

        public void setBannerLink(String bannerLink) {
            this.bannerLink = bannerLink;
        }

        public String getBannerName() {
            return bannerName;
        }

        public void setBannerName(String bannerName) {
            this.bannerName = bannerName;
        }

        public String getBannerId() {
            return bannerId;
        }

        public void setBannerId(String bannerId) {
            this.bannerId = bannerId;
        }

        public String getDiscription() {
            return discription;
        }

        public void setDiscription(String discription) {
            this.discription = discription;
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

        @Override
        public String toString() {
            return "Bannerlist{" +
                    "id='" + id + '\'' +
                    ", others='" + others + '\'' +
                    ", createdBy='" + createdBy + '\'' +
                    ", location='" + location + '\'' +
                    ", modifiedBy='" + modifiedBy + '\'' +
                    ", bannerLink='" + bannerLink + '\'' +
                    ", bannerName='" + bannerName + '\'' +
                    ", bannerId='" + bannerId + '\'' +
                    ", discription='" + discription + '\'' +
                    ", deleteFlag='" + deleteFlag + '\'' +
                    ", modifyDt='" + modifyDt + '\'' +
                    ", createDt='" + createDt + '\'' +
                    '}';
        }
    }
}
