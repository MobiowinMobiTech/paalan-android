package com.phyder.paalan.payload.request.organization;

import com.phyder.paalan.social.Social;

/**
 * Created on 6/1/17.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class OrgReqDeleteAchievement {

    private String entity;

    private Data data;

    private String action;

    private String type;

    public static OrgReqDeleteAchievement get(String orgId, String achievementId) {

        Data data = new Data();
        data.setOrgid(orgId);
        data.setAchievementid(achievementId);

        OrgReqDeleteAchievement reqDeleteAchievement = new OrgReqDeleteAchievement();
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
        return "OrgReqDeleteAchievement{" +
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
                    ", eventid='" + achievementid + '\'' +
                    '}';
        }
    }

}
