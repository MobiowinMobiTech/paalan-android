package com.phyder.paalan.payload.response.organization;

/**
 * Created on 22/12/16.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class OrganizationResRegistration {
    private String message;

    private String status;

    private String[] data;

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

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ClassPojo [message = " + message + ", status = " + status + ", data = " + data + "]";
    }
}
