package com.mrgamification.manamakhdumi.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {


    @FormUrlEncoded
    @POST("/webservice/api/app/submit?")
     Call<String> MakeManaCum(@Field("project") String project,
                                    @Field("userID") String userID,
                                    @Field("tozih") String tozih,
                                    @Field("action") String action,
                                    @Field("time") String time,
                                    @Field("actionId") String actionId);
}
