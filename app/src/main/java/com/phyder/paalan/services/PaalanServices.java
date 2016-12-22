package com.phyder.paalan.services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created on 21/12/16.
 * Author Dharmendra
 * Company CmssPhyder
 */

public interface PaalanServices {

    @POST("PaalanNGO")
    Call<ReqLoginPayload> paalanLogin(@Body ReqLoginPayload reqLoginPayload);

//    @POST("PaalanNGO")
//    Call<ReqLoginPayload> paalanLogin(@Body ReqLoginPayload reqLoginPayload);
//
//    @POST("PaalanNGO")
//    Call<ReqLoginPayload> paalanLogin(@Body ReqLoginPayload reqLoginPayload);
//
//    @POST("PaalanNGO")
//    Call<ReqLoginPayload> paalanLogin(@Body ReqLoginPayload reqLoginPayload);
//
//    @POST("PaalanNGO")
//    Call<ReqLoginPayload> paalanLogin(@Body ReqLoginPayload reqLoginPayload);
//
//    @POST("PaalanNGO")
//    Call<ReqLoginPayload> paalanLogin(@Body ReqLoginPayload reqLoginPayload);
//
//    @POST("PaalanNGO")
//    Call<ReqLoginPayload> paalanLogin(@Body ReqLoginPayload reqLoginPayload);
}
