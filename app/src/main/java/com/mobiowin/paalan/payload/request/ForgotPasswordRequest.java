package com.mobiowin.paalan.payload.request;

/**
 * Created by yashika on 15/2/17.
 */

public class ForgotPasswordRequest {
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

    public static class Data
    {
        private String userid;

        private String password;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUserid ()
        {
            return userid;
        }

        public void setUserid (String userid)
        {
            this.userid = userid;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "userid='" + userid + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }



}
