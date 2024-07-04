package com.example.quest;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface VkApiService {
    @FormUrlEncoded
    @POST("wall.post")
    Call<VkResponse> postToWall(
            @Field("access_token") String accessToken,
            @Field("owner_id") String ownerId,
            @Field("message") String message,
            @Field("attachments") String attachments,
            @Field("v") String version
    );
}
