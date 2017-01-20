package com.phyder.paalan.model;

/**
 * Created by Yashica on 1/15/16.
 * Company PhyderCmss
 */
public class DashboardModel {

    private String dashboardContentName;
    private String dashboardContentDescription;
    private int imageResourceId;

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getDashboardContentName() {
        return dashboardContentName;
    }

    public void setDashboardContentName(String dashboardContentName) {
        this.dashboardContentName = dashboardContentName;
    }

    public String getDashboardContentDescription() {
        return dashboardContentDescription;
    }

    public void setDashboardContentDescription(String dashboardContentDescription) {
        this.dashboardContentDescription = dashboardContentDescription;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}




