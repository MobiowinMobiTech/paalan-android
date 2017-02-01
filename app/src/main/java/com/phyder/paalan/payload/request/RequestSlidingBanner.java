package com.phyder.paalan.payload.request;

import com.phyder.paalan.social.Social;

/**
 * Created on 23/1/17.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class RequestSlidingBanner {

    private String entity;

    private Data data;

    private String action;

    private String type;


    public static RequestSlidingBanner get(String latitude, String longitude, String lastsyncdate) {

        Data data = new Data();
        data.setLatitude(latitude);
        data.setLongitude(longitude);
        data.setLastsyncdate(lastsyncdate);

        RequestSlidingBanner requestSlidingBanner = new RequestSlidingBanner();

        requestSlidingBanner.setData(data);
        requestSlidingBanner.setAction(Social.ACTION_SYNC_TYPE);
        requestSlidingBanner.setEntity(Social.ENTITY_APP);
        requestSlidingBanner.setType(Social.APP_INIT_TYPE);
        return requestSlidingBanner;
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
        return "RequestSlidingBanner{" +
                "entity='" + entity + '\'' +
                ", data=" + data +
                ", action='" + action + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public static class Data {
        private String longitude;

        private String latitude;

        private String lastsyncdate;

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLastsyncdate() {
            return lastsyncdate;
        }

        public void setLastsyncdate(String lastsyncdate) {
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
