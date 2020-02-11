package com.example.learn.Models

data class User(val _id:String,val name:String,val gender:String)
data class Users(val count:Int,val users:List<User>)