package com.phyder.paalan.payload.request;

import com.phyder.paalan.social.Social;

/**
 * Created by cmss on 11/1/17.
 */

public class RequestLogin {

    private String entity;

    private Data data;

    private String type;

    private String action;


    public static RequestLogin get(String userId, String password) {

        Data data = new Data();
        data.setImeino("");
        data.setUserid(userId);
        data.setPassword(password);

        RequestLogin reqLogin = new RequestLogin();
        reqLogin.setData(data);
        reqLogin.setAction(Social.SUBMIT_ACTION);
        reqLogin.setType(Social.LOGIN_TYPE);
        reqLogin.setEntity(Social.ORG_ENTITY);
        return reqLogin;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "RequestLogin{" +
                "entity='" + entity + '\'' +
                ", data=" + data +
                ", type='" + type + '\'' +
                ", action='" + action + '\'' +
                '}';
    }

    public static class Data {
        private String imeino;

        private String userid;

        private String password;

        public String getImeino() {
            return imeino;
        }

        public void setImeino(String imeino) {
            this.imeino = imeino;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "ClassPojo [imeino = " + imeino + ", userid = " + userid + ", password = " + password + "]";
        }
    }
}


