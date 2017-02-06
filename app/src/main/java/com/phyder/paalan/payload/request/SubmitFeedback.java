package com.phyder.paalan.payload.request;

/**
 * Created by yashika on 6/2/17.
 */

public class SubmitFeedback {
    private String entity;

    private Data data;

    private String action;

    private String type;


    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        return "SubmitFeedback{" +
                "entity='" + entity + '\'' +
                ", data=" + data +
                ", action='" + action + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public static class Data
    {
        private String message;

        private String name;

        private String mobileno;

        private String emailid;

        public String getMessage ()
        {
            return message;
        }

        public void setMessage (String message)
        {
            this.message = message;
        }

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }

        public String getMobileno ()
        {
            return mobileno;
        }

        public void setMobileno (String mobileno)
        {
            this.mobileno = mobileno;
        }

        public String getEmailid ()
        {
            return emailid;
        }

        public void setEmailid (String emailid)
        {
            this.emailid = emailid;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [message = "+message+", name = "+name+", mobileno = "+mobileno+", emailid = "+emailid+"]";
        }
    }



}
