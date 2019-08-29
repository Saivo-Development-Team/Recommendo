package com.saivo.recommendo.util.exception


class UserNotFoundException : Exception(){
    override val message: String? = "${super.message} - USER_NOT_FOUND"
}