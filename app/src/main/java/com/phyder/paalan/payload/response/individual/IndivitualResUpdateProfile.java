package com.phyder.paalan.payload.response.individual;

/**
 * Created on 22/12/16.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class IndivitualResUpdateProfile {

    private String sts;

    private Data data;

    public String getSts ()
    {
        return sts;
    }

    public void setSts (String sts)
    {
        this.sts = sts;
    }

    public Data getData ()
    {
        return data;
    }

    public void setData (Data data)
    {
        this.data = data;
    }

    @Override
    public String toString() {
        return "IndivitualResUpdateProfile{" +
                "sts='" + sts + '\'' +
                ", data=" + data +
                '}';
    }
    public class Data
    {
        private String id;

        private String tkn;

        private String sts;

        private String msg;

        public String getId ()
        {
            return id;
        }

        public void setId (String id)
        {
            this.id = id;
        }

        public String getTkn ()
        {
            return tkn;
        }

        public void setTkn (String tkn)
        {
            this.tkn = tkn;
        }

        public String getSts ()
        {
            return sts;
        }

        public void setSts (String sts)
        {
            this.sts = sts;
        }

        public String getMsg ()
        {
            return msg;
        }

        public void setMsg (String msg)
        {
            this.msg = msg;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [id = "+id+", tkn = "+tkn+", sts = "+sts+", msg = "+msg+"]";
        }
    }
}
