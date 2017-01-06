package com.phyder.paalan.payload.request.organization;

import android.util.Log;

import com.phyder.paalan.social.Social;

/**
 * Created by cmss on 4/1/17.
 */

public class OrganizationReqResistration {
    private String entity;

    private Data data;

    private String type;

    public static OrganizationReqResistration get(String name, String mobileno, String emailid, String password, String notificationid,
                                                  String deviceid, String imeino, String address, String city
            , String state, String pincode, String country) {

        Data data = new Data();
        data.setName(name);
        data.setMobileno(mobileno);
        data.setEmailid(emailid);
        data.setPassword(password);
        data.setNotificationid(notificationid);
        data.setDeviceid(deviceid);
        data.setImeino(imeino);
        data.setAddress(address);
        data.setCity(city);
        data.setState(state);
        data.setPincode(pincode);
        data.setCountry(country);

        OrganizationReqResistration reqResistration = new OrganizationReqResistration();
        reqResistration.setData(data);
        reqResistration.setEntity(Social.ORG_REGISTRATION_ENTITY);
        reqResistration.setType(Social.ORG_REQ_TYPE);
        return reqResistration;

    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "OrganizationReqResistration{" +
                "entity='" + entity + '\'' +
                ", data=" + data +
                ", type='" + type + '\'' +
                '}';
    }

    public static class Data {
        private String pincode;

        private String imeino;

        private String address;

        private String name;

        private String state;

        private String notificationid;

        private String password;

        private String country;

        private String city;

        private String deviceid;

        private String emailid;

        private String mobileno;

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getImeino() {
            return imeino;
        }

        public void setImeino(String imeino) {
            this.imeino = imeino;
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

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getNotificationid() {
            return notificationid;
        }

        public void setNotificationid(String notificationid) {
            this.notificationid = notificationid;
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

        public String getDeviceid() {
            return deviceid;
        }

        public void setDeviceid(String deviceid) {
            this.deviceid = deviceid;
        }

        public String getEmailid() {
            return emailid;
        }

        public void setEmailid(String emailid) {
            this.emailid = emailid;
        }

        public String getMobileno() {
            return mobileno;
        }

        public void setMobileno(String mobileno) {
            this.mobileno = mobileno;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "pincode='" + pincode + '\'' +
                    ", imeino='" + imeino + '\'' +
                    ", address='" + address + '\'' +
                    ", name='" + name + '\'' +
                    ", state='" + state + '\'' +
                    ", notificationid='" + notificationid + '\'' +
                    ", password='" + password + '\'' +
                    ", country='" + country + '\'' +
                    ", city='" + city + '\'' +
                    ", deviceid='" + deviceid + '\'' +
                    ", emailid='" + emailid + '\'' +
                    ", mobileno='" + mobileno + '\'' +
                    '}';
        }
    }
}
