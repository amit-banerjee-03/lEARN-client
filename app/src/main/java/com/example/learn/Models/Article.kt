package com.example.learn.Models

data class Article(val id:Int,val title:String,val body:String,val description:String)
data class Articles(val count:Int,val articles:List<Article>,val cover_image:String?)