package com.mobiowin.paalan.payload.request;

import com.mobiowin.paalan.helper.Social;

/**
 * Created by cmss on 31/1/17.
 */

public class RequestIndDashboard {


    private String entity;

    private Data data;

    private String action;

    private String type;


    public static RequestIndDashboard get(String lat, String longs, String lastsyncdate) {

        RequestIndDashboard.Data data = new RequestIndDashboard.Data();
        data.setLatitude(lat);
        data.setLongitude(longs);
        data.setLastsyncdate(lastsyncdate);

        RequestIndDashboard syncIndDash = new RequestIndDashboard();
        syncIndDash.setData(data);
        syncIndDash.setAction(Social.SUBMIT_ACTION);
        syncIndDash.setEntity(Social.IND_ENTITY);
        syncIndDash.setType(Social.LOGIN_TYPE);
        return syncIndDash;
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
        return "RequestIndDashboard{" +
                "entity='" + entity + '\'' +
                ", data=" + data +
                ", action='" + action + '\'' +
                ", type='" + type + '\'' +
                '}';
    }




    public static class Data
    {
        private String longitude;

        private String latitude;

        private String lastsyncdate;

        public String getLongitude ()
        {
            return longitude;
        }

        public void setLongitude (String longitude)
        {
            this.longitude = longitude;
        }

        public String getLatitude ()
        {
            return latitude;
        }

        public void setLatitude (String latitude)
        {
            this.latitude = latitude;
        }

        public String getLastsyncdate ()
        {
            return lastsyncdate;
        }

        public void setLastsyncdate (String lastsyncdate)
        {
            this.lastsyncdate = lastsyncdate;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "longitude='" + longitude + '\'' +
                    ", latitude='" + latitude + '\'' +
                    ", lastsyncdate='" + lastsyncdate + '\'' +
                    '}';
        }
    }


}
