package com.example.learn.Utils

import retrofit2.http.GET
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Url

public interface File {
    @GET
    fun downloadFile(
        @Url url:String
        ):Call<ResponseBody>
}