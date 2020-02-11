package com.example.learn.Utils

import android.content.Context
import java.net.URLEncoder

object Utility {
    fun generateGetString(map: Map<String, String>): String {
        var url = ""
        for (key in map.keys) {
            url += "$key=${map[key]}&"
        }
        if (url != "") {
            url = url.substring(0, url.length - 2)
        }
        return URLEncoder.encode(url,"utf-8")
    }


}