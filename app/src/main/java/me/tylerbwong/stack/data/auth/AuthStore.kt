package me.tylerbwong.stack.data.auth

import android.content.SharedPreferences
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.tylerbwong.stack.data.model.Scope
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import javax.inject.Inject

class AuthStore @Inject constructor(private val preferences: SharedPreferences) {

    var accessToken: String?
        get() = preferences.getString(ACCESS_TOKEN, null)
        private set(value) {
            preferences.edit().putString(ACCESS_TOKEN, value).apply()
            mutableIsAuthenticatedLiveData.postValue(!value.isNullOrBlank())
        }

    private val mutableIsAuthenticatedLiveData = MutableLiveData(!accessToken.isNullOrBlank())

    val isAuthenticatedLiveData: LiveData<Boolean> = mutableIsAuthenticatedLiveData

    fun setAccessToken(uri: Uri) {
        accessToken = Uri.parse("$AUTH_REDIRECT?${uri.encodedFragment}")
            .getQueryParameter(ACCESS_TOKEN)
    }

    fun clear() {
        accessToken = null
    }

    companion object {
        const val ACCESS_TOKEN = "access_token"
        private const val AUTH_BASE = "https://stackoverflow.com/oauth/dialog"
        private const val AUTH_REDIRECT = "stack://tylerbwong.me/auth/redirect"
        private const val CLIENT_ID = "12074"

        val authUrl: String = run {
            val httpUrl = AUTH_BASE.toHttpUrlOrNull() ?: return@run ""

            httpUrl.newBuilder()
                .addEncodedQueryParameter("client_id", CLIENT_ID)
                .addEncodedQueryParameter("redirect_uri", AUTH_REDIRECT)
                .addEncodedQueryParameter("scope", Scope.all.joinToString(","))
                .build()
                .toString()
        }
    }
}
