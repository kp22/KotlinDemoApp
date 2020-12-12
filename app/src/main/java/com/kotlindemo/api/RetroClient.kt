package com.kotlindemo.api

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
//import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object RetroClient {
    var BASE_URL = "http://worldclockapi.com/"
    private val retrofitInstance: Retrofit
        private get() {
            val gson = GsonBuilder().setLenient().create()
            return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson))
                /*.client(httpClient)*/.build()
        }

    /**
     * Get API Service
     * @return API Service
     */
    val apiService: ApiService
        get() = retrofitInstance.create(ApiService::class.java)

    // Set connection timeout
    // Read timeout
    // Write timeout
//    val httpClient: OkHttpClient
//        get() {
//            val interceptor = HttpLoggingInterceptor()
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//            return OkHttpClient.Builder().connectTimeout(220, TimeUnit.SECONDS) // Set connection timeout
//                .readTimeout(220, TimeUnit.SECONDS) // Read timeout
//                .writeTimeout(220, TimeUnit.SECONDS) // Write timeout
//                .addInterceptor(object : Interceptor {
//                    @Throws(IOException::class)
//                    override fun intercept(chain: Interceptor.Chain): Response {
//                        val request = chain.request()
//                        val newRequest: Request
//                        newRequest = request.newBuilder().build()
//                        return chain.proceed(newRequest)
//                    }
//                }).addInterceptor(interceptor).build()
//        }
}