package com.phyder.paalan.payload.request.organization;

import com.phyder.paalan.social.Social;

/**
 * Created on 6/1/17.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class OrgReqSyncAchievement {

    private String entity;

    private Data data;

    private String action;

    private String type;

    public static OrgReqSyncAchievement get(String orgId) {
        Data data = new Data();
        data.setOrgid(orgId);

        OrgReqSyncAchievement syncAchievement = new OrgReqSyncAchievement();
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
        return "OrgReqSyncAchievement{" +
                "entity='" + entity + '\'' +
                ", data=" + data +
                ", action='" + action + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public static class Data {
        private String orgid;

        public String getOrgid() {
            return orgid;
        }

        public void setOrgid(String orgid) {
            this.orgid = orgid;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "orgid='" + orgid + '\'' +
                    '}';
        }
    }
}
