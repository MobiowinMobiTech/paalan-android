package com.phyder.paalan.payload.request;

import com.phyder.paalan.social.Social;

/**
 * Created by cmss on 11/1/17.
 */

public class RequestBroadcastNotification {

    private String entity;

    private Data data;

    private String action;

    private String type;


    public static RequestBroadcastNotification get(String orgId, String recordId,String notifyType) {

        Data data = new Data();
        data.setOrgid(orgId);
        data.setRecordid(recordId);
        data.setNotificationtype(notifyType);

        RequestBroadcastNotification reqBroadcast = new RequestBroadcastNotification();
        reqBroadcast.setData(data);
        reqBroadcast.setAction(Social.ACTION_SYNC_TYPE);
        reqBroadcast.setType(Social.ACTION_RECORD_TYPE);
        reqBroadcast.setEntity(Social.ORG_ENTITY);
        return reqBroadcast;
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
        return "RequestBroadcastNotification{" +
                "entity='" + entity + '\'' +
                ", data=" + data +
                ", action='" + action + '\'' +
                ", type='" + type + '\'' +
                '}';
    }


    public static class Data
    {
        private String notificationtype;

        private String orgid;

        private String recordid;

        public String getNotificationtype ()
        {
            return notificationtype;
        }

        public void setNotificationtype (String notificationtype)
        {
            this.notificationtype = notificationtype;
        }

        public String getOrgid ()
        {
            return orgid;
        }

        public void setOrgid (String orgid)
        {
            this.orgid = orgid;
        }

        public String getRecordid ()
        {
            return recordid;
        }

        public void setRecordid (String recordid)
        {
            this.recordid = recordid;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "notificationtype='" + notificationtype + '\'' +
                    ", orgid='" + orgid + '\'' +
                    ", recordid='" + recordid + '\'' +
                    '}';
        }
    }

}


