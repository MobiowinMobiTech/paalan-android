package com.phyder.paalan.fragments;

/**
 * Created by Yashica on 1/15/16.
 * Company PhyderCmss
 */
public class DashboardModel {

    private String dashboardContentName;
    private String dashboardContentDescription;
    private int imageResourceId;

    int getImageResourceId() {
        return imageResourceId;
    }

    String getDashboardContentName() {
        return dashboardContentName;
    }

    void setDashboardContentName(String dashboardContentName) {
        this.dashboardContentName = dashboardContentName;
    }

    String getDashboardContentDescription() {
        return dashboardContentDescription;
    }

    void setDashboardContentDescription(String dashboardContentDescription) {
        this.dashboardContentDescription = dashboardContentDescription;
    }

    void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}




