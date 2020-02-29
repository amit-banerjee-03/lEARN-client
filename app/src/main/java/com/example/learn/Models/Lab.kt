package com.example.learn.Models

data class Lab(val id:Int,val name:String,val category:String,val level:String,val cover_image: String,val lab_problems:List<LabProblem>)
data class LabList(val id:Int,val name:String,val category:String,val level:String,val cover_image: String)
data class Labs(val count:Int,val labs:List<LabList>)