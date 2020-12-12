package com.kotlindemo.api

import com.kotlindemo.model.api.BaseRequest
import com.kotlindemo.model.api.GetResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @get:GET("api/json/est/now") val getCurrentDate: Call<GetResponse>

    @POST("api.php") fun register(@Body req: BaseRequest?): Call<BaseRequest?>?

    @GET("superadmin/firmware/{firmware_id}") @Streaming
    fun downloadVersion(@Path("firmware_id") firmwareId: String?): Call<ResponseBody?>?

    @POST("dashboard/external/reset-password-request") @FormUrlEncoded
    fun resetPassword(@Field("email") email: String?): Call<ResponseBody?>?

    @Multipart @POST("dashboard/external/login")
    fun login(@PartMap requestBodyMap: Map<String?, RequestBody?>?): Call<ResponseBody?>?
}