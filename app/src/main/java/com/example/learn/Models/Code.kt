package com.example.learn.Models

import java.lang.Error

data class Response(val async:Int,val compile_status: String,val id: String,val code_id:String)

data class Code(val response: Response)

data class RunStatus(val memory_used:String,val time_limit:String,val output_html:String,val memory_limit:Int,val time_used:String,val signal:String,
                     val exit_code:String,val status_detail:String,val status:String,val stderr:String,val output:String,val async:Int,
                     val request_NOT_OK_reason:String,val request_ok:String)

data class RunResponse(val run_status: RunStatus,val compile_status: String,val code_id: String)

data class CodeRun(val response:RunResponse)

data class TestCase(val id:Int,val passed:Boolean,val error: String)

data class Submit(val lab_problem_id:Int,val test_cases:List<TestCase>)