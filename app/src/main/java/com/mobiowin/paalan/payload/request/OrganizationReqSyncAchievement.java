package com.mobiowin.paalan.payload.request;

import com.mobiowin.paalan.helper.Social;


public class OrganizationReqSyncAchievement {

    private String entity;

    private Data data;

    private String action;

    private String type;

    public static OrganizationReqSyncAchievement get(String orgId, String lastsyncdate) {
        Data data = new Data();
        data.setOrgid(orgId);
        data.setLastsyncdate(lastsyncdate);

        OrganizationReqSyncAchievement syncAchievement = new OrganizationReqSyncAchievement();
        syncAchievement.setData(data);
        syncAchievement.setEntity(Social.ORG_ENTITY);
        syncAchievement.setAction(Social.EVENT_SYNC_ACTION);
        syncAchievement.setType(Social.ACHIEVEMENT_TYPE);
        return syncAchievement;
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
        return "OrganizationReqSyncAchievement{" +
                "entity='" + entity + '\'' +
                ", data=" + data +
                ", action='" + action + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public static class Data {

        private String orgid;
        private String lastsyncdate;

        public String getOrgid() {
            return orgid;
        }

        public void setOrgid(String orgid) {
            this.orgid = orgid;
        }

        public String getlLastsyncdate() {
            return lastsyncdate;
        }

        public void setLastsyncdate(String lastsyncdate) {
            this.lastsyncdate = lastsyncdate;
        }


        @Override
        public String toString() {
            return "Data{" +
                    "orgid='" + orgid + '\'' +
                    "lastsyncdate='" + lastsyncdate + '\'' +
                    '}';
        }
    }
}
