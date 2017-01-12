//package com.phyder.paalan.payload.request.organization;
//
//import com.phyder.paalan.social.Social;
//
///**
// * Created on 22/12/16.
// * Author Dharmendra
// * Company CmssPhyder
// */
//
//public class OrganizationReqLogin {
//
//    private String entity;
//
//    private Data data;
//
//    private String type;
//
//    private String action;
//
//    public static OrganizationReqLogin get(String imeino, String userId, String password) {
//
//        Data data = new Data();
//        data.setImeino(imeino);
//        data.setUserid(userId);
//        data.setPassword(password);
//
//        OrganizationReqLogin organizationReqLogin = new OrganizationReqLogin();
//        organizationReqLogin.setData(data);
//        organizationReqLogin.setAction(Social.SUBMIT_ACTION);
//        organizationReqLogin.setType(Social.ORG_LOGIN_TYPE);
//        organizationReqLogin.setEntity(Social.ORG_ENTITY);
//        return organizationReqLogin;
//    }
//
//    public String getEntity() {
//        return entity;
//    }
//
//    public void setEntity(String entity) {
//        this.entity = entity;
//    }
//
//    public Data getData() {
//        return data;
//    }
//
//    public void setData(Data data) {
//        this.data = data;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getAction() {
//        return action;
//    }
//
//    public void setAction(String action) {
//        this.action = action;
//    }
//
//    @Override
//    public String toString() {
//        return "OrganizationReqLogin{" +
//                "entity='" + entity + '\'' +
//                ", data=" + data +
//                ", type='" + type + '\'' +
//                ", action='" + action + '\'' +
//                '}';
//    }
//
//    public static class Data {
//        private String imeino;
//
//        private String userid;
//
//        private String password;
//
//        public String getImeino() {
//            return imeino;
//        }
//
//        public void setImeino(String imeino) {
//            this.imeino = imeino;
//        }
//
//        public String getUserid() {
//            return userid;
//        }
//
//        public void setUserid(String userid) {
//            this.userid = userid;
//        }
//
//        public String getPassword() {
//            return password;
//        }
//
//        public void setPassword(String password) {
//            this.password = password;
//        }
//
//        @Override
//        public String toString() {
//            return "ClassPojo [imeino = " + imeino + ", userid = " + userid + ", password = " + password + "]";
//        }
//    }
//}
//
//
