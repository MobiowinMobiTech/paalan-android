package com.phyder.paalan.payload.request.individual;

import android.support.annotation.Nullable;

import com.phyder.paalan.social.Social;

/**
 * Created on 22/12/16.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class IndivitualReqUpdateProfile {

    private String entity;

    private Data data;

    private String type;

    @Nullable
    public static IndivitualReqUpdateProfile get(String pincode, String dpimage, String memberid, String dob, String state, String gender, String firstName, String lastName, String mobileNo, String country, String city) {

        Data data = new Data();
        data.setPincode(pincode);
        data.setDpimage(dpimage);
        data.setMemberid(memberid);
        data.setDob(dob);
        data.setState(state);
        data.setGender(gender);
        data.setFirstname(firstName);
        data.setLastname(lastName);
        data.setMobileno(mobileNo);
        data.setCountry(country);
        data.setCity(city);


        IndivitualReqUpdateProfile reqProfile = new IndivitualReqUpdateProfile();
        reqProfile.setType(Social.PROFILE_TYPE);
        reqProfile.setData(data);
        reqProfile.setEntity(Social.IND_ENTITY);
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
        return "IndivitualReqUpdateProfile{" +
                "entity='" + entity + '\'' +
                ", data=" + data +
                ", type='" + type + '\'' +
                '}';
    }

    public static class Data {
        private String pincode;

        private String dpimage;

        private String memberid;

        private String dob;

        private String state;

        private String gender;

        private String lastname;

        private String firstname;

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

        public String getMemberid() {
            return memberid;
        }

        public void setMemberid(String memberid) {
            this.memberid = memberid;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
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
                    ", memberid='" + memberid + '\'' +
                    ", dob='" + dob + '\'' +
                    ", state='" + state + '\'' +
                    ", gender='" + gender + '\'' +
                    ", lastname='" + lastname + '\'' +
                    ", firstname='" + firstname + '\'' +
                    ", mobileno='" + mobileno + '\'' +
                    ", country='" + country + '\'' +
                    ", city='" + city + '\'' +
                    '}';
        }
    }


}
