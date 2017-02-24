package com.mobiowin.paalan.payload.response;

import java.util.Arrays;

/**
 * Created by cmss on 4/2/17.
 */

public class ResponseOrganizerProfile {

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
    public String toString() {
        return "ResponseOrganizerProfile{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }


    public class Data
    {
        private Orgprofilelist[] orgprofilelist;

        public Orgprofilelist[] getOrgprofilelist ()
        {
            return orgprofilelist;
        }

        public void setOrgprofilelist (Orgprofilelist[] orgprofilelist)
        {
            this.orgprofilelist = orgprofilelist;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "orgprofilelist=" + Arrays.toString(orgprofilelist) +
                    '}';
        }
    }


    public class Orgprofilelist
    {
        private String isGovtRegister;

        private String dpImgLink;

        private String modifiedBy;

        private String registrationNo;

        private String fbLink;

        private String id;

        private String orgId;

        private String websiteLink;

        private String emailId;

        private String createdBy;

        private String address;

        private String twitterLink;

        private String presenceArea;

        private String name;

        private String role;

        private String imeiNo;

        private String deleteFlag;

        private String modifyDt;

        private String linkedinLink;

        private String createDt;

        private String mobileNo;

        private String isNewsLetter;

        public String getIsGovtRegister ()
        {
            return isGovtRegister;
        }

        public void setIsGovtRegister (String isGovtRegister)
        {
            this.isGovtRegister = isGovtRegister;
        }

        public String getDpImgLink ()
        {
            return dpImgLink;
        }

        public void setDpImgLink (String dpImgLink)
        {
            this.dpImgLink = dpImgLink;
        }

        public String getModifiedBy ()
        {
            return modifiedBy;
        }

        public void setModifiedBy (String modifiedBy)
        {
            this.modifiedBy = modifiedBy;
        }

        public String getRegistrationNo ()
        {
            return registrationNo;
        }

        public void setRegistrationNo (String registrationNo)
        {
            this.registrationNo = registrationNo;
        }

        public String getFbLink ()
        {
            return fbLink;
        }

        public void setFbLink (String fbLink)
        {
            this.fbLink = fbLink;
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

        public String getWebsiteLink ()
        {
            return websiteLink;
        }

        public void setWebsiteLink (String websiteLink)
        {
            this.websiteLink = websiteLink;
        }

        public String getEmailId ()
        {
            return emailId;
        }

        public void setEmailId (String emailId)
        {
            this.emailId = emailId;
        }

        public String getCreatedBy ()
        {
            return createdBy;
        }

        public void setCreatedBy (String createdBy)
        {
            this.createdBy = createdBy;
        }

        public String getAddress ()
        {
            return address;
        }

        public void setAddress (String address)
        {
            this.address = address;
        }

        public String getTwitterLink ()
        {
            return twitterLink;
        }

        public void setTwitterLink (String twitterLink)
        {
            this.twitterLink = twitterLink;
        }

        public String getPresenceArea ()
        {
            return presenceArea;
        }

        public void setPresenceArea (String presenceArea)
        {
            this.presenceArea = presenceArea;
        }

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }

        public String getRole ()
        {
            return role;
        }

        public void setRole (String role)
        {
            this.role = role;
        }

        public String getImeiNo ()
        {
            return imeiNo;
        }

        public void setImeiNo (String imeiNo)
        {
            this.imeiNo = imeiNo;
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

        public String getLinkedinLink ()
        {
            return linkedinLink;
        }

        public void setLinkedinLink (String linkedinLink)
        {
            this.linkedinLink = linkedinLink;
        }

        public String getCreateDt ()
        {
            return createDt;
        }

        public void setCreateDt (String createDt)
        {
            this.createDt = createDt;
        }

        public String getMobileNo ()
        {
            return mobileNo;
        }

        public void setMobileNo (String mobileNo)
        {
            this.mobileNo = mobileNo;
        }

        public String getIsNewsLetter ()
        {
            return isNewsLetter;
        }

        public void setIsNewsLetter (String isNewsLetter)
        {
            this.isNewsLetter = isNewsLetter;
        }

        @Override
        public String toString() {
            return "Orgprofilelist{" +
                    "isGovtRegister='" + isGovtRegister + '\'' +
                    ", dpImgLink='" + dpImgLink + '\'' +
                    ", modifiedBy='" + modifiedBy + '\'' +
                    ", registrationNo='" + registrationNo + '\'' +
                    ", fbLink='" + fbLink + '\'' +
                    ", id='" + id + '\'' +
                    ", orgId='" + orgId + '\'' +
                    ", websiteLink='" + websiteLink + '\'' +
                    ", emailId='" + emailId + '\'' +
                    ", createdBy='" + createdBy + '\'' +
                    ", address='" + address + '\'' +
                    ", twitterLink='" + twitterLink + '\'' +
                    ", presenceArea='" + presenceArea + '\'' +
                    ", name='" + name + '\'' +
                    ", role='" + role + '\'' +
                    ", imeiNo='" + imeiNo + '\'' +
                    ", deleteFlag='" + deleteFlag + '\'' +
                    ", modifyDt='" + modifyDt + '\'' +
                    ", linkedinLink='" + linkedinLink + '\'' +
                    ", createDt='" + createDt + '\'' +
                    ", mobileNo='" + mobileNo + '\'' +
                    ", isNewsLetter='" + isNewsLetter + '\'' +
                    '}';
        }
    }
}
