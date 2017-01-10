package com.phyder.paalan.services;

import com.phyder.paalan.payload.request.individual.IndividualReqLogin;
import com.phyder.paalan.payload.request.individual.IndivitualReqPublishEvent;
import com.phyder.paalan.payload.request.individual.IndivitualReqRegistration;
import com.phyder.paalan.payload.request.individual.IndivitualReqUpdateProfile;
import com.phyder.paalan.payload.request.organization.OrgReqCreateAchievments;
import com.phyder.paalan.payload.request.organization.OrgReqCreateEvent;
import com.phyder.paalan.payload.request.organization.OrgReqDeleteEvent;
import com.phyder.paalan.payload.request.organization.OrgReqDeleteAchievement;
import com.phyder.paalan.payload.request.organization.OrgReqSyncAchievement;
import com.phyder.paalan.payload.request.organization.OrgReqSyncEvent;
import com.phyder.paalan.payload.request.organization.OrgReqUpdateAchievments;
import com.phyder.paalan.payload.request.organization.OrgReqUpdateEvent;
import com.phyder.paalan.payload.request.organization.OrganisationReqProfile;
import com.phyder.paalan.payload.request.organization.OrganizationReqLogin;
import com.phyder.paalan.payload.request.organization.OrganizationReqPublishEven;
import com.phyder.paalan.payload.request.organization.OrganizationReqResistration;
import com.phyder.paalan.payload.response.individual.IndivitualResLogin;
import com.phyder.paalan.payload.response.individual.IndivitualResPublishEvent;
import com.phyder.paalan.payload.response.individual.IndivitualResRegistration;
import com.phyder.paalan.payload.response.individual.IndivitualResUpdateProfile;
import com.phyder.paalan.payload.response.organization.OrgResCreateAchievments;
import com.phyder.paalan.payload.response.organization.OrgResCreateEvent;
import com.phyder.paalan.payload.response.organization.OrgResDeleteEvent;
import com.phyder.paalan.payload.response.organization.OrgResDeleteAchievement;
import com.phyder.paalan.payload.response.organization.OrgResSyncAchievement;
import com.phyder.paalan.payload.response.organization.OrgResSyncEvent;
import com.phyder.paalan.payload.response.organization.OrgResUpdateAchievments;
import com.phyder.paalan.payload.response.organization.OrgResUpdateEvent;
import com.phyder.paalan.payload.response.organization.OrganizationResLogin;
import com.phyder.paalan.payload.response.organization.OrganizationResProfile;
import com.phyder.paalan.payload.response.organization.OrganizationResPublishEvent;
import com.phyder.paalan.payload.response.organization.OrganizationResRegistration;

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
    Call<IndivitualResLogin> paalanINDLogin(@Body IndividualReqLogin reqLoginPayload);

    @POST("paalan/PaalanGateway")
    Call<OrganizationResLogin> paalanORGLogin(@Body OrganizationReqLogin reqLoginPayload);

    @POST("paalan/PaalanGateway")
    Call<IndivitualResUpdateProfile> indUpdateProfile(@Body IndivitualReqUpdateProfile indivitualReqProfile);

    @POST("paalan/PaalanGateway")
    Call<IndivitualReqRegistration> indRegistration(@Body IndivitualResRegistration indivitualResRegistration);

    @POST("paalan/PaalanGateway")
    Call<IndivitualReqPublishEvent> indEventPublish(@Body IndivitualResPublishEvent indivitualResPublishEvent);

    @POST("paalan/PaalanGateway")
    Call<OrganizationResProfile> orgProfile(@Body OrganisationReqProfile organizationReqProfile);
//    Call<OrganizationResProfile> orgProfile(@Body OrganizationReqProfile organizationReqProfile);

    @POST("paalan/PaalanGateway")
    Call<OrganizationReqPublishEven> orgEventPublish(@Body OrganizationResPublishEvent organizationResPublishEvent);

    @POST("paalan/PaalanGateway")
    Call<OrganizationResRegistration> orgRegistration(@Body OrganizationReqResistration reqLoginPayload);

    @POST("paalan/PaalanGateway")
    Call<OrgResDeleteEvent> orgDeleteEvent(@Body OrgReqDeleteEvent reqLoginPayload);

//    Call<OrganizationResRegistration> orgRegistration(@Body OrganizationReqRegistration organizationResRegistration);

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
}
