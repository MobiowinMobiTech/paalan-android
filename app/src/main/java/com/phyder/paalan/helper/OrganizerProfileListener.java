package com.phyder.paalan.helper;


import com.phyder.paalan.payload.response.ResponseOrganizerProfile;

import retrofit2.Response;

public interface OrganizerProfileListener {

         void onSuccess(Response<ResponseOrganizerProfile> responseOrganizerProfile);
    }