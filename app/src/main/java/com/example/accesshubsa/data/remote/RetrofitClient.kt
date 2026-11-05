package com.example.accesshubsa.data.remote
import android.content.Context
import com.example.accesshubsa.utils.SessionManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitClient {
 private const val BASE_URL="http://10.0.2.2:5000/"
 fun create(context:Context):ApiService{
  val log=HttpLoggingInterceptor().apply{level=HttpLoggingInterceptor.Level.BODY}
  val client=OkHttpClient.Builder().addInterceptor{chain->
   val b=chain.request().newBuilder();SessionManager(context).getToken()?.let{b.addHeader("Authorization","Bearer $it")};chain.proceed(b.build())
  }.addInterceptor(log).build()
  return Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build().create(ApiService::class.java)
 }
}
