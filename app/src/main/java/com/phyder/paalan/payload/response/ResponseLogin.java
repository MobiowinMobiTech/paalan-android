package com.phyder.paalan.payload.response;

/**
 * Created by cmss on 11/1/17.
 */
public class ResponseLogin {

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
        return "ClassPojo [message = " + message + ", status = " + status + ", data = " + data + "]";
    }

    public class Data {
        private String[] orgprofiledata;

        private Orgregdata[] orgregdata;

        public String[] getOrgprofiledata() {
            return orgprofiledata;
        }

        public void setOrgprofiledata(String[] orgprofiledata) {
            this.orgprofiledata = orgprofiledata;
        }

        public Orgregdata[] getOrgregdata() {
            return orgregdata;
        }

        public void setOrgregdata(Orgregdata[] orgregdata) {
            this.orgregdata = orgregdata;
        }

        @Override
        public String toString() {
            return "ClassPojo [orgprofiledata = " + orgprofiledata + ", orgregdata = " + orgregdata + "]";
        }
    }

    public class Orgregdata {
        private String modifiedBy;

        private String state;

        private String password;

        private String country;

        private String city;

        private String pincode;

        private String id;

        private String orgId;

        private String notificationId;

        private String emailId;

        private String createdBy;

        private String address;

        private String name;

        private String imeiNo;

        private String deleteFlag;

        private String modifyDt;

        private String createDt;

        private String mobileNo;

        private String deviceId;

        public String getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
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

        public String getNotificationId() {
            return notificationId;
        }

        public void setNotificationId(String notificationId) {
            this.notificationId = notificationId;
        }

        public String getEmailId() {
            return emailId;
        }

        public void setEmailId(String emailId) {
            this.emailId = emailId;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImeiNo() {
            return imeiNo;
        }

        public void setImeiNo(String imeiNo) {
            this.imeiNo = imeiNo;
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

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        @Override
        public String toString() {
            return "ClassPojo [modifiedBy = " + modifiedBy + ", state = " + state + ", password = " + password + ", country = " + country + ", city = " + city + ", pincode = " + pincode + ", id = " + id + ", orgId = " + orgId + ", notificationId = " + notificationId + ", emailId = " + emailId + ", createdBy = " + createdBy + ", address = " + address + ", name = " + name + ", imeiNo = " + imeiNo + ", deleteFlag = " + deleteFlag + ", modifyDt = " + modifyDt + ", createDt = " + createDt + ", mobileNo = " + mobileNo + ", deviceId = " + deviceId + "]";
        }
    }
}

