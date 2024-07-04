package com.example.quest;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class VkPoster {

    private static final String VK_API_URL = "https://api.vk.com/method/";
    private static final String VK_API_VERSION = "5.131";

    public void postToWall(String accessToken, String ownerId, String message, Bitmap image) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(VK_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        VkApiService vkApiService = retrofit.create(VkApiService.class);

        Call<UploadServerResponse> uploadServerCall = vkApiService.getUploadServer(accessToken, VK_API_VERSION);
        uploadServerCall.enqueue(new Callback<UploadServerResponse>() {
            @Override
            public void onResponse(Call<UploadServerResponse> call, retrofit2.Response<UploadServerResponse> response) {
                if (response.isSuccessful()) {
                    String uploadUrl = response.body().getResponse().getUploadUrl();
                    uploadPhoto(uploadUrl, image, accessToken, ownerId, message);
                } else {
                    try {
                        Log.e("VkPoster", "Error getting upload server: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UploadServerResponse> call, Throwable t) {
                Log.e("VkPoster", "Failure getting upload server: " + t.getMessage());
            }
        });
    }

    private void uploadPhoto(String uploadUrl, Bitmap image, String accessToken, String ownerId, String message) {
        OkHttpClient client = new OkHttpClient();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("photo", "photo.jpg", RequestBody.create(MediaType.parse("image/jpeg"), byteArray))
                .build();

        Request request = new Request.Builder()
                .url(uploadUrl)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.e("VkPoster", "Upload photo failed: " + e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    savePhoto(responseBody, accessToken, ownerId, message);
                } else {
                    Log.e("VkPoster", "Upload photo error: " + response.body().string());
                }
            }
        });
    }

    private void savePhoto(String responseBody, String accessToken, String ownerId, String message) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(VK_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        VkApiService vkApiService = retrofit.create(VkApiService.class);

        SavePhotoRequest savePhotoRequest = new Gson().fromJson(responseBody, SavePhotoRequest.class);

        Call<SavePhotoResponse> savePhotoCall = vkApiService.saveWallPhoto(
                accessToken,
                savePhotoRequest.getServer(),
                savePhotoRequest.getPhoto(),
                savePhotoRequest.getHash(),
                VK_API_VERSION
        );

        savePhotoCall.enqueue(new Callback<SavePhotoResponse>() {
            @Override
            public void onResponse(Call<SavePhotoResponse> call, retrofit2.Response<SavePhotoResponse> response) {
                if (response.isSuccessful()) {
                    String photoId = response.body().getResponse().get(0).getId();
                    String attachment = "photo" + ownerId + "_" + photoId;
                    postToWallWithAttachment(accessToken, ownerId, message, attachment);
                } else {
                    try {
                        Log.e("VkPoster", "Save photo error: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SavePhotoResponse> call, Throwable t) {
                Log.e("VkPoster", "Save photo failure: " + t.getMessage());
            }
        });
    }

    private void postToWallWithAttachment(String accessToken, String ownerId, String message, String attachment) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(VK_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        VkApiService vkApiService = retrofit.create(VkApiService.class);

        Call<VkResponse> call = vkApiService.postToWall(accessToken, ownerId, message, attachment, VK_API_VERSION);
        call.enqueue(new Callback<VkResponse>() {
            @Override
            public void onResponse(Call<VkResponse> call, retrofit2.Response<VkResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("VkPoster", "Post successful: " + response.body().getResponse());
                } else {
                    try {
                        Log.e("VkPoster", "Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<VkResponse> call, Throwable t) {
                Log.e("VkPoster", "Failure: " + t.getMessage());
            }
        });
    }

    interface VkApiService {
        @GET("photos.getWallUploadServer")
        Call<UploadServerResponse> getUploadServer(@Query("access_token") String accessToken, @Query("v") String version);

        @FormUrlEncoded
        @POST("photos.saveWallPhoto")
        Call<SavePhotoResponse> saveWallPhoto(
                @Field("access_token") String accessToken,
                @Field("server") String server,
                @Field("photo") String photo,
                @Field("hash") String hash,
                @Field("v") String version
        );

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

    static class UploadServerResponse {
        @SerializedName("response")
        private Response response;

        public Response getResponse() {
            return response;
        }

        static class Response {
            @SerializedName("upload_url")
            private String uploadUrl;

            public String getUploadUrl() {
                return uploadUrl;
            }
        }
    }

    static class SavePhotoRequest {
        @SerializedName("server")
        private String server;

        @SerializedName("photo")
        private String photo;

        @SerializedName("hash")
        private String hash;

        public String getServer() {
            return server;
        }

        public String getPhoto() {
            return photo;
        }

        public String getHash() {
            return hash;
        }
    }

    static class SavePhotoResponse {
        @SerializedName("response")
        private List<PhotoResponse> response;

        public List<PhotoResponse> getResponse() {
            return response;
        }

        static class PhotoResponse {
            @SerializedName("id")
            private String id;

            public String getId() {
                return id;
            }
        }
    }

    static class VkResponse {
        @SerializedName("response")
        private String response;

        public String getResponse() {
            return response;
        }
    }
}