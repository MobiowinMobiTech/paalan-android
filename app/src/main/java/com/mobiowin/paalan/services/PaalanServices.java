package com.mobiowin.paalan.services;

import com.mobiowin.paalan.payload.request.OrgReqSyncEvent;
import com.mobiowin.paalan.payload.request.OrgReqSyncRequest;
import com.mobiowin.paalan.payload.request.OrganisationReqProfile;
import com.mobiowin.paalan.payload.request.OrganizationReqCreateAchievments;
import com.mobiowin.paalan.payload.request.OrganizationReqCreateEvent;
import com.mobiowin.paalan.payload.request.OrganizationReqDeleteAchievement;
import com.mobiowin.paalan.payload.request.OrganizationReqDeleteEvent;
import com.mobiowin.paalan.payload.request.OrganizationReqDeleteRequest;
import com.mobiowin.paalan.payload.request.OrganizationReqSyncAchievement;
import com.mobiowin.paalan.payload.request.RequestBroadcastNotification;
import com.mobiowin.paalan.payload.request.RequestIndDashboard;
import com.mobiowin.paalan.payload.request.RequestInitialData;
import com.mobiowin.paalan.payload.request.RequestLogin;
import com.mobiowin.paalan.payload.request.RequestOrganizerProfile;
import com.mobiowin.paalan.payload.request.RequestSyncNotification;
import com.mobiowin.paalan.payload.request.SubmitDonateForm;
import com.mobiowin.paalan.payload.request.SubmitFeedback;
import com.mobiowin.paalan.payload.response.ForgotPasswordResponse;
import com.mobiowin.paalan.payload.response.OrgResCreateAchievments;
import com.mobiowin.paalan.payload.response.OrgResCreateEvent;
import com.mobiowin.paalan.payload.response.OrgResCreateRequest;
import com.mobiowin.paalan.payload.response.OrgResDeleteAchievement;
import com.mobiowin.paalan.payload.response.OrgResDeleteEvent;
import com.mobiowin.paalan.payload.response.OrgResDeleteRequest;
import com.mobiowin.paalan.payload.response.OrgResNotificationDetailsAchievement;
import com.mobiowin.paalan.payload.response.OrgResNotificationDetailsEvent;
import com.mobiowin.paalan.payload.response.OrgResNotificationDetailsRequest;
import com.mobiowin.paalan.payload.response.OrgResSyncAchievement;
import com.mobiowin.paalan.payload.response.OrgResSyncRequest;
import com.mobiowin.paalan.payload.response.OrganizationResProfile;
import com.mobiowin.paalan.payload.response.OrganizationResRegistration;
import com.mobiowin.paalan.payload.response.ResponseIndDashboard;
import com.mobiowin.paalan.payload.response.ResponseInitialData;
import com.mobiowin.paalan.payload.response.ResponseLogin;
import com.mobiowin.paalan.payload.response.ResponseOrganizerProfile;
import com.mobiowin.paalan.payload.response.SubmitDonateResponse;
import com.mobiowin.paalan.payload.request.ForgotPasswordRequest;
import com.mobiowin.paalan.payload.request.OrgReqCreateRequest;
import com.mobiowin.paalan.payload.request.OrganizationReqResistration;
import com.mobiowin.paalan.payload.response.OrgResSyncEvent;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created on 21/12/16.
 * Author Dharmendra
 * Company CmssPhyder
 */

public interface PaalanServices {

    @POST("paalan/PaalanGateway")
    Call<ResponseLogin> paalanLogin(@Body RequestLogin reqLoginPayload);

    @POST("paalan/PaalanGateway")
    Call<OrganizationResProfile> orgProfile(@Body OrganisationReqProfile organizationReqProfile);

    @POST("paalan/PaalanGateway")
    Call<OrganizationResRegistration> orgRegistration(@Body OrganizationReqResistration reqLoginPayload);

    @POST("paalan/PaalanGateway")
    Call<OrgResDeleteEvent> orgDeleteEvent(@Body OrganizationReqDeleteEvent reqLoginPayload);

    @POST("paalan/PaalanGateway")
    Call<OrgResCreateEvent> orgCreateEvent(@Body OrganizationReqCreateEvent orgResCreateEvent);

    @POST("paalan/PaalanGateway")
    Call<OrgResSyncEvent> orgSyncEvent(@Body OrgReqSyncEvent orgResUpdateEvent);

    @POST("paalan/PaalanGateway")
    Call<OrgResCreateAchievments> orgCreateAchievements(@Body OrganizationReqCreateAchievments orgReqAchievements);

    @POST("paalan/PaalanGateway")
    Call<OrgResDeleteAchievement> orgDeleteAchievements(@Body OrganizationReqDeleteAchievement orgReqAchievements);

    @POST("paalan/PaalanGateway")
    Call<OrgResSyncAchievement> orgSyncAchievements(@Body OrganizationReqSyncAchievement orgReqAchievements);

    @POST("paalan/PaalanGateway")
    Call<OrgResCreateRequest> orgCreateRequest(@Body OrgReqCreateRequest orgReqRequest);

    @POST("paalan/PaalanGateway")
    Call<OrgResDeleteRequest> orgDeleteRequest(@Body OrganizationReqDeleteRequest orgReqRequest);

    @POST("paalan/PaalanGateway")
    Call<OrgResSyncRequest> orgSyncRequest(@Body OrgReqSyncRequest orgReqRequest);

    @POST("paalan/PaalanGateway")
    Call<ResponseInitialData> appSyncBanner(@Body RequestInitialData slidingBanner);

    @POST("paalan/PaalanGateway")
    Call<ResponseIndDashboard> appIndDashborad(@Body RequestIndDashboard reqIndDash);

    @POST("paalan/PaalanGateway")
    Call<RequestSyncNotification> syncNotificationId(@Body RequestSyncNotification requestSyncNotification);

    @POST("paalan/PaalanGateway")
    Call<ResponseOrganizerProfile> orgProfile(@Body RequestOrganizerProfile requestOrganizerProfile);

    @POST("paalan/PaalanGateway")
    Call<SubmitDonateResponse> submitFeedback(@Body SubmitFeedback submitFeedback);

    @POST("paalan/PaalanGateway")
    Call<SubmitDonateResponse> submitDonateForm(@Body SubmitDonateForm submitDonateForm);

    @POST("paalan/PaalanGateway")
    Call<ForgotPasswordResponse> forgotPassword(@Body ForgotPasswordRequest forgotPassword);

    @POST("paalan/PaalanGateway")
    Call<OrgResNotificationDetailsEvent> eventBroadcastNotification(@Body RequestBroadcastNotification broadcastNotification);

    @POST("paalan/PaalanGateway")
    Call<OrgResNotificationDetailsAchievement> achievementBroadcastNotification(@Body RequestBroadcastNotification broadcastNotification);

    @POST("paalan/PaalanGateway")
    Call<OrgResNotificationDetailsRequest> requestBroadcastNotification(@Body RequestBroadcastNotification broadcastNotification);
}
