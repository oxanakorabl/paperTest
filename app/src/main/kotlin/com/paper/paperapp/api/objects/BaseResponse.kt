package com.paper.paperapp.api.objects

import com.google.gson.annotations.SerializedName

open class BaseResponse<out T> {

    companion object {
        const val ERROR = 0
        const val SUCCESS = 1
        const val SAME_HASH = 2
    }

    @SerializedName("errors") private var _errors: List<String>? = null
    @SerializedName("data") private var _data: List<T>? = null
    @SerializedName("result") private var _result: Int = 0
    @SerializedName("message") private var _message: String = ""
    @SerializedName("hash") private var _hash: String = ""


    val errors get() = _errors
    val data get() = _data
    val result get() = _result
    val message get() = _message
    val hash get() = _hash

    val isSuccess get() = result != ERROR

}