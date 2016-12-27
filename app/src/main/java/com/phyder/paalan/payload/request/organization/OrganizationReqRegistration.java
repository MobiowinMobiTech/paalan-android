package com.phyder.paalan.payload.request.organization;

import com.phyder.paalan.social.Social;

import java.util.ArrayList;
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

    public static OrganizationReqRegistration get(String imeino, ArrayList<String> place, String registrationno, String isnewsletter, String name, String role, String lati, String longi, String isRegistered, String password, ArrayList<String> socialLink, String email, String mobile) {

        Data data = new Data();
        data.setImeino(imeino);

        Presence[] presence1 = new Presence[place.size()];

        for (int i = 0; i < presence1.length; i++) {
            Presence obj = new Presence();
            obj.setPlace(place.get(i));
            presence1[i] = obj;
        }
        data.setRegistrationno(registrationno);
        data.setIsnewsletter(isnewsletter);
        data.setName(name);
        data.setRole(role);
        data.setLatitude(lati);
        data.setLongitude(longi);
        data.setIsregisterd(isRegistered);
        data.setPassword(password);

        Sociallink[] sociallinks = new Sociallink[socialLink.size()];
        for (int i = 0; i < sociallinks.length; i++) {
            Sociallink social = new Sociallink();
            social.setSocialsitelink(socialLink.get(i));
            sociallinks[i] = social;
        }
        data.setSociallink(sociallinks);
        data.setPresence(presence1);

        data.setEmailid(email);
        data.setMobileno(mobile);

        OrganizationReqRegistration payload = new OrganizationReqRegistration();
        payload.setEntity(Social.ORG_REGISTRATION_ENTITY);
        payload.setType("registration");
        payload.setData(data);
        return payload;
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
        return "OrganizationReqRegistration{" +
                "entity='" + entity + '\'' +
                ", data=" + data +
                ", type='" + type + '\'' +
                '}';
    }

    public static class Data {
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

    public static class Sociallink {
        private String socialsitelink;

        public String getSocialsitelink() {
            return socialsitelink;
        }

        public void setSocialsitelink(String socialsitelink) {
            this.socialsitelink = socialsitelink;
        }

        @Override
        public String toString() {
            return "Sociallink{" +
                    "socialsitelink='" + socialsitelink + '\'' +
                    '}';
        }
    }

    public static class Presence {
        private String place;

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        @Override
        public String toString() {
            return "Presence{" +
                    "place='" + place + '\'' +
                    '}';
        }
    }

}
