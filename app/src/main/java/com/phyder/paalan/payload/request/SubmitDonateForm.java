package com.phyder.paalan.payload.request;

/**
 * Created by yashika on 7/2/17.
 */

public class SubmitDonateForm {
    private String entity;

    private Data data;

    private String action;

    private String type;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEntity ()
    {
        return entity;
    }

    public void setEntity (String entity)
    {
        this.entity = entity;
    }

    public Data getData ()
    {
        return data;
    }

    public void setData (Data data)
    {
        this.data = data;
    }

    public String getAction ()
    {
        return action;
    }

    public void setAction (String action)
    {
        this.action = action;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SubmitDonateForm{" +
                "entity='" + entity + '\'' +
                ", data=" + data +
                ", action='" + action + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public class Data
    {
        private String freetext4;

        private String freetext3;

        private String freetext2;

        private String freetext1;

        private String img;

        private String deliverymode;

        private String date;

        private String emailid;

        private String mobileno;

        private String message;

        private String category;

        private String address;

        private String name;

        public String getFreetext4 ()
        {
            return freetext4;
        }

        public void setFreetext4 (String freetext4)
        {
            this.freetext4 = freetext4;
        }

        public String getFreetext3 ()
        {
            return freetext3;
        }

        public void setFreetext3 (String freetext3)
        {
            this.freetext3 = freetext3;
        }

        public String getFreetext2 ()
        {
            return freetext2;
        }

        public void setFreetext2 (String freetext2)
        {
            this.freetext2 = freetext2;
        }

        public String getFreetext1 ()
        {
            return freetext1;
        }

        public void setFreetext1 (String freetext1)
        {
            this.freetext1 = freetext1;
        }

        public String getImg ()
        {
            return img;
        }

        public void setImg (String img)
        {
            this.img = img;
        }

        public String getDeliverymode ()
        {
            return deliverymode;
        }

        public void setDeliverymode (String deliverymode)
        {
            this.deliverymode = deliverymode;
        }

        public String getDate ()
        {
            return date;
        }

        public void setDate (String date)
        {
            this.date = date;
        }

        public String getEmailid ()
        {
            return emailid;
        }

        public void setEmailid (String emailid)
        {
            this.emailid = emailid;
        }

        public String getMobileno ()
        {
            return mobileno;
        }

        public void setMobileno (String mobileno)
        {
            this.mobileno = mobileno;
        }

        public String getMessage ()
        {
            return message;
        }

        public void setMessage (String message)
        {
            this.message = message;
        }

        public String getCategory ()
        {
            return category;
        }

        public void setCategory (String category)
        {
            this.category = category;
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

        @Override
        public String toString()
        {
            return "ClassPojo [freetext4 = "+freetext4+", freetext3 = "+freetext3+", freetext2 = "+freetext2+", freetext1 = "+freetext1+", img = "+img+", deliverymode = "+deliverymode+", date = "+date+", emailid = "+emailid+", mobileno = "+mobileno+", message = "+message+", category = "+category+", address = "+address+", name = "+name+"]";
        }
    }



}
