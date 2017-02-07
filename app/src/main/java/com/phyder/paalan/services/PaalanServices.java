package com.phyder.paalan.services;

import com.phyder.paalan.payload.request.RequestIndDashboard;
import com.phyder.paalan.payload.request.RequestLogin;
import com.phyder.paalan.payload.request.RequestOrganizerProfile;
import com.phyder.paalan.payload.request.RequestSyncNotification;
import com.phyder.paalan.payload.request.SubmitDonateForm;
import com.phyder.paalan.payload.request.SubmitFeedback;
import com.phyder.paalan.payload.request.individual.IndivitualReqPublishEvent;
import com.phyder.paalan.payload.request.individual.IndivitualReqRegistration;
import com.phyder.paalan.payload.request.individual.IndivitualReqUpdateProfile;
import com.phyder.paalan.payload.request.organization.OrgReqCreateAchievments;
import com.phyder.paalan.payload.request.organization.OrgReqCreateEvent;
import com.phyder.paalan.payload.request.organization.OrgReqCreateRequest;
import com.phyder.paalan.payload.request.organization.OrgReqDeleteAchievement;
import com.phyder.paalan.payload.request.organization.OrgReqDeleteEvent;
import com.phyder.paalan.payload.request.organization.OrgReqDeleteRequest;
import com.phyder.paalan.payload.request.organization.OrgReqSyncAchievement;
import com.phyder.paalan.payload.request.organization.OrgReqSyncEvent;
import com.phyder.paalan.payload.request.organization.OrgReqSyncRequest;
import com.phyder.paalan.payload.request.organization.OrgReqUpdateAchievments;
import com.phyder.paalan.payload.request.organization.OrgReqUpdateEvent;
import com.phyder.paalan.payload.request.organization.OrganisationReqProfile;
import com.phyder.paalan.payload.request.organization.OrganizationReqResistration;
import com.phyder.paalan.payload.request.organization.RequestInitialData;
import com.phyder.paalan.payload.response.ResponseIndDashboard;
import com.phyder.paalan.payload.response.ResponseLogin;
import com.phyder.paalan.payload.response.ResponseOrganizerProfile;
import com.phyder.paalan.payload.response.individual.IndivitualResPublishEvent;
import com.phyder.paalan.payload.response.individual.IndivitualResRegistration;
import com.phyder.paalan.payload.response.individual.IndivitualResUpdateProfile;
import com.phyder.paalan.payload.response.organization.OrgResCreateAchievments;
import com.phyder.paalan.payload.response.organization.OrgResCreateEvent;
import com.phyder.paalan.payload.response.organization.OrgResCreateRequest;
import com.phyder.paalan.payload.response.organization.OrgResDeleteAchievement;
import com.phyder.paalan.payload.response.organization.OrgResDeleteEvent;
import com.phyder.paalan.payload.response.organization.OrgResDeleteRequest;
import com.phyder.paalan.payload.response.organization.OrgResSyncAchievement;
import com.phyder.paalan.payload.response.organization.OrgResSyncEvent;
import com.phyder.paalan.payload.response.organization.OrgResSyncRequest;
import com.phyder.paalan.payload.response.organization.OrgResUpdateAchievments;
import com.phyder.paalan.payload.response.organization.OrgResUpdateEvent;
import com.phyder.paalan.payload.response.organization.OrganizationResProfile;
import com.phyder.paalan.payload.response.organization.OrganizationResPublishEvent;
import com.phyder.paalan.payload.response.organization.OrganizationResRegistration;
import com.phyder.paalan.payload.response.organization.ResponseInitialData;

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
    Call<IndivitualResUpdateProfile> indUpdateProfile(@Body IndivitualReqUpdateProfile indivitualReqProfile);

    @POST("paalan/PaalanGateway")
    Call<IndivitualReqRegistration> indRegistration(@Body IndivitualResRegistration indivitualResRegistration);

    @POST("paalan/PaalanGateway")
    Call<IndivitualReqPublishEvent> indEventPublish(@Body IndivitualResPublishEvent indivitualResPublishEvent);

    @POST("paalan/PaalanGateway")
    Call<OrganizationResProfile> orgProfile(@Body OrganisationReqProfile organizationReqProfile);

    @POST("paalan/PaalanGateway")
    Call<OrgReqUpdateEvent> orgEventPublish(@Body OrganizationResPublishEvent organizationResPublishEvent);

    @POST("paalan/PaalanGateway")
    Call<OrganizationResRegistration> orgRegistration(@Body OrganizationReqResistration reqLoginPayload);

    @POST("paalan/PaalanGateway")
    Call<OrgResDeleteEvent> orgDeleteEvent(@Body OrgReqDeleteEvent reqLoginPayload);

    @POST("paalan/PaalanGateway")
    Call<OrgResCreateEvent> orgCreateEvent(@Body OrgReqCreateEvent orgResCreateEvent);

    @POST("paalan/PaalanGateway")
    Call<OrgResUpdateEvent> orgupdateEvent(@Body OrgReqUpdateEvent orgResUpdateEvent);

    @POST("paalan/PaalanGateway")
    Call<OrgResSyncEvent> orgSyncEvent(@Body OrgReqSyncEvent orgResUpdateEvent);

    @POST("paalan/PaalanGateway")
    Call<OrgResCreateAchievments> orgCreateAchievements(@Body OrgReqCreateAchievments orgReqAchievements);

    @POST("paalan/PaalanGateway")
    Call<OrgResUpdateAchievments> orgUpdateAchievements(@Body OrgReqUpdateAchievments orgReqAchievements);

    @POST("paalan/PaalanGateway")
    Call<OrgResDeleteAchievement> orgDeleteAchievements(@Body OrgReqDeleteAchievement orgReqAchievements);

    @POST("paalan/PaalanGateway")
    Call<OrgResSyncAchievement> orgSyncAchievements(@Body OrgReqSyncAchievement orgReqAchievements);

    @POST("paalan/PaalanGateway")
    Call<OrgResCreateRequest> orgCreateRequest(@Body OrgReqCreateRequest orgReqRequest);

    @POST("paalan/PaalanGateway")
    Call<OrgResDeleteRequest> orgDeleteRequest(@Body OrgReqDeleteRequest orgReqRequest);

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
    Call<SubmitFeedback> submitFeedback(@Body SubmitFeedback submitFeedback);

    @POST("paalan/PaalanGateway")
    Call<SubmitDonateForm> submitDonateForm(@Body SubmitDonateForm submitDonateForm);
}
