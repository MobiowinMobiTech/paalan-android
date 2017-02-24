package com.mobiowin.paalan.payload.request;

import com.mobiowin.paalan.helper.Social;

/**
 * Created by Yashica on 5/1/17.
 * Company PhyderCmss
 */

public class OrganizationReqDeleteEvent {
    private String entity;

    private Data data;

    private String action;

    private String type;

    public static OrganizationReqDeleteEvent get(String orgid, String eventid){

        Data data = new Data();
        data.setOrgid(orgid);
        data.setEventid(eventid);

        OrganizationReqDeleteEvent deleteEvent= new OrganizationReqDeleteEvent();
        deleteEvent.setData(data);
        deleteEvent.setEntity(Social.ORG_ENTITY);
        deleteEvent.setAction(Social.ACTION_DELETE);
        deleteEvent.setType(Social.EVENT_TYPE);
        return deleteEvent;

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
        return "OrgResDeleteEvent{" +
                "entity='" + entity + '\'' +
                ", data=" + data +
                ", action='" + action + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public static class Data {
        private String orgid;

        private String eventid;

        public String getOrgid() {
            return orgid;
        }

        public void setOrgid(String orgid) {
            this.orgid = orgid;
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
                    "orgid='" + orgid + '\'' +
                    ", eventid='" + eventid + '\'' +
                    '}';
        }
    }
}
