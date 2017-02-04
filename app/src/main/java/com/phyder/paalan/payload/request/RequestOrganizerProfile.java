package com.phyder.paalan.payload.request;

import com.phyder.paalan.social.Social;

/**
 * Created by cmss on 4/2/17.
 */

public class RequestOrganizerProfile {


    private String entity;

    private Data data;

    private String action;

    private String type;


    public static RequestOrganizerProfile get(String orgId) {

        Data data = new Data();
        data.setOrgid(orgId);

        RequestOrganizerProfile reqProfile = new RequestOrganizerProfile();
        reqProfile.setData(data);
        reqProfile.setAction(Social.VIEW_ACTION);
        reqProfile.setType(Social.PROFILE_TYPE);
        reqProfile.setEntity(Social.IND_ENTITY);
        return reqProfile;
    }



    public String getEntity ()
    {
        return entity;
    }

    public void setEntity (String entity)
    {
        this.entity = entity;
    }

    public Data getData ()
    {
        return data;
    }

    public void setData (Data data)
    {
        this.data = data;
    }

    public String getAction ()
    {
        return action;
    }

    public void setAction (String action)
    {
        this.action = action;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RequestOrganizerProfile{" +
                "entity='" + entity + '\'' +
                ", data=" + data +
                ", action='" + action + '\'' +
                ", type='" + type + '\'' +
                '}';
    }


    public static class Data
    {
        private String orgid;

        public String getOrgid ()
        {
            return orgid;
        }

        public void setOrgid (String orgid)
        {
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
