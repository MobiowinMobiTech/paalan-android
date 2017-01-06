package com.phyder.paalan.services;

import com.phyder.paalan.payload.request.individual.IndivitualReqLogin;
import com.phyder.paalan.payload.request.individual.IndivitualReqPublishEvent;
import com.phyder.paalan.payload.request.individual.IndivitualReqRegistration;
import com.phyder.paalan.payload.request.individual.IndivitualReqUpdateProfile;
import com.phyder.paalan.payload.request.organization.OrgReqDeleteEvent;
import com.phyder.paalan.payload.request.organization.OrganisationReqProfile;
import com.phyder.paalan.payload.request.organization.OrganizationReqProfile;
import com.phyder.paalan.payload.request.organization.OrganizationReqPublishEven;
import com.phyder.paalan.payload.request.organization.OrganizationReqRegistration;
import com.phyder.paalan.payload.request.organization.OrganizationReqResistration;
import com.phyder.paalan.payload.response.individual.IndivitualResLogin;
import com.phyder.paalan.payload.response.individual.IndivitualResPublishEvent;
import com.phyder.paalan.payload.response.individual.IndivitualResRegistration;
import com.phyder.paalan.payload.response.individual.IndivitualResUpdateProfile;
import com.phyder.paalan.payload.response.organization.OrgResDeleteEvent;
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
    Call<IndivitualResLogin> paalanLogin(@Body IndivitualReqLogin reqLoginPayload);

    @POST("paalan/PaalanGateway")
    Call<IndivitualResUpdateProfile> indUpdateProfile(@Body IndivitualReqUpdateProfile indivitualReqProfile);

    @POST("paalan/PaalanGateway")
    Call<IndivitualReqRegistration> indRegistration(@Body IndivitualResRegistration indivitualResRegistration);

    @POST("paalan/PaalanGateway")
    Call<IndivitualReqPublishEvent> indEventPublish(@Body IndivitualResPublishEvent indivitualResPublishEvent);

    @POST("paalan/PaalanGateway")
    Call<OrganizationResProfile> orgProfile(@Body OrganisationReqProfile organizationReqProfile);

    @POST("paalan/PaalanGateway")
    Call<OrganizationReqPublishEven> orgEventPublish(@Body OrganizationResPublishEvent organizationResPublishEvent);

    @POST("paalan/PaalanGateway")
    Call<OrganizationResRegistration> orgRegistration(@Body OrganizationReqResistration reqLoginPayload);

    @POST("paalan/PaalanGateway")
    Call<OrgResDeleteEvent> orgDeleteEvent(@Body OrgReqDeleteEvent reqLoginPayload);
}
