package com.example.chatapp

class User {
    var name: String? = null
    var email: String? = null
    var uid: String? = null
    var date: String? = null

    constructor(){}

    constructor(name: String?, email: String? , uid: String?,date: String?){
        this.name = name
        this.email = email
        this.uid = uid
        this.date = date
    }
}