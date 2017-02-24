package com.mobiowin.paalan.payload.response;


public class OrgResNotificationDetailsRequest {


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
        private Orgreclist[] orgreclist;

        public Orgreclist[] getOrgreclist ()
        {
            return orgreclist;
        }

        public void setOrgreclist (Orgreclist[] orgreclist)
        {
            this.orgreclist = orgreclist;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [orgreclist = "+orgreclist+"]";
        }
    }


    public class Orgreclist
    {
        private String others;

        private String requestId;

        private String location;

        private String modifiedBy;

        private String discription;

        private String orgName;

        private String id;

        private String orgId;

        private String subTitle;

        private String title;

        private String createdBy;

        private String deleteFlag;

        private String modifyDt;

        private String createDt;

        public String getOthers ()
        {
            return others;
        }

        public void setOthers (String others)
        {
            this.others = others;
        }

        public String getRequestId ()
        {
            return requestId;
        }

        public void setRequestId (String requestId)
        {
            this.requestId = requestId;
        }

        public String getLocation ()
        {
            return location;
        }

        public void setLocation (String location)
        {
            this.location = location;
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

        public String getOrgName ()
    {
        return orgName;
    }

        public void setOrgName (String orgName)
        {
            this.orgName = orgName;
        }

        public String getId ()
        {
            return id;
        }

        public void setId (String id)
        {
            this.id = id;
        }

        public String getOrgId ()
        {
            return orgId;
        }

        public void setOrgId (String orgId)
        {
            this.orgId = orgId;
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

        public String getCreatedBy ()
        {
            return createdBy;
        }

        public void setCreatedBy (String createdBy)
        {
            this.createdBy = createdBy;
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
        public String toString() {
            return "Orgreclist{" +
                    "others='" + others + '\'' +
                    ", requestId='" + requestId + '\'' +
                    ", location='" + location + '\'' +
                    ", modifiedBy='" + modifiedBy + '\'' +
                    ", discription='" + discription + '\'' +
                    ", orgName='" + orgName + '\'' +
                    ", id='" + id + '\'' +
                    ", orgId='" + orgId + '\'' +
                    ", subTitle='" + subTitle + '\'' +
                    ", title='" + title + '\'' +
                    ", createdBy='" + createdBy + '\'' +
                    ", deleteFlag='" + deleteFlag + '\'' +
                    ", modifyDt='" + modifyDt + '\'' +
                    ", createDt='" + createDt + '\'' +
                    '}';
        }
    }
}
