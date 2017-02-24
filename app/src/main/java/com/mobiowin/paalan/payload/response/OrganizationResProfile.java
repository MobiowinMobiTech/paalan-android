package com.mobiowin.paalan.payload.response;

import java.util.Arrays;


public class OrganizationResProfile {

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
        return "OrganizationResProfile{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }



    public class Data
    {
        private String linkedinlink;

        private String dpimage;

        private String fblink;

        private String orgid;

        private String registrationno;

        private String presencearea;

        private String role;

        private String isregisterd;

        private String twitterlink;

        private String websitelink;

        public String getLinkedinlink ()
        {
            return linkedinlink;
        }

        public void setLinkedinlink (String linkedinlink)
        {
            this.linkedinlink = linkedinlink;
        }

        public String getDpimage ()
        {
            return dpimage;
        }

        public void setDpimage (String dpimage)
        {
            this.dpimage = dpimage;
        }

        public String getFblink ()
        {
            return fblink;
        }

        public void setFblink (String fblink)
        {
            this.fblink = fblink;
        }

        public String getOrgid ()
        {
            return orgid;
        }

        public void setOrgid (String orgid)
        {
            this.orgid = orgid;
        }

        public String getRegistrationno ()
        {
            return registrationno;
        }

        public void setRegistrationno (String registrationno)
        {
            this.registrationno = registrationno;
        }

        public String getPresencearea ()
        {
            return presencearea;
        }

        public void setPresencearea (String presencearea)
        {
            this.presencearea = presencearea;
        }

        public String getRole ()
        {
            return role;
        }

        public void setRole (String role)
        {
            this.role = role;
        }

        public String getIsregisterd ()
        {
            return isregisterd;
        }

        public void setIsregisterd (String isregisterd)
        {
            this.isregisterd = isregisterd;
        }

        public String getTwitterlink ()
        {
            return twitterlink;
        }

        public void setTwitterlink (String twitterlink)
        {
            this.twitterlink = twitterlink;
        }

        public String getWebsitelink ()
        {
            return websitelink;
        }

        public void setWebsitelink (String websitelink)
        {
            this.websitelink = websitelink;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "linkedinlink='" + linkedinlink + '\'' +
                    ", dpimage='" + dpimage + '\'' +
                    ", fblink='" + fblink + '\'' +
                    ", orgid='" + orgid + '\'' +
                    ", registrationno='" + registrationno + '\'' +
                    ", presencearea='" + presencearea + '\'' +
                    ", role='" + role + '\'' +
                    ", isregisterd='" + isregisterd + '\'' +
                    ", twitterlink='" + twitterlink + '\'' +
                    ", websitelink='" + websitelink + '\'' +
                    '}';
        }
    }

}
