package com.phyder.paalan.payload.request.individual;

import com.phyder.paalan.social.Social;

/**
 * Created on 22/12/16.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class IndivitualReqLogin {

    private String entity;

    private Data data;

    private String type;

    public static IndivitualReqLogin get(String userid, String imeino, String password) {
        Data data = new Data();
        data.setImeino(imeino);
        data.setUserid(userid);
        data.setPassword(password);

        IndivitualReqLogin indivitualReqLogin = new IndivitualReqLogin();
        indivitualReqLogin.setData(data);
        indivitualReqLogin.setEntity(Social.ORG_ENTITY);
//        indivitualReqLogin.setType(Social.IND_LOGIN_TYPE);
        indivitualReqLogin.setType(Social.ORG_LOGIN_TYPE);
        return indivitualReqLogin;
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

    @Override
    public String toString() {
        return "IndivitualReqLogin{" +
                "entity='" + entity + '\'' +
                ", data=" + data +
                ", type='" + type + '\'' +
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
            return "Data{" +
                    "userid='" + userid + '\'' +
                    ", imeino='" + imeino + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }

}
