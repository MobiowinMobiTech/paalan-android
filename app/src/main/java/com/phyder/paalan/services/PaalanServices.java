package com.phyder.paalan.services;

import com.phyder.paalan.payload.request.individual.IndivitualReqLogin;
import com.phyder.paalan.payload.request.individual.IndivitualReqUpdateProfile;
import com.phyder.paalan.payload.request.individual.IndivitualReqPublishEvent;
import com.phyder.paalan.payload.request.individual.IndivitualReqRegistration;
import com.phyder.paalan.payload.request.organization.OrganizationReqProfile;
import com.phyder.paalan.payload.request.organization.OrganizationReqPublishEven;
import com.phyder.paalan.payload.request.organization.OrganizationReqRegistration;
import com.phyder.paalan.payload.response.individual.IndivitualResLogin;
import com.phyder.paalan.payload.response.individual.IndivitualResPublishEvent;
import com.phyder.paalan.payload.response.individual.IndivitualResRegistration;
import com.phyder.paalan.payload.response.individual.IndivitualResUpdateProfile;
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

    @POST("mobiOwin/GeeniServlet")
    Call<IndivitualResLogin> paalanLogin(@Body IndivitualReqLogin reqLoginPayload);

    @POST("mobiOwin/GeeniServlet")
    Call<IndivitualResUpdateProfile> indUpdateProfile(@Body IndivitualReqUpdateProfile indivitualReqProfile);

    @POST("mobiOwin/GeeniServlet")
    Call<IndivitualReqRegistration> indRegistration(@Body IndivitualResRegistration indivitualResRegistration);

    @POST("mobiOwin/GeeniServlet")
    Call<IndivitualReqPublishEvent> indEventPublish(@Body IndivitualResPublishEvent indivitualResPublishEvent);

    @POST("mobiOwin/GeeniServlet")
    Call<OrganizationResProfile> orgProfile(@Body OrganizationReqProfile organizationReqProfile);

    @POST("mobiOwin/GeeniServlet")
    Call<OrganizationReqPublishEven> orgEventPublish(@Body OrganizationResPublishEvent organizationResPublishEvent);

    @POST("mobiOwin/GeeniServlet")
    Call<OrganizationResRegistration> orgRegistration(@Body OrganizationReqRegistration organizationResRegistration);
}
