package com.phyder.paalan.payload.request.organization;

import java.util.Arrays;

/**
 * Created on 22/12/16.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class OrganizationReqProfile {

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
        return "OrganizationReqProfile{" +
                "entity='" + entity + '\'' +
                ", data=" + data +
                ", type='" + type + '\'' +
                '}';
    }

    public class Data {
        private String pincode;

        private String dpimage;

        private Presence[] presence;

        private String memberid;

        private String registrationno;

        private String name;

        private String state;

        private Sociallink[] sociallink;

        private String mobileno;

        private String country;

        private String city;

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getDpimage() {
            return dpimage;
        }

        public void setDpimage(String dpimage) {
            this.dpimage = dpimage;
        }

        public Presence[] getPresence() {
            return presence;
        }

        public void setPresence(Presence[] presence) {
            this.presence = presence;
        }

        public String getMemberid() {
            return memberid;
        }

        public void setMemberid(String memberid) {
            this.memberid = memberid;
        }

        public String getRegistrationno() {
            return registrationno;
        }

        public void setRegistrationno(String registrationno) {
            this.registrationno = registrationno;
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

        public Sociallink[] getSociallink() {
            return sociallink;
        }

        public void setSociallink(Sociallink[] sociallink) {
            this.sociallink = sociallink;
        }

        public String getMobileno() {
            return mobileno;
        }

        public void setMobileno(String mobileno) {
            this.mobileno = mobileno;
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

        @Override
        public String toString() {
            return "Data{" +
                    "pincode='" + pincode + '\'' +
                    ", dpimage='" + dpimage + '\'' +
                    ", presence=" + Arrays.toString(presence) +
                    ", memberid='" + memberid + '\'' +
                    ", registrationno='" + registrationno + '\'' +
                    ", name='" + name + '\'' +
                    ", state='" + state + '\'' +
                    ", sociallink=" + Arrays.toString(sociallink) +
                    ", mobileno='" + mobileno + '\'' +
                    ", country='" + country + '\'' +
                    ", city='" + city + '\'' +
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
