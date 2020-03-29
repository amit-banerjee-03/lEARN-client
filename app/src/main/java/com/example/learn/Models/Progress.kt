package com.example.learn.Models


data class Progress(val id:Int,val name:String,val progress: Float)

data class CourseProgress(val id:Int,val name: String,val descriptor: String,val level: String,val cover_image:String,val total_videos:Int,val watched_videos:Int,val percentage_completed:Float)

data class LabProgress(val id: Int,val name:String, val category:String, val level:String, val cover_image:String, val test_cases:Int, val solved_test_cases:Int, val percentage_completed: Float )

data class UserProgress(val courses:List<CourseProgress>,val labs:List<LabProgress>)

data class Certificate(val message:String,val url: String)