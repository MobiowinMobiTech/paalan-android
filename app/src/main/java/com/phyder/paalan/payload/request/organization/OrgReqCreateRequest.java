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

    public static OrgReqCreateRequest get(String orgs, String reqId,String name,String title, String subTitle,String description,
                                          String others,String location, String action,String notifyId) {

        Data data = new Data();
        data.setNotificationId(notifyId);
        data.setOrgid(orgs);
        data.setReqid(reqId);
        data.setName(name);
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
        private String orgName;

        private String title;

        private String others;

        private String orgid;

        private String requestid;

        private String location;

        private String subtitle;

        private String discription;

        private String notificationid;

        public String getTitle ()
        {
            return title;
        }

        public void setTitle (String title)
        {
            this.title = title;
        }

        public String getName ()
        {
            return orgName;
        }

        public void setName (String orgName)
        {
            this.orgName = orgName;
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

        public String getReqid ()
        {
            return requestid;
        }

        public void setReqid (String requestid)
        {
            this.requestid = requestid;
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

        public String getNotificationId()
        {
            return notificationid;
        }

        public void setNotificationId (String notificationid)
        {
            this.notificationid = notificationid;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "orgName='" + orgName + '\'' +
                    "title='" + title + '\'' +
                    ", others='" + others + '\'' +
                    ", orgid='" + orgid + '\'' +
                    ", requestid='" + requestid + '\'' +
                    ", location='" + location + '\'' +
                    ", subtitle='" + subtitle + '\'' +
                    ", discription='" + discription + '\'' +
                    ", notificationid='" + notificationid + '\'' +
                    '}';
        }
    }

}
