package com.mobiowin.paalan.helper;


import com.mobiowin.paalan.payload.response.ResponseOrganizerProfile;

import retrofit2.Response;

public interface OrganizerProfileListener {
         void onSuccess(Response<ResponseOrganizerProfile> responseOrganizerProfile);
}