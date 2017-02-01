package com.phyder.paalan.payload.response.organization;

import java.util.Arrays;

/**
 * Created on 22/12/16.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class OrganizationResRegistration {
    private String message;

    private String status;

    private Data[] data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OrganizationResRegistration{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }

    public class Data {
        private String orgid;
        private String errmsg;

        public String getErrmsg() {
            return errmsg;
        }

        public void setErrmsg(String errmsg) {
            this.errmsg = errmsg;
        }

        public String getOrgid() {
            return orgid;
        }

        public void setOrgid(String orgid) {
            this.orgid = orgid;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "orgid='" + orgid + '\'' +
                    '}';
        }
    }
}
