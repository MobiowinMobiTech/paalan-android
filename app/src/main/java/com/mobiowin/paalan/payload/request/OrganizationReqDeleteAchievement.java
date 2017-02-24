package com.mobiowin.paalan.payload.request;

import com.mobiowin.paalan.helper.Social;

/**
 * Created on 6/1/17.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class OrganizationReqDeleteAchievement {

    private String entity;

    private Data data;

    private String action;

    private String type;

    public static OrganizationReqDeleteAchievement get(String orgId, String achievementId) {

        Data data = new Data();
        data.setOrgid(orgId);
        data.setAchievementid(achievementId);

        OrganizationReqDeleteAchievement reqDeleteAchievement = new OrganizationReqDeleteAchievement();
        reqDeleteAchievement.setData(data);
        reqDeleteAchievement.setType(Social.ACHIEVEMENT_TYPE);
        reqDeleteAchievement.setAction(Social.ACTION_DELETE);
        reqDeleteAchievement.setEntity(Social.ORG_ENTITY);
        return reqDeleteAchievement;
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
        return "OrganizationReqDeleteAchievement{" +
                "entity='" + entity + '\'' +
                ", data=" + data +
                ", action='" + action + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public static class Data {
        private String orgid;

        private String achievementid;

        public String getOrgid() {
            return orgid;
        }

        public void setOrgid(String orgid) {
            this.orgid = orgid;
        }

        public String getAchievementid() {
            return achievementid;
        }

        public void setAchievementid(String achievementid) {
            this.achievementid = achievementid;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "orgid='" + orgid + '\'' +
                    ", achievementid='" + achievementid + '\'' +
                    '}';
        }
    }

}
