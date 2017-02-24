package com.mobiowin.paalan.payload.response;

/**
 * Created on 6/1/17.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class OrgResCreateAchievments {

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
        private String achievementid;

        private String[] achievementimg;

        public String getAchievementid ()
        {
            return achievementid;
        }

        public void setAchievementid (String achievementid)
        {
            this.achievementid = achievementid;
        }

        public String[] getAchievementimg ()
        {
            return achievementimg;
        }

        public void setAchievementimg (String[] achievementimg)
        {
            this.achievementimg = achievementimg;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [achievementid = "+achievementid+", achievementimg = "+achievementimg+"]";
        }

    }

}




