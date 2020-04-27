package com.example.learn.Models


data class User(val _id:String,val first_name:String,val last_name:String,val gender:String,val email: String,val date_of_birth:String,val display_picture_url:String?)
data class UserObj(val user:User)
data class Users(val count:Int,val users:List<User>)