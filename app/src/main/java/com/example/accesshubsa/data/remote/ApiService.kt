package com.example.accesshubsa.data.remote
import retrofit2.Response
import retrofit2.http.*
data class RegisterRequest(val firstName:String,val surname:String,val email:String,val phoneNumber:String,val password:String)
data class LoginRequest(val email:String,val password:String)
data class LoginResponse(val userId:Int,val firstName:String,val surname:String,val email:String,val token:String)
data class ServiceResponse(val serviceId:Int,val serviceName:String,val category:String,val description:String,val categoryId:Int)
data class CreateRequest(val serviceId:Int,val description:String,val priority:String)
data class RequestResponse(val requestId:Int,val serviceId:Int,val serviceName:String,val description:String,val priority:String,val status:String,val createdDate:String,val synced:Boolean)
data class ProfileResponse(val userId:Int,val firstName:String,val surname:String,val email:String,val phoneNumber:String,val preferredLanguage:String,val notificationEnabled:Boolean,val points:Int)
data class UpdateProfileRequest(val firstName:String,val surname:String,val phoneNumber:String,val preferredLanguage:String,val notificationEnabled:Boolean)
data class ChangePasswordRequest(val currentPassword:String,val newPassword:String)
data class NotificationResponse(val notificationId:Int,val notificationMessage:String,val isRead:Boolean,val createdDate:String)
data class AchievementResponse(val achievementId:Int,val achievementName:String,val description:String,val pointsRequired:Int,val earned:Boolean)
data class FcmTokenRequest(val fcmToken:String)
interface ApiService {
 @POST("api/auth/register") suspend fun register(@Body request:RegisterRequest):Response<Any>
 @POST("api/auth/login") suspend fun login(@Body request:LoginRequest):Response<LoginResponse>
 @POST("api/auth/google") suspend fun googleLogin(@Body request:Map<String,String>):Response<LoginResponse>
 @GET("api/services") suspend fun getServices(@Query("search") search:String?=null,@Query("categoryId") categoryId:Int?=null):Response<List<ServiceResponse>>
 @GET("api/services/categories") suspend fun getCategories():Response<List<Map<String,Any>>>
 @GET("api/requests") suspend fun getRequests():Response<List<RequestResponse>>
 @POST("api/requests") suspend fun createRequest(@Body request:CreateRequest):Response<Any>
 @PUT("api/requests/{id}/status") suspend fun updateRequestStatus(@Path("id") id:Int,@Body status:Map<String,String>):Response<Any>
 @GET("api/profile") suspend fun getProfile():Response<ProfileResponse>
 @PUT("api/profile") suspend fun updateProfile(@Body request:UpdateProfileRequest):Response<Any>
 @PUT("api/profile/password") suspend fun changePassword(@Body request:ChangePasswordRequest):Response<Any>
 @DELETE("api/profile") suspend fun deleteAccount():Response<Any>
 @POST("api/profile/fcm-token") suspend fun registerFcmToken(@Body request:FcmTokenRequest):Response<Any>
 @GET("api/notifications") suspend fun getNotifications():Response<List<NotificationResponse>>
 @PUT("api/notifications/{id}/read") suspend fun markNotificationRead(@Path("id") id:Int):Response<Any>
 @GET("api/achievements") suspend fun getAchievements():Response<List<AchievementResponse>>
}
