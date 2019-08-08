package com.rocca.umrah.kafala.webservice;


import com.google.gson.JsonElement;
import com.rocca.umrah.kafala.reponse.CitiesResponseDTO;
import com.rocca.umrah.kafala.reponse.GenericResponseDTO;
import com.rocca.umrah.kafala.reponse.about.AboutResponseDTO;
import com.rocca.umrah.kafala.reponse.adverismentresponse.SearchResultResponseDTO;
import com.rocca.umrah.kafala.reponse.categoriesresponse.CategoriesResponseDTO;
import com.rocca.umrah.kafala.reponse.edituserprofile.EditUserProfileRequestDTO;
import com.rocca.umrah.kafala.reponse.myadvresponse.MyAdvResponseDTO;
import com.rocca.umrah.kafala.reponse.nationalitiesResponse.NationalitiesResponseDTO;
import com.rocca.umrah.kafala.reponse.notifications.NotificationResponseDTO;
import com.rocca.umrah.kafala.reponse.register.RegisterRequestDTO;
import com.rocca.umrah.kafala.reponse.register.RegisterResponseDTO;
import com.rocca.umrah.kafala.request.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;


public interface EndPointInterfaces {
    @GET("citiesMobile.php")
    Call<CitiesResponseDTO> getAllCities(@Header("Content-Type") String contentType);

    @GET("categoriesMobile.php")
    Call<CategoriesResponseDTO> getAllCategories(@Header("Content-Type") String contentType);

    @GET("nationalitiesMobile.php")
    Call<NationalitiesResponseDTO> getAllNationalities(@Header("Content-Type") String contentType);

    @GET("allGuranteedMobile.php")
    Call<List<SearchResultResponseDTO>> getAllAdvertisments(@Header("Content-Type") String contentType);

    @GET("searchGuaranteedMobile.php")
    Call<List<SearchResultResponseDTO>> searchForAdvertisments(@Header("Content-Type") String contentType,
                                                               @Query("occ") Integer occ,
                                                               @Query("nat") Integer nat,
                                                               @Query("city") Integer city);

    @GET("myPostsMobile.php")
    Call<MyAdvResponseDTO> myAdv(@Header("Content-Type") String contentType,
                                 @Query("userId") Integer occ);

    @POST("profileUpdate.php")
    Call<JsonElement> editUserprofile(@Header("Content-Type") String contentType, @Body EditUserProfileRequestDTO editUserProfileRequestDTO);

    @POST("registerMobile.php")
    Call<RegisterResponseDTO> register(@Header("Content-Type") String contentType, @Body RegisterRequestDTO registerRequestDTO);

    @POST("loginMobile.php")
    Call<RegisterResponseDTO> login(@Header("Content-Type") String contentType, @Body LoginRequestDTO loginRequestDTO);

    @POST("newPostMobile.php")
    Call<GenericResponseDTO> addPost(@Header("Content-Type") String contentType, @Body AddPostRequestDTO loginRequestDTO);

    @POST("updatePostMobile.php")
    Call<GenericResponseDTO> updatePost(@Header("Content-Type") String contentType, @Body EditPostRequest loginRequestDTO);

    @POST("updatePostDate.php")
    Call<GenericResponseDTO> updatePostDate(@Header("Content-Type") String contentType, @Body UpdatePostDate loginRequestDTO);

    @POST("changePasswordMobile.php")
    Call<GenericResponseDTO> changePassword(@Header("Content-Type") String contentType, @Body ChangePasswordRequest loginRequestDTO);

    @GET("deletePostMobile.php")
    Call<GenericResponseDTO> deletePost(@Header("Content-Type") String contentType, @Query("postId") Integer occ);

    @GET("notificationMobile.php")
    Call<NotificationResponseDTO> getAllNotification(@Header("Content-Type") String contentType);

    @POST("pagesMobile.php?pageId=1")
    Call<AboutResponseDTO> about(@Header("Content-Type") String contentType);

    @POST("pagesMobile.php?pageId=2")
    Call<AboutResponseDTO> bankAccounts(@Header("Content-Type") String contentType);

    @POST("pagesMobile.php?pageId=3")
    Call<AboutResponseDTO> callus(@Header("Content-Type") String contentType);

    @POST("pagesMobile.php?pageId=4")
    Call<AboutResponseDTO> getFirstTerms(@Header("Content-Type") String contentType);


    @POST("pagesMobile.php?pageId=5")
    Call<AboutResponseDTO> getSecondTerms(@Header("Content-Type") String contentType);

    @POST("pagesMobile.php?pageId=8")
    Call<AboutResponseDTO> getNotifications(@Header("Content-Type") String contentType);

}
