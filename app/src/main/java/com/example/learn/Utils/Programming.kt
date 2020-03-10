package com.example.learn.Utils

object Programming {
    var languages=HashMap<String,String>()

    public fun getSupportedLanguages():ArrayList<String>{
        populateLanguages()
        var supportedLanguages=ArrayList<String>()
        for(key in languages.keys){
            supportedLanguages.add(key)
        }
        return supportedLanguages
    }

    fun getLanguageCode(languageName:String):String?{
        populateLanguages()
        return languages.get(languageName)
    }

    private fun populateLanguages(){
        languages.put("C","C")
        languages.put("C++","CPP")
        languages.put("C++11","CPP11")
        languages.put("C++14","CPP14")
        languages.put("CLOJURE","CLOJURE")
        languages.put("C#","CSHARP")
        languages.put("GO","GO")
        languages.put("HASKELL","HASKELL")
        languages.put("JAVA","JAVA")
        languages.put("JAVA 8","JAVA8")
        languages.put("JavaScript(Rhino)","JAVASCRIPT")
        languages.put("PHP","PHP")
        languages.put("Python 2","PYTHON")
        languages.put("Python 3","PYTHON3")
    }
}