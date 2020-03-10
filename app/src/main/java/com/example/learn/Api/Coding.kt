package com.example.learn.Api

import com.example.learn.Models.*
import retrofit2.Call
import retrofit2.http.*

interface Coding {
    @FormUrlEncoded
    @POST("code/compile")
    fun compile(
        @Header("Authorization") auth:String,
        @Field("source_code") source:String,
        @Field("lang") lang:String,
        @Field("custom_input") input:String
    ): Call<Code>

    @FormUrlEncoded
    @POST("code/run")
    fun run(
        @Header("Authorization") auth:String,
        @Field("source_code") source:String,
        @Field("lang") lang:String,
        @Field("custom_input") input:String
    ): Call<CodeRun>

    @FormUrlEncoded
    @POST("code/test")
    fun runTestCases(
        @Header("Authorization") auth:String,
        @Field("source_code") source:String,
        @Field("lang") lang:String,
        @Field("lab_problem_id") labProblemId:Int
    ): Call<Submit>

}