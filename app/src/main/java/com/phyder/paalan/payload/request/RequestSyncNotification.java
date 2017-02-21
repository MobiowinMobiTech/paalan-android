package com.phyder.paalan.payload.request;

/**
 * Created by yashika on 2/2/17.
 */

public class RequestSyncNotification {
    private String entity;

    private Data data;

    private String action;

    private String type;

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
    public String toString()
    {
        return "ClassPojo [entity = "+entity+", data = "+data+", action = "+action+", type = "+type+"]";
    }

    public static RequestSyncNotification get(String syncAction, String entityApp, String type,
                                              String model, String notificationId,
                                              String osVersion, String deviceId,
                                              String latitude,String longitude) {

        Data data = new Data();
        data.setDeviceid(deviceId);
        data.setDevicemodel(model);
        data.setImeino("");
        data.setNotificationid(notificationId);
        data.setOsversion(osVersion);
        data.setLatitude(latitude);
        data.setLongitude(longitude);
        RequestSyncNotification requestSyncNotification = new RequestSyncNotification();
        requestSyncNotification.setType(type);
        requestSyncNotification.setEntity(entityApp);
        requestSyncNotification.setAction(syncAction);
        requestSyncNotification.setData(data);


        return requestSyncNotification;
    }

    public static class Data
    {
        private String devicemodel;

        private String imeino;

        private String notificationid;

        private String osversion;

        private String deviceid;

        private String longitude;

        private String latitude;


        public String getDevicemodel ()
        {
            return devicemodel;
        }

        public void setDevicemodel (String devicemodel)
        {
            this.devicemodel = devicemodel;
        }

        public String getImeino ()
        {
            return imeino;
        }

        public void setImeino (String imeino)
        {
            this.imeino = imeino;
        }

        public String getNotificationid ()
        {
            return notificationid;
        }

        public void setNotificationid (String notificationid)
        {
            this.notificationid = notificationid;
        }

        public String getOsversion ()
        {
            return osversion;
        }

        public void setOsversion (String osversion)
        {
            this.osversion = osversion;
        }

        public String getDeviceid ()
        {
            return deviceid;
        }

        public void setDeviceid (String deviceid)
        {
            this.deviceid = deviceid;
        }

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

        @Override
        public String toString() {
            return "Data{" +
                    "devicemodel='" + devicemodel + '\'' +
                    ", imeino='" + imeino + '\'' +
                    ", notificationid='" + notificationid + '\'' +
                    ", osversion='" + osversion + '\'' +
                    ", deviceid='" + deviceid + '\'' +
                    ", longitude='" + longitude + '\'' +
                    ", latitude='" + latitude + '\'' +
                    '}';
        }
    }



}
