package com.phyder.paalan.payload.response;

import java.util.Arrays;

/**
 * Created by cmss on 31/1/17.
 */

public class ResponseIndDashboard {


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
        return "ResponseIndDashboard{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }



    public class Data
    {
        private Orgprofilelist[] orgprofilelist;

        private Orglist[] orglist;

        private Orgachievementlist[] orgachievementlist;

        private Orgreqlist[] orgreqlist;

        private Eventlist[] eventlist;

        private String lastsyncdate;

        public Orgprofilelist[] getOrgprofilelist ()
        {
            return orgprofilelist;
        }

        public void setOrgprofilelist (Orgprofilelist[] orgprofilelist)
        {
            this.orgprofilelist = orgprofilelist;
        }

        public Orglist[] getOrglist ()
        {
            return orglist;
        }

        public void setOrglist (Orglist[] orglist)
        {
            this.orglist = orglist;
        }

        public Orgachievementlist[] getOrgachievementlist ()
        {
            return orgachievementlist;
        }

        public void setOrgachievementlist (Orgachievementlist[] orgachievementlist)
        {
            this.orgachievementlist = orgachievementlist;
        }

        public Orgreqlist[] getOrgreqlist ()
        {
            return orgreqlist;
        }

        public void setOrgreqlist (Orgreqlist[] orgreqlist)
        {
            this.orgreqlist = orgreqlist;
        }

        public Eventlist[] getEventlist ()
        {
            return eventlist;
        }

        public void setEventlist (Eventlist[] eventlist)
        {
            this.eventlist = eventlist;
        }

        public String getLastsyncdate ()
        {
            return lastsyncdate;
        }

        public void setLastsyncdate (String lastsyncdate)
        {
            this.lastsyncdate = lastsyncdate;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "orgprofilelist=" + Arrays.toString(orgprofilelist) +
                    ", orglist=" + Arrays.toString(orglist) +
                    ", orgachievementlist=" + Arrays.toString(orgachievementlist) +
                    ", orgreqlist=" + Arrays.toString(orgreqlist) +
                    ", eventlist=" + Arrays.toString(eventlist) +
                    ", lastsyncdate='" + lastsyncdate + '\'' +
                    '}';
        }
    }


    public class Orgreqlist{

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
                    ", createdBy='" + createdBy + '\'' +
                    ", twitterLink='" + twitterLink + '\'' +
                    ", presenceArea='" + presenceArea + '\'' +
                    ", role='" + role + '\'' +
                    ", imeiNo='" + imeiNo + '\'' +
                    ", deleteFlag='" + deleteFlag + '\'' +
                    ", linkedinLink='" + linkedinLink + '\'' +
                    ", isNewsLetter='" + isNewsLetter + '\'' +
                    '}';
        }
    }



    public class Orglist
    {
        private String modifiedBy;

        private String state;

        private String password;

        private String country;

        private String city;

        private String pincode;

        private String id;

        private String orgId;

        private String notificationId;

        private String emailId;

        private String createdBy;

        private String address;

        private String name;

        private String imeiNo;

        private String deleteFlag;

        private String modifyDt;

        private String createDt;

        private String mobileNo;

        private String deviceId;

        public String getModifiedBy ()
        {
            return modifiedBy;
        }

        public void setModifiedBy (String modifiedBy)
        {
            this.modifiedBy = modifiedBy;
        }

        public String getState ()
        {
            return state;
        }

        public void setState (String state)
        {
            this.state = state;
        }

        public String getPassword ()
        {
            return password;
        }

        public void setPassword (String password)
        {
            this.password = password;
        }

        public String getCountry ()
        {
            return country;
        }

        public void setCountry (String country)
        {
            this.country = country;
        }

        public String getCity ()
        {
            return city;
        }

        public void setCity (String city)
        {
            this.city = city;
        }

        public String getPincode ()
        {
            return pincode;
        }

        public void setPincode (String pincode)
        {
            this.pincode = pincode;
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

        public String getNotificationId ()
        {
            return notificationId;
        }

        public void setNotificationId (String notificationId)
        {
            this.notificationId = notificationId;
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

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
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

        public String getDeviceId ()
        {
            return deviceId;
        }

        public void setDeviceId (String deviceId)
        {
            this.deviceId = deviceId;
        }

        @Override
        public String toString() {
            return "Orglist{" +
                    "modifiedBy='" + modifiedBy + '\'' +
                    ", state='" + state + '\'' +
                    ", password='" + password + '\'' +
                    ", country='" + country + '\'' +
                    ", city='" + city + '\'' +
                    ", pincode='" + pincode + '\'' +
                    ", id='" + id + '\'' +
                    ", orgId='" + orgId + '\'' +
                    ", emailId='" + emailId + '\'' +
                    ", createdBy='" + createdBy + '\'' +
                    ", address='" + address + '\'' +
                    ", name='" + name + '\'' +
                    ", deleteFlag='" + deleteFlag + '\'' +
                    ", mobileNo='" + mobileNo + '\'' +
                    '}';
        }
    }



    public class Orgachievementlist
    {
        private String others;

        private String modifiedBy;

        private String achievementId;

        private String discription;

        private String id;

        private String orgId;

        private String subTitle;

        private String title;

        private String orgName;

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
            return "Orgachievementlist{" +
                    "modifiedBy='" + modifiedBy + '\'' +
                    ", achievementId='" + achievementId + '\'' +
                    ", discription='" + discription + '\'' +
                    ", id='" + id + '\'' +
                    ", orgId='" + orgId + '\'' +
                    ", subTitle='" + subTitle + '\'' +
                    ", title='" + title + '\'' +
                    ", orgName='" + orgName + '\'' +
                    ", image2='" + image2 + '\'' +
                    ", image1='" + image1 + '\'' +
                    ", createdBy='" + createdBy + '\'' +
                    ", deleteFlag='" + deleteFlag + '\'' +
                    ", modifyDt='" + modifyDt + '\'' +
                    ", image3='" + image3 + '\'' +
                    ", createDt='" + createDt + '\'' +
                    '}';
        }
    }



    public class Eventlist
    {
        private String others;

        private String endDt;

        private String location;

        private String modifiedBy;

        private String discription;

        private String id;

        private String orgId;

        private String subTitle;

        private String orgName;

        private String title;

        private String category;

        private String eventId;

        private String createdBy;

        private String eventLogo;

        private String startDt;

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

        public String getEndDt ()
        {
            return endDt;
        }

        public void setEndDt (String endDt)
        {
            this.endDt = endDt;
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

        public String getName ()
        {
            return orgName;
        }

        public void setName (String orgName)
        {
            this.orgName = orgName;
        }


        public String getTitle ()
        {
            return title;
        }

        public void setTitle (String title)
        {
            this.title = title;
        }

        public String getCategory ()
        {
            return category;
        }

        public void setCategory (String category)
        {
            this.category = category;
        }

        public String getEventId ()
        {
            return eventId;
        }

        public void setEventId (String eventId)
        {
            this.eventId = eventId;
        }

        public String getCreatedBy ()
        {
            return createdBy;
        }

        public void setCreatedBy (String createdBy)
        {
            this.createdBy = createdBy;
        }

        public String getEventLogo ()
        {
            return eventLogo;
        }

        public void setEventLogo (String eventLogo)
        {
            this.eventLogo = eventLogo;
        }


        public String getStartDt ()
        {
            return startDt;
        }

        public void setStartDt (String startDt)
        {
            this.startDt = startDt;
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
            return "Eventlist{" +
                    "others='" + others + '\'' +
                    ", endDt='" + endDt + '\'' +
                    ", location='" + location + '\'' +
                    ", discription='" + discription + '\'' +
                    ", id='" + id + '\'' +
                    ", orgId='" + orgId + '\'' +
                    ", subTitle='" + subTitle + '\'' +
                    ", orgName='" + orgName + '\'' +
                    ", title='" + title + '\'' +
                    ", eventId='" + eventId + '\'' +
                    ", eventLogo='" + eventLogo + '\'' +
                    ", createdBy='" + createdBy + '\'' +
                    ", startDt='" + startDt + '\'' +
                    ", deleteFlag='" + deleteFlag + '\'' +
                    ", createDt='" + createDt + '\'' +
                    '}';
        }
    }
}
