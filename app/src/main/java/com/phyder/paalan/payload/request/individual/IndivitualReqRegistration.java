package com.phyder.paalan.payload.request.individual;

import com.phyder.paalan.social.Social;

/**
 * Created on 22/12/16.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class IndivitualReqRegistration {


    private String entity;

    private Data data;

    private String type;
    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public static IndivitualReqRegistration get(String name, String email, String contactNo, String password,
                                                String imeiNo, String newsletter, String notificationId) {

        Data data = new Data();
        data.setName(name);
        data.setEmailid(email);
        data.setPassword(password);
        data.setImeino(imeiNo);
        data.setMobileno(contactNo);
        data.setIsnewsletter(newsletter);
        data.setNotificationid(notificationId);


        IndivitualReqRegistration indivitualReqRegistration = new IndivitualReqRegistration();
        indivitualReqRegistration.setData(data);
        indivitualReqRegistration.setEntity(Social.IND_REGISTRATION_ENTITY);
        indivitualReqRegistration.setType(Social.REGISTRATION_TYPE);
        indivitualReqRegistration.setAction(Social.SUBMIT_ACTION);

        return indivitualReqRegistration;

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
        return "IndivitualReqRegistration{" +
                "entity='" + entity + '\'' +
                ", data=" + data +
                ", type='" + type + '\'' +
                '}';
    }

    public static class Data {
        private String imeino;

        private String isnewsletter;

        private String name;

        private String notificationid;

        public String getNotificationid() {
            return notificationid;
        }

        public void setNotificationid(String notificationid) {
            this.notificationid = notificationid;
        }

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
                    ", password='" + password + '\'' +
                    ", emailid='" + emailid + '\'' +
                    ", mobileno='" + mobileno + '\'' +
                    '}';
        }
    }


}
