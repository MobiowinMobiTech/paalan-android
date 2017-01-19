package com.phyder.paalan.payload.response.organization;


public class OrgResSyncRequest {


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
        private Orgreqlist[] orgreqlist;

        public Orgreqlist[] getOrgreqlist ()
        {
            return orgreqlist;
        }

        public void setOrgreqlist (Orgreqlist[] orgreqlist)
        {
            this.orgreqlist = orgreqlist;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [orgreqlist = "+orgreqlist+"]";
        }
    }


    public class Orgreqlist
    {
        private String orgId;

        private String id;

        private String subTitle;

        private String title;

        private String others;

        private String createdBy;

        private String location;

        private String requestId;

        private String modifiedBy;

        private String discription;

        private String deleteFlag;

        private String modifyDt;

        private String createDt;

        public String getOrgId ()
        {
            return orgId;
        }

        public void setOrgId (String orgId)
        {
            this.orgId = orgId;
        }

        public String getId ()
        {
            return id;
        }

        public void setId (String id)
        {
            this.id = id;
        }

        public String getSubTitle ()
        {
            return subTitle;
        }

        public void setSubTitle (String subTitle)
        {
            this.subTitle = subTitle;
        }

        public String getTitle ()
        {
            return title;
        }

        public void setTitle (String title)
        {
            this.title = title;
        }

        public String getOthers ()
        {
            return others;
        }

        public void setOthers (String others)
        {
            this.others = others;
        }

        public String getCreatedBy ()
        {
            return createdBy;
        }

        public void setCreatedBy (String createdBy)
        {
            this.createdBy = createdBy;
        }

        public String getLocation ()
        {
            return location;
        }

        public void setLocation (String location)
        {
            this.location = location;
        }

        public String getRequestId ()
        {
            return requestId;
        }

        public void setRequestId (String requestId)
        {
            this.requestId = requestId;
        }

        public String getModifiedBy ()
        {
            return modifiedBy;
        }

        public void setModifiedBy (String modifiedBy)
        {
            this.modifiedBy = modifiedBy;
        }

        public String getDiscription ()
        {
            return discription;
        }

        public void setDiscription (String discription)
        {
            this.discription = discription;
        }

        public String getDeleteFlag ()
        {
            return deleteFlag;
        }

        public void setDeleteFlag (String deleteFlag)
        {
            this.deleteFlag = deleteFlag;
        }

        public String getModifyDt ()
        {
            return modifyDt;
        }

        public void setModifyDt (String modifyDt)
        {
            this.modifyDt = modifyDt;
        }

        public String getCreateDt ()
        {
            return createDt;
        }

        public void setCreateDt (String createDt)
        {
            this.createDt = createDt;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [orgId = "+orgId+", id = "+id+", subTitle = "+subTitle+", title = "+title+", others = "+others+", createdBy = "+createdBy+", location = "+location+", requestId = "+requestId+", modifiedBy = "+modifiedBy+", discription = "+discription+", deleteFlag = "+deleteFlag+", modifyDt = "+modifyDt+", createDt = "+createDt+"]";
        }
    }
}
