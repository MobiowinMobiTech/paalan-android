package com.phyder.paalan.payload.request.organization;

import com.phyder.paalan.social.Social;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created on 6/1/17.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class OrgReqCreateRequest {

    private String entity;

    private Data data;

    private String action;

    private String type;

    public static OrgReqCreateRequest get(String orgs, String title, String subTitle,String description,
                                          String others,String location, String action) {

        Data data = new Data();
        data.setOrgid(orgs);
        data.setTitle(title);
        data.setSubtitle(subTitle);
        data.setDiscription(description);
        data.setOthers(others);
        data.setLocation(location);

        OrgReqCreateRequest createReuest = new OrgReqCreateRequest();
        createReuest.setData(data);
        createReuest.setAction(action);
        createReuest.setEntity(Social.ORG_ENTITY);
        createReuest.setType(Social.REQUEST_TYPE);

        return createReuest;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "OrgReqCreateRequest{" +
                "entity='" + entity + '\'' +
                ", data=" + data +
                ", action='" + action + '\'' +
                ", type='" + type + '\'' +
                '}';
    }


    public static class Data
    {
        private String title;

        private String others;

        private String orgid;

        private String location;

        private String subtitle;

        private String discription;

        public String getTitle ()
        {
            return title;
        }

        public void setTitle (String title)
        {
            this.title = title;
        }

        public String getOthers ()
        {
            return others;
        }

        public void setOthers (String others)
        {
            this.others = others;
        }

        public String getOrgid ()
        {
            return orgid;
        }

        public void setOrgid (String orgid)
        {
            this.orgid = orgid;
        }

        public String getLocation ()
        {
            return location;
        }

        public void setLocation (String location)
        {
            this.location = location;
        }

        public String getSubtitle ()
        {
            return subtitle;
        }

        public void setSubtitle (String subtitle)
        {
            this.subtitle = subtitle;
        }

        public String getDiscription ()
        {
            return discription;
        }

        public void setDiscription (String discription)
        {
            this.discription = discription;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [title = "+title+", others = "+others+", orgid = "+orgid+", location = "+location+", subtitle = "+subtitle+", discription = "+discription+"]";
        }
    }

}
