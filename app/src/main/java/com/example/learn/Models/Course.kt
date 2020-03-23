package com.example.learn.Models

data class Courses(val count:Int,val courses: List<Course>)

data class Course(val id:Int,val name:String,val description:String,val level:String,val cover_image:String,val videos:List<Video>)

data class FinishVideo(val message:String?,val error:String?)