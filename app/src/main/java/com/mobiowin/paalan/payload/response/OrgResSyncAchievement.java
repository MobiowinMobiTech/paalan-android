package com.mobiowin.paalan.payload.response;

/**
 * Created on 6/1/17.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class OrgResSyncAchievement {

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
        private Orglist[] orglist;

        public Orglist[] getOrglist ()
        {
            return orglist;
        }

        public void setOrglist (Orglist[] orglist)
        {
            this.orglist = orglist;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [orglist = "+orglist+"]";
        }


    }



    public class Orglist
    {
        private String others;

        private String modifiedBy;

        private String achievementId;

        private String discription;

        private String id;

        private String orgId;

        private String subTitle;

        private String orgName;

        private String title;

        private String image2;

        private String image1;

        private String createdBy;

        private String deleteFlag;

        private String image4;

        private String modifyDt;

        private String image3;

        private String createDt;

        public String getOthers ()
        {
            return others;
        }

        public void setOthers (String others)
        {
            this.others = others;
        }

        public String getModifiedBy ()
        {
            return modifiedBy;
        }

        public void setModifiedBy (String modifiedBy)
        {
            this.modifiedBy = modifiedBy;
        }

        public String getAchievementId ()
        {
            return achievementId;
        }

        public void setAchievementId (String achievementId)
        {
            this.achievementId = achievementId;
        }

        public String getDiscription ()
        {
            return discription;
        }

        public void setDiscription (String discription)
        {
            this.discription = discription;
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

        public String getName ()
        {
            return orgName;
        }

        public void setName (String orgName)
        {
            this.orgName = orgName;
        }

        public String getImage2 ()
        {
            return image2;
        }

        public void setImage2 (String image2)
        {
            this.image2 = image2;
        }

        public String getImage1 ()
        {
            return image1;
        }

        public void setImage1 (String image1)
        {
            this.image1 = image1;
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

        public String getImage4 ()
        {
            return image4;
        }

        public void setImage4 (String image4)
        {
            this.image4 = image4;
        }

        public String getModifyDt ()
        {
            return modifyDt;
        }

        public void setModifyDt (String modifyDt)
        {
            this.modifyDt = modifyDt;
        }

        public String getImage3 ()
        {
            return image3;
        }

        public void setImage3 (String image3)
        {
            this.image3 = image3;
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
            return "Orglist{" +
                    "others='" + others + '\'' +
                    ", modifiedBy='" + modifiedBy + '\'' +
                    ", achievementId='" + achievementId + '\'' +
                    ", discription='" + discription + '\'' +
                    ", id='" + id + '\'' +
                    ", orgId='" + orgId + '\'' +
                    ", subTitle='" + subTitle + '\'' +
                    ", orgName='" + orgName + '\'' +
                    ", title='" + title + '\'' +
                    ", image2='" + image2 + '\'' +
                    ", image1='" + image1 + '\'' +
                    ", createdBy='" + createdBy + '\'' +
                    ", deleteFlag='" + deleteFlag + '\'' +
                    ", image4='" + image4 + '\'' +
                    ", modifyDt='" + modifyDt + '\'' +
                    ", image3='" + image3 + '\'' +
                    ", createDt='" + createDt + '\'' +
                    '}';
        }
    }
}
