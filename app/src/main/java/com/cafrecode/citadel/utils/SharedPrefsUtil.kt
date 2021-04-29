package com.cafrecode.citadel.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPrefsUtil {

    //TODO: Store this and others in Room allow multiple addresses
    private const val DEFAULT_WALLET_ADDRESS = "default_wallet_address"

    private const val TAG = "SharedPrefsUtil"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(TAG, Context.MODE_PRIVATE)
    }

    fun setDefaultAddress(context: Context, token: String?) {
        val editor = getPrefs(context).edit()
        editor.putString(DEFAULT_WALLET_ADDRESS, token)
        editor.apply()
    }
    fun getDefaultAddress(context: Context): String? =
        getPrefs(context).getString(DEFAULT_WALLET_ADDRESS, null)
}
