package com.phyder.paalan.payload.request.organization;

/**
 * Created on 22/12/16.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class OrganizationReqPublishEven {
    private String entity;

    private Data data;

    private String type;

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "OrganizationReqPublishEven{" +
                "entity='" + entity + '\'' +
                ", data=" + data +
                ", type='" + type + '\'' +
                '}';
    }

    public class Data {
        private String endtime;

        private String starttime;

        private String memberid;

        private String state;

        private String discription;

        private String image;

        private String cnumer;

        private String eventid;

        private String country;

        private String city;

        private String imeino;

        private String title;

        private String address;

        private String subtitle;

        private String cperson;

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getMemberid() {
            return memberid;
        }

        public void setMemberid(String memberid) {
            this.memberid = memberid;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getDiscription() {
            return discription;
        }

        public void setDiscription(String discription) {
            this.discription = discription;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCnumer() {
            return cnumer;
        }

        public void setCnumer(String cnumer) {
            this.cnumer = cnumer;
        }

        public String getEventid() {
            return eventid;
        }

        public void setEventid(String eventid) {
            this.eventid = eventid;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getImeino() {
            return imeino;
        }

        public void setImeino(String imeino) {
            this.imeino = imeino;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getCperson() {
            return cperson;
        }

        public void setCperson(String cperson) {
            this.cperson = cperson;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "endtime='" + endtime + '\'' +
                    ", starttime='" + starttime + '\'' +
                    ", memberid='" + memberid + '\'' +
                    ", state='" + state + '\'' +
                    ", discription='" + discription + '\'' +
                    ", image='" + image + '\'' +
                    ", cnumer='" + cnumer + '\'' +
                    ", eventid='" + eventid + '\'' +
                    ", country='" + country + '\'' +
                    ", city='" + city + '\'' +
                    ", imeino='" + imeino + '\'' +
                    ", title='" + title + '\'' +
                    ", address='" + address + '\'' +
                    ", subtitle='" + subtitle + '\'' +
                    ", cperson='" + cperson + '\'' +
                    '}';
        }
    }
}
