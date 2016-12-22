package com.phyder.paalan.payload.request.individual;

/**
 * Created on 22/12/16.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class IndivitualReqRegistration {

    private String entity;

    private Data data;

    private String type;

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
        return "IndivitualReqRegistration{" +
                "entity='" + entity + '\'' +
                ", data=" + data +
                ", type='" + type + '\'' +
                '}';
    }

    public class Data {
        private String imeino;

        private String isnewsletter;

        private String name;

        private String longitude;

        private String latitude;

        private String password;

        private String emailid;

        private String mobileno;

        public String getImeino() {
            return imeino;
        }

        public void setImeino(String imeino) {
            this.imeino = imeino;
        }

        public String getIsnewsletter() {
            return isnewsletter;
        }

        public void setIsnewsletter(String isnewsletter) {
            this.isnewsletter = isnewsletter;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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
                    "imeino='" + imeino + '\'' +
                    ", isnewsletter='" + isnewsletter + '\'' +
                    ", name='" + name + '\'' +
                    ", longitude='" + longitude + '\'' +
                    ", latitude='" + latitude + '\'' +
                    ", password='" + password + '\'' +
                    ", emailid='" + emailid + '\'' +
                    ", mobileno='" + mobileno + '\'' +
                    '}';
        }
    }


}
