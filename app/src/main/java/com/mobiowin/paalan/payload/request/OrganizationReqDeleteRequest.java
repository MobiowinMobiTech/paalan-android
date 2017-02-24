package com.mobiowin.paalan.payload.request;

import com.mobiowin.paalan.helper.Social;


public class OrganizationReqDeleteRequest {

    private String entity;

    private Data data;

    private String action;

    private String type;

    public static OrganizationReqDeleteRequest get(String orgId, String requestId) {

        Data data = new Data();
        data.setOrgid(orgId);
        data.setRequestid(requestId);

        OrganizationReqDeleteRequest reqDeleteAchievement = new OrganizationReqDeleteRequest();
        reqDeleteAchievement.setData(data);
        reqDeleteAchievement.setType(Social.REQUEST_TYPE);
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
        return "OrganizationReqDeleteRequest{" +
                "entity='" + entity + '\'' +
                ", data=" + data +
                ", action='" + action + '\'' +
                ", type='" + type + '\'' +
                '}';
    }


    public static class Data {
        private String orgid;

        private String requestid;

        public String getOrgid() {
            return orgid;
        }

        public void setOrgid(String orgid) {
            this.orgid = orgid;
        }

        public String getRequestid() {
            return requestid;
        }

        public void setRequestid(String requestid) {
            this.requestid = requestid;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "orgid='" + orgid + '\'' +
                    ", requestid='" + requestid + '\'' +
                    '}';
        }
    }

}
