package com.mobiowin.paalan.payload.response;

/**
 * Created by yashika on 15/2/17.
 */

public class ForgotPasswordResponse {
    private String message;

    private String status;

    private Data[] data;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public Data[] getData ()
    {
        return data;
    }

    public void setData (Data[] data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", status = "+status+", data = "+data+"]";
    }

    public class Data
    {
        private String otp;
        private String errmsg;

        public String getErrmsg() {
            return errmsg;
        }

        public void setErrmsg(String errmsg) {
            this.errmsg = errmsg;
        }

        public String getOtp ()
        {
            return otp;
        }

        public void setOtp (String otp)
        {
            this.otp = otp;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "otp='" + otp + '\'' +
                    ", errmsg='" + errmsg + '\'' +
                    '}';
        }
    }



}
