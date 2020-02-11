package com.example.learn.Models

data class User(val _id:String,val first_name:String,val last_name:String,val gender:String,val email: String)
data class Users(val count:Int,val users:List<User>)