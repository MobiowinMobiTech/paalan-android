package com.phyder.paalan.payload.response;

/**
 * Created on 23/1/17.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class ResponseInitialData {
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



    public class Data{
        private Bannerlist[] bannerlist;

        private String lastsyncdate;

        private String[] broadcasttopiclist;

        private Screenlist[] screenlist;

        public Bannerlist[] getBannerlist ()
        {
            return bannerlist;
        }

        public void setBannerlist (Bannerlist[] bannerlist)
        {
            this.bannerlist = bannerlist;
        }

        public String getLastsyncdate ()
        {
            return lastsyncdate;
        }

        public void setLastsyncdate (String lastsyncdate)
        {
            this.lastsyncdate = lastsyncdate;
        }

        public String[] getBroadcasttopiclist ()
        {
            return broadcasttopiclist;
        }

        public void setBroadcasttopiclist (String[] broadcasttopiclist)
        {
            this.broadcasttopiclist = broadcasttopiclist;
        }

        public Screenlist[] getScreenlist ()
        {
            return screenlist;
        }

        public void setScreenlist (Screenlist[] screenlist)
        {
            this.screenlist = screenlist;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [bannerlist = "+bannerlist+", lastsyncdate = "+lastsyncdate+", broadcasttopiclist = "+broadcasttopiclist+", screenlist = "+screenlist+"]";
        }
    }

    public class Bannerlist{
        private String id;

        private String others;

        private String createdBy;

        private String location;

        private String modifiedBy;

        private String bannerLink;

        private String bannerName;

        private String bannerId;

        private String discription;

        private String deleteFlag;

        private String modifyDt;

        private String createDt;

        public String getId ()
        {
            return id;
        }

        public void setId (String id)
        {
            this.id = id;
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

        public String getModifiedBy ()
    {
        return modifiedBy;
    }

        public void setModifiedBy (String modifiedBy)
        {
            this.modifiedBy = modifiedBy;
        }

        public String getBannerLink ()
        {
            return bannerLink;
        }

        public void setBannerLink (String bannerLink)
        {
            this.bannerLink = bannerLink;
        }

        public String getBannerName ()
        {
            return bannerName;
        }

        public void setBannerName (String bannerName)
        {
            this.bannerName = bannerName;
        }

        public String getBannerId ()
        {
            return bannerId;
        }

        public void setBannerId (String bannerId)
        {
            this.bannerId = bannerId;
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
            return "ClassPojo [id = "+id+", others = "+others+", createdBy = "+createdBy+", location = "+location+", modifiedBy = "+modifiedBy+", bannerLink = "+bannerLink+", bannerName = "+bannerName+", bannerId = "+bannerId+", discription = "+discription+", deleteFlag = "+deleteFlag+", modifyDt = "+modifyDt+", createDt = "+createDt+"]";
        }
    }

    public class Screenlist{
        private String screenId;

        private String screenTxt;

        public String getScreenTxt() {
            return screenTxt;
        }

        public void setScreenTxt(String screenTxt) {
            this.screenTxt = screenTxt;
        }

        private String id;

        private String createdBy;

        private String freeText1;

        private String modifiedBy;

        private String screenName;

        private String screenImgLink;

        private String deleteFlag;

        private String modifyDt;

        private String screenSeq;

        private String createDt;

        public String getScreenId ()
        {
            return screenId;
        }

        public void setScreenId (String screenId)
        {
            this.screenId = screenId;
        }

        public String getId ()
        {
            return id;
        }

        public void setId (String id)
        {
            this.id = id;
        }

        public String getCreatedBy ()
        {
            return createdBy;
        }

        public void setCreatedBy (String createdBy)
        {
            this.createdBy = createdBy;
        }

        public String getFreeText1 ()
        {
            return freeText1;
        }

        public void setFreeText1 (String freeText1)
        {
            this.freeText1 = freeText1;
        }

        public String getModifiedBy ()
        {
            return modifiedBy;
        }

        public void setModifiedBy (String modifiedBy)
        {
            this.modifiedBy = modifiedBy;
        }

        public String getScreenName ()
        {
            return screenName;
        }

        public void setScreenName (String screenName)
        {
            this.screenName = screenName;
        }

        public String getScreenImgLink ()
        {
            return screenImgLink;
        }

        public void setScreenImgLink (String screenImgLink)
        {
            this.screenImgLink = screenImgLink;
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

        public String getScreenSeq ()
        {
            return screenSeq;
        }

        public void setScreenSeq (String screenSeq)
        {
            this.screenSeq = screenSeq;
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
            return "Screenlist{" +
                    "screenId='" + screenId + '\'' +
                    ", screenTxt='" + screenTxt + '\'' +
                    ", id='" + id + '\'' +
                    ", createdBy='" + createdBy + '\'' +
                    ", freeText1='" + freeText1 + '\'' +
                    ", modifiedBy='" + modifiedBy + '\'' +
                    ", screenName='" + screenName + '\'' +
                    ", screenImgLink='" + screenImgLink + '\'' +
                    ", deleteFlag='" + deleteFlag + '\'' +
                    ", modifyDt='" + modifyDt + '\'' +
                    ", screenSeq='" + screenSeq + '\'' +
                    ", createDt='" + createDt + '\'' +
                    '}';
        }
    }

}
