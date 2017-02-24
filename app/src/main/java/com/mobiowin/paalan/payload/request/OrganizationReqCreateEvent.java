package com.mobiowin.paalan.payload.request;

import com.mobiowin.paalan.helper.Social;

/**
 * Created on 5/1/17.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class OrganizationReqCreateEvent {

    private String entity;

    private Data data;

    private String action;

    private String type;

    public static OrganizationReqCreateEvent get(String orgId, String eventId, String name, String title, String subTitle,
                                                 String description, String startDate, String endDate,
                                                 String other, String category, String location, String action, String notifyId) {

        Data data = new Data();
        data.setNotificationid(notifyId);
        data.setOrgid(orgId);
        data.setEventid(eventId);
        data.setName(name);
        data.setTitle(title);
        data.setSubtitle(subTitle);
        data.setDiscription(description);
        data.setOthers(other);
        data.setCategory(category);
        data.setLocation(location);
        data.setStartdate(startDate);
        data.setEnddate(endDate);

        OrganizationReqCreateEvent createEvent = new OrganizationReqCreateEvent();
        createEvent.setData(data);
        createEvent.setEntity(Social.ORG_ENTITY);
        createEvent.setAction(action);
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
        return "OrganizationReqCreateEvent{" +
                "entity='" + entity + '\'' +
                ", data=" + data +
                ", action='" + action + '\'' +
                ", type='" + type + '\'' +
                '}';
    }



    public static class Data {

        private String orgName;

        private String title;

        private String others;

        private String startdate;

        private String orgid;

        private String location;

        private String subtitle;

        private String discription;

        private String category;

        private String enddate;

        private String eventid;

        private String notificationid;

        public String getName() {
            return orgName;
        }

        public void setName(String orgName) {
            this.orgName = orgName;
        }

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

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
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
        public String getNotificationid() {
            return notificationid;
        }

        public void setNotificationid(String notificationid) {
            this.notificationid = notificationid;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "orgName='" + orgName + '\'' +
                    "title='" + title + '\'' +
                    ", other='" + others + '\'' +
                    ", startdate='" + startdate + '\'' +
                    ", orgid='" + orgid + '\'' +
                    ", location='" + location + '\'' +
                    ", subtitle='" + subtitle + '\'' +
                    ", discription='" + discription + '\'' +
                    ", category='" + category + '\'' +
                    ", enddate='" + enddate + '\'' +
                    ", eventid='" + eventid + '\'' +
                    ", notificationid='" + notificationid + '\'' +
                    '}';
        }
    }
}
