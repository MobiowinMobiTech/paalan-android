package com.phyder.paalan.payload.request.organization;

import android.util.Log;

import com.phyder.paalan.social.Social;

import java.util.ArrayList;
import java.util.Arrays;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created on 22/12/16.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class OrganizationReqProfile {

    private String entity;

    private Data data;

    private String type;

    public static OrganizationReqProfile get(String pincode, String dpimage, ArrayList<String> presence, String memberid, String registrationno, String name, String state, ArrayList<String> socialLink, String mobileno, String country, String city) {
        Data data = new Data();
        data.setPincode(pincode);
        data.setCity(city);
        data.setDpimage(dpimage);

        Presence[] presences = new Presence[presence.size()];

        for (int i = 0; i < presence.size(); i++) {

            Presence obj = new Presence();
            obj.setPlace(presence.get(i));
            presences[i] = obj;
        }
        data.setPresence(presences);

        data.setMemberid(memberid);
        data.setRegistrationno(registrationno);
        data.setName(name);
        data.setState(state);

        Sociallink[] sociallinks = new Sociallink[socialLink.size()];
        for (int i = 0; i < sociallinks.length; i++) {
            Sociallink socialObj = new Sociallink();
            socialObj.setSociallink(socialLink.get(i));
            sociallinks[i] = socialObj;
        }
        data.setSociallink(sociallinks);

        data.setMobileno(mobileno);
        data.setCountry(country);
        data.setCity(city);

        OrganizationReqProfile reqProfile = new OrganizationReqProfile();
        reqProfile.setData(data);
        reqProfile.setEntity(Social.ORG_REGISTRATION_ENTITY);
        reqProfile.setType(Social.ORG_PROFILE);
        return reqProfile;
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
        return "OrganizationReqProfile{" +
                "entity='" + entity + '\'' +
                ", data=" + data +
                ", type='" + type + '\'' +
                '}';
    }

    public static class Data {
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

    public static class Sociallink {

        private String socialLinks;

        public String getSociallink() {
            return socialLinks;
        }

        public void setSociallink(String socialLinks) {
            this.socialLinks = socialLinks;
        }

        @Override
        public String toString() {
            return "Sociallink{" +
                    "twitter='" + socialLinks + '\'' +
                    '}';
        }
        //        private String instagram;
//
//        private String hangoutlink;
//
//        private String linkedin;
//
//        private String fb;


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
                    ", place='" + place + '\'' +
                    '}';
        }
    }
}
