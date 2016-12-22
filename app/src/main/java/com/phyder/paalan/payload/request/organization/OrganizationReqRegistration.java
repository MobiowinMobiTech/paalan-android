package com.phyder.paalan.payload.request.organization;

import java.util.Arrays;

/**
 * Created on 22/12/16.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class OrganizationReqRegistration {
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
        return "OrganizationReqRegistration{" +
                "entity='" + entity + '\'' +
                ", data=" + data +
                ", type='" + type + '\'' +
                '}';
    }

    public class Data {
        private String imeino;

        private Presence[] presence;

        private String registrationno;

        private String isnewsletter;

        private String name;

        private String role;

        private String longitude;

        private String latitude;

        private String isregisterd;

        private String password;

        private Sociallink[] sociallink;

        private String emailid;

        private String mobileno;

        public String getImeino() {
            return imeino;
        }

        public void setImeino(String imeino) {
            this.imeino = imeino;
        }

        public Presence[] getPresence() {
            return presence;
        }

        public void setPresence(Presence[] presence) {
            this.presence = presence;
        }

        public String getRegistrationno() {
            return registrationno;
        }

        public void setRegistrationno(String registrationno) {
            this.registrationno = registrationno;
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

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
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

        public String getIsregisterd() {
            return isregisterd;
        }

        public void setIsregisterd(String isregisterd) {
            this.isregisterd = isregisterd;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Sociallink[] getSociallink() {
            return sociallink;
        }

        public void setSociallink(Sociallink[] sociallink) {
            this.sociallink = sociallink;
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
                    ", presence=" + Arrays.toString(presence) +
                    ", registrationno='" + registrationno + '\'' +
                    ", isnewsletter='" + isnewsletter + '\'' +
                    ", name='" + name + '\'' +
                    ", role='" + role + '\'' +
                    ", longitude='" + longitude + '\'' +
                    ", latitude='" + latitude + '\'' +
                    ", isregisterd='" + isregisterd + '\'' +
                    ", password='" + password + '\'' +
                    ", sociallink=" + Arrays.toString(sociallink) +
                    ", emailid='" + emailid + '\'' +
                    ", mobileno='" + mobileno + '\'' +
                    '}';
        }
    }

    public class Sociallink {
        private String twitter;

        private String instagram;

        private String hangoutlink;

        private String linkedin;

        private String fb;

        public String getTwitter() {
            return twitter;
        }

        public void setTwitter(String twitter) {
            this.twitter = twitter;
        }

        public String getInstagram() {
            return instagram;
        }

        public void setInstagram(String instagram) {
            this.instagram = instagram;
        }

        public String getHangoutlink() {
            return hangoutlink;
        }

        public void setHangoutlink(String hangoutlink) {
            this.hangoutlink = hangoutlink;
        }

        public String getLinkedin() {
            return linkedin;
        }

        public void setLinkedin(String linkedin) {
            this.linkedin = linkedin;
        }

        public String getFb() {
            return fb;
        }

        public void setFb(String fb) {
            this.fb = fb;
        }

        @Override
        public String toString() {
            return "Sociallink{" +
                    "twitter='" + twitter + '\'' +
                    ", instagram='" + instagram + '\'' +
                    ", hangoutlink='" + hangoutlink + '\'' +
                    ", linkedin='" + linkedin + '\'' +
                    ", fb='" + fb + '\'' +
                    '}';
        }
    }

    public class Presence {
        private String place2;

        private String place1;

        private String place;

        private String place3;

        public String getPlace2() {
            return place2;
        }

        public void setPlace2(String place2) {
            this.place2 = place2;
        }

        public String getPlace1() {
            return place1;
        }

        public void setPlace1(String place1) {
            this.place1 = place1;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getPlace3() {
            return place3;
        }

        public void setPlace3(String place3) {
            this.place3 = place3;
        }

        @Override
        public String toString() {
            return "Presence{" +
                    "place2='" + place2 + '\'' +
                    ", place1='" + place1 + '\'' +
                    ", place='" + place + '\'' +
                    ", place3='" + place3 + '\'' +
                    '}';
        }
    }

}
