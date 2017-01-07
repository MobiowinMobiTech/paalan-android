package com.phyder.paalan.payload.request.organization;

import com.phyder.paalan.social.Social;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created on 22/12/16.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class OrganisationReqProfile {

    private String entity;

    private Data data;

    private String type;

    public static OrganisationReqProfile get(String orgid, String role, String imeino, String isnewsletter, String isregisterd, String registrationno, String dpimage, String fblink, String linkedinlink, String websitelink, String twitterlink, String presencearea) {

        Data data = new Data();
        data.setOrgid(orgid);
        data.setRole(role);
        data.setImeino(imeino);
        data.setIsnewsletter(isnewsletter);
        data.setIsregisterd(isregisterd);
        data.setRegistrationno(registrationno);
        data.setDpimage(dpimage);
        data.setFblink(fblink);
        data.setLinkedinlink(linkedinlink);
        data.setWebsitelink(websitelink);
        data.setTwitterlink(twitterlink);
        data.setPresencearea(presencearea);

        OrganisationReqProfile reqProfile = new OrganisationReqProfile();
        reqProfile.setData(data);
        reqProfile.setEntity(Social.ORG_ENTITY);
        reqProfile.setType(Social.PROFILE_TYPE);
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
        return "OrganisationReqProfile{" +
                "entity='" + entity + '\'' +
                ", data=" + data +
                ", type='" + type + '\'' +
                '}';
    }

    public static class Data {
        private String linkedinlink;

        private String fblink;

        private String dpimage;

        private String imeino;

        private String orgid;

        private String registrationno;

        private String presencearea;

        private String isnewsletter;

        private String role;

        private String isregisterd;

        private String twitterlink;

        private String websitelink;

        public String getLinkedinlink() {
            return linkedinlink;
        }

        public void setLinkedinlink(String linkedinlink) {
            this.linkedinlink = linkedinlink;
        }

        public String getFblink() {
            return fblink;
        }

        public void setFblink(String fblink) {
            this.fblink = fblink;
        }

        public String getDpimage() {
            return dpimage;
        }

        public void setDpimage(String dpimage) {
            this.dpimage = dpimage;
        }

        public String getImeino() {
            return imeino;
        }

        public void setImeino(String imeino) {
            this.imeino = imeino;
        }

        public String getOrgid() {
            return orgid;
        }

        public void setOrgid(String orgid) {
            this.orgid = orgid;
        }

        public String getRegistrationno() {
            return registrationno;
        }

        public void setRegistrationno(String registrationno) {
            this.registrationno = registrationno;
        }

        public String getPresencearea() {
            return presencearea;
        }

        public void setPresencearea(String presencearea) {
            this.presencearea = presencearea;
        }

        public String getIsnewsletter() {
            return isnewsletter;
        }

        public void setIsnewsletter(String isnewsletter) {
            this.isnewsletter = isnewsletter;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getIsregisterd() {
            return isregisterd;
        }

        public void setIsregisterd(String isregisterd) {
            this.isregisterd = isregisterd;
        }

        public String getTwitterlink() {
            return twitterlink;
        }

        public void setTwitterlink(String twitterlink) {
            this.twitterlink = twitterlink;
        }

        public String getWebsitelink() {
            return websitelink;
        }

        public void setWebsitelink(String websitelink) {
            this.websitelink = websitelink;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "linkedinlink='" + linkedinlink + '\'' +
                    ", fblink='" + fblink + '\'' +
                    ", dpimage='" + dpimage + '\'' +
                    ", imeino='" + imeino + '\'' +
                    ", orgid='" + orgid + '\'' +
                    ", registrationno='" + registrationno + '\'' +
                    ", presencearea='" + presencearea + '\'' +
                    ", isnewsletter='" + isnewsletter + '\'' +
                    ", role='" + role + '\'' +
                    ", isregisterd='" + isregisterd + '\'' +
                    ", twitterlink='" + twitterlink + '\'' +
                    ", websitelink='" + websitelink + '\'' +
                    '}';
        }
    }
}
