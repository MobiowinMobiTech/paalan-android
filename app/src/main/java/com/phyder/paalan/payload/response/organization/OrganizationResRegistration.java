package com.phyder.paalan.payload.response.organization;

/**
 * Created on 22/12/16.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class OrganizationResRegistration {
    private String message;

    private String status;

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

    @Override
    public String toString() {
        return "OrganizationResRegistration{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
