package com.phyder.paalan.payload.response.organization;



public class OrgResCreateRequest {

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
        private String requestid;

        public String getRequestid ()
        {
            return requestid;
        }

        public void setRequestid (String requestid)
        {
            this.requestid = requestid;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [requestid = "+requestid+"]";
        }
    }

}




