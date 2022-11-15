package com.example.myapplication

class DataBaseInfo {
    var userName: String? = null
    var userpass: String? = null
    private var emailAddress: String? = null
    fun getemailAddress(): String? {
        return emailAddress
    }

    fun setemailAddress(emailAddress: String?) {
        this.emailAddress = emailAddress
    }
}
