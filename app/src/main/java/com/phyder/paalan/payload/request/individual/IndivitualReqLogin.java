package com.phyder.paalan.payload.request.individual;

/**
 * Created on 22/12/16.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class IndivitualReqLogin {

    private String entity;

    private Data data;

    private String type;

    public IndivitualReqLogin(String entity, Data data, String type) {
        this.entity = entity;
        this.data = data;
        this.type = type;
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

    public class Data {
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
                    "imeino='" + imeino + '\'' +
                    ", userid='" + userid + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }

}
