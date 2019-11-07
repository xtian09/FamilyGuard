package com.njdc.abb.familyguard.model.entity

data class UserSource<T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        AUTHENTICATED, ERROR, LOADING, LOGOUT
    }

    companion object {

        fun <T> authenticated(data: T): UserSource<T> {
            return UserSource(Status.AUTHENTICATED, data, null)
        }

        fun <T> error(msg: String, data: T? = null): UserSource<T> {
            return UserSource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): UserSource<T> {
            return UserSource(Status.LOADING, data, null)
        }

        fun <T> logout(): UserSource<T> {
            return UserSource(Status.LOGOUT, null, null)
        }
    }

}