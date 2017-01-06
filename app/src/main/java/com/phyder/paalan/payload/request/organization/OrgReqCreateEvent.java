package com.phyder.paalan.payload.request.organization;

import com.phyder.paalan.social.Social;

/**
 * Created on 5/1/17.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class OrgReqCreateEvent {

    private String entity;

    private Data data;

    private String action;

    private String type;

    public static OrgReqCreateEvent get(String orgId, String eventId, String title, String subTitle, String description, String startDate, String endDate, String location, String other) {

        Data data = new Data();
        data.setOrgid(orgId);
        data.setEventid(eventId);
        data.setTitle(title);
        data.setSubtitle(subTitle);
        data.setDiscription(description);
        data.setStartdate(startDate);
        data.setEnddate(endDate);
        data.setLocation(location);
        data.setOthers(other);

        OrgReqCreateEvent createEvent = new OrgReqCreateEvent();
        createEvent.setData(data);
        createEvent.setEntity(Social.ORG_ENTITY);
        createEvent.setAction(Social.EVENT_ACTION);
        createEvent.setType(Social.ACTION_TYPE);
        return createEvent;
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
        return "OrgReqCreateEvent{" +
                "entity='" + entity + '\'' +
                ", data=" + data +
                ", action='" + action + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public static class Data {
        private String title;

        private String others;

        private String startdate;

        private String orgid;

        private String location;

        private String subtitle;

        private String discription;

        private String enddate;

        private String eventid;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOthers() {
            return others;
        }

        public void setOthers(String others) {
            this.others = others;
        }

        public String getStartdate() {
            return startdate;
        }

        public void setStartdate(String startdate) {
            this.startdate = startdate;
        }

        public String getOrgid() {
            return orgid;
        }

        public void setOrgid(String orgid) {
            this.orgid = orgid;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getDiscription() {
            return discription;
        }

        public void setDiscription(String discription) {
            this.discription = discription;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public String getEventid() {
            return eventid;
        }

        public void setEventid(String eventid) {
            this.eventid = eventid;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "title='" + title + '\'' +
                    ", other='" + others + '\'' +
                    ", startdate='" + startdate + '\'' +
                    ", orgid='" + orgid + '\'' +
                    ", location='" + location + '\'' +
                    ", subtitle='" + subtitle + '\'' +
                    ", discription='" + discription + '\'' +
                    ", enddate='" + enddate + '\'' +
                    ", eventid='" + eventid + '\'' +
                    '}';
        }
    }
}
