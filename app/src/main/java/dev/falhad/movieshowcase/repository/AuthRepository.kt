package dev.falhad.movieshowcase.repository

import com.afollestad.rxkprefs.RxkPrefs
import com.afollestad.rxkprefs.coroutines.asFlow
import com.google.gson.Gson


class AuthRepository(private val rxkPrefs: RxkPrefs, val gson: Gson) {


    private fun saveString(key: String, value: String = "") = rxkPrefs.string(key).set(value)

    private fun getString(key: String, default: String = ""): String =
        rxkPrefs.string(key, default).get()

    fun tokenFlow() = rxkPrefs.string("user_token", "").asFlow()

    fun token(token: String?) = saveString("user_token", token ?: "")
    fun token(): String = getString("user_token")

    fun uid(uid: String?) = saveString("user_uid", uid ?: "")
    fun uid(): String = getString("user_uid")


    fun email(email: String?) = saveString("user_email", email ?: "")
    fun email(): String = getString("user_email")

    fun mobile(mobile: String?) = saveString("user_mobile", mobile ?: "")
    fun mobile(): String = getString("user_mobile")


    fun username(username: String?) = saveString("user_username", username ?: "")
    fun username(): String = getString("user_username")

    fun tempToken(token: String?) = saveString("user_temp_token", token ?: "")
    fun tempToken(): String = getString("user_temp_token")


    fun tfa(tfa: Boolean?) = rxkPrefs.boolean("user_tfa").set(tfa ?: false)
    fun tfa(): Boolean = rxkPrefs.boolean("user_tfa").get()

    fun subscription(subscription: Boolean?) =
        rxkPrefs.boolean("user_subscription").set(subscription ?: false)

    fun subscription(): Boolean = rxkPrefs.boolean("user_subscription").get()





    fun logout() {
        rxkPrefs.clear()
    }




}

